package com.vivaldi.spring.alyssa.indexcontroller;

import com.vivaldi.spring.alyssa.data.MisEnlaces;
import com.vivaldi.spring.alyssa.data.SearchBox;
import com.vivaldi.spring.alyssa.services.ServiceMisEnlaces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class MisEnlacesController {

    private MisEnlaces misEnlaces;
    private SearchBox searchBox;

    private final ServiceMisEnlaces serviceMisEnlaces;

    public MisEnlacesController(ServiceMisEnlaces serviceMisEnlaces) {
        this.serviceMisEnlaces = serviceMisEnlaces;
    }

    @GetMapping("/addnew")
    public String getaddnew(Model model, HttpSession session){

        log.info("addnew getId '{}'", session.getAttribute("email"));
        misEnlaces = MisEnlaces.builder().build();
        misEnlaces.setEmail((String) session.getAttribute("email"));
        model.addAttribute("MisEnlaces", misEnlaces);
        return "addnew";
    }

    @PostMapping("/addnew")
    public String addnew(@ModelAttribute("MisEnlaces")MisEnlaces misEnlaces, HttpSession session){

        if (misEnlaces==null)
            return "addnew";

        misEnlaces.setEmail((String) session.getAttribute("email"));
        serviceMisEnlaces.SaveLink(misEnlaces);

        return "addnew_success";
    }

    @PostMapping("/grid")
    public String showForm(Model model, @ModelAttribute("searchBox")SearchBox searchBox, HttpSession session){


        List<MisEnlaces> enlaces = serviceMisEnlaces.processSearch(searchBox.getQueryString(), (String) session.getAttribute("email"));

        //enlaces.get(0).
        log.info("number of records '{}'",enlaces.size());
        model.addAttribute("enlaces", enlaces);

        return "linksgrid";
    }

    /**
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/search")
    public String searchLink(Model model, HttpSession session){

        searchBox = SearchBox.builder().build();

        model.addAttribute("SearchBox", searchBox);

        return "searchQuery";
    }

    @GetMapping(value = {"/browser","/browser/{pagNum}" })
    public String browserPage(@PathVariable(required = false) String pagNum, Model model, HttpSession session){

        int Numpag = 0;

        if (pagNum!=null){
            Numpag = Integer.parseInt(String.valueOf(pagNum));
        }

        List<MisEnlaces> enlaces = serviceMisEnlaces.getPageOfLinks((String) session.getAttribute("email"), Numpag);

        //enlaces.get(0).
        log.info("number of records '{}'",enlaces.size());
        model.addAttribute("enlaces", enlaces);

        return "linksgrid_pageable";
    }
}
