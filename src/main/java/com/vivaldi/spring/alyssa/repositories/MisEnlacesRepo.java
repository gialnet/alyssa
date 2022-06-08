package com.vivaldi.spring.alyssa.repositories;

import com.vivaldi.spring.alyssa.data.MisEnlaces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MisEnlacesRepo extends ElasticsearchRepository<MisEnlaces, String> {

}
