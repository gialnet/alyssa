package com.vivaldi.spring.alyssa.services;

import com.vivaldi.spring.alyssa.data.MisEnlaces;
import com.vivaldi.spring.alyssa.repositories.MisEnlacesRepo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServiceMisEnlaces {

    private final MisEnlacesRepo misEnlacesRepo;
    private final ElasticsearchOperations elasticsearchOperations;
    private final String ENLACES_INDEX = "mis-enlaces";

    @Autowired
    private HttpSession httpSession;
    //private MisEnlaces misEnlaces;

    public ServiceMisEnlaces(MisEnlacesRepo misEnlacesRepo, ElasticsearchOperations elasticsearchOperations) {
        this.misEnlacesRepo = misEnlacesRepo;
        this.elasticsearchOperations = elasticsearchOperations;
    }


    /**
     *
     * @param email
     * @param pageNum
     * @return
     */
    public List<MisEnlaces>  getPageOfLinks(String email, int pageNum){

        List<MisEnlaces> linkMatches = new ArrayList<MisEnlaces>();

        Pageable pageable = PageRequest.of(pageNum -1, 10, Sort.by("_id"));

        //Page<MisEnlaces> misEnlaces = misEnlacesRepo.findAllByEmail(email, PageRequest.of( pageNum, 10));
        Page<MisEnlaces> misEnlaces = misEnlacesRepo.findAllByEmail(email, pageable);

        if (pageNum > misEnlaces.getTotalPages())
        {
            httpSession.setAttribute("pageNum", misEnlaces.getTotalPages() -1);
        }
        else httpSession.setAttribute("pageNum", pageNum - 1);


        httpSession.setAttribute("TotalPages", misEnlaces.getTotalPages());


        misEnlaces.stream().forEach(searchHit->{
            linkMatches.add(searchHit);
        });

        //number of records
        // httpSession.setAttribute("numberOfRecords", linkMatches.size());

        return linkMatches;
    }

    /**
     * Save a link into the index
     * @param enlaces
     */
    public void SaveLink(MisEnlaces enlaces){

        misEnlacesRepo.save(enlaces);

    }

    /**
     * Find by ID and return an Optional with the result
     *
     * @param id
     * @return Optional<MisEnlaces>
     */
    public Optional<MisEnlaces> findById(String id){

        return misEnlacesRepo.findById(id);

    }

    /**
     * get a list of likns
     * @param query
     * @return
     */
    public List<MisEnlaces> processSearch(final String query, String email){

        List<MisEnlaces> linkMatches = new ArrayList<MisEnlaces>();
        //String email="antonio.gialnet@gmail.com";

        log.info("Search with query {}", query);

        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder =
                QueryBuilders
                        .multiMatchQuery(query, "link", "description", "tags")
                        .fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .build();

        // 2. Execute search
        SearchHits<MisEnlaces> linksHits =
                elasticsearchOperations
                        .search(searchQuery, MisEnlaces.class,
                                IndexCoordinates.of(ENLACES_INDEX));

        // 3. Map searchHits to link list

        linksHits.stream().filter(p -> p.getContent().getEmail().contentEquals(email)).limit(10).forEach(searchHit->{
            linkMatches.add(searchHit.getContent());
        });

        // .filter(p -> p.getContent().getEmail()=="antonio.gialnet@gmail.com" )
        //List<MisEnlaces> linkMatchesFilter = linkMatches.stream().filter(p -> p.getEmail().contentEquals(email)).collect(Collectors.toList());
        //System.out.println(linkMatchesFilter.size());

        //return linkMatches.stream().filter(p -> p.getEmail().contentEquals(email)).collect(Collectors.toList());
        return linkMatches;

    }
}
