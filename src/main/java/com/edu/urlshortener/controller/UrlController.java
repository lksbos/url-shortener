package com.edu.urlshortener.controller;

import com.edu.urlshortener.model.dto.UrlDTO;
import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class UrlController {
    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String indexForm(Model model) {
        model.addAttribute("urlDTO", new UrlDTO());
        return "index";
    }

    @PostMapping("/")
    public ModelAndView shortenUrl(@ModelAttribute UrlDTO urlDTO, ModelMap modelMap, HttpServletRequest request) {
        try {
            Url url = urlService.createUrl(urlDTO.getUrl());
            urlDTO.setShortUrl(request.getRequestURL().toString() + url.getId());
            urlDTO.setUrl(url.getUrl());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            urlDTO.setErrorMessage(e.getMessage());
        }

        return new ModelAndView("index", modelMap);
    }

    @GetMapping("/{id}")
    public ModelAndView redirect(@PathVariable("id") String id, ModelMap modelMap) {
        try {
            Url url = urlService.getUrl(id);
            return new ModelAndView("redirect:" + url.getUrl());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            UrlDTO dto = UrlDTO.builder().errorMessage(e.getMessage()).build();
            modelMap.addAttribute("urlDTO", dto);
        }
        return new ModelAndView("index", modelMap);
    }
}
