package com.edu.urlshortener.controller;

import com.edu.urlshortener.model.dto.UrlDTO;
import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.service.UrlService;
import com.edu.urlshortener.util.IdUtils;
import com.edu.urlshortener.util.UrlUtils;
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
    private static final String INDEX_PAGE = "index";
    private static final String URL_DTO_ATTR_NAME = "urlDTO";

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String indexForm(Model model) {
        model.addAttribute(URL_DTO_ATTR_NAME, new UrlDTO());
        return INDEX_PAGE;
    }

    @PostMapping("/")
    public ModelAndView shortenUrl(@ModelAttribute UrlDTO urlDTO, ModelMap modelMap, HttpServletRequest request) {
        try {
            Url url = urlService.createUrl(urlDTO.getUrl());
            urlDTO.setShortUrl(request.getRequestURL().toString() + IdUtils.idToUrlHash(url.getId()));
            urlDTO.setUrl(url.getUrl());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            urlDTO.setErrorMessage(e.getMessage());
        }

        return new ModelAndView(INDEX_PAGE, modelMap);
    }

    @GetMapping("/{id}")
    public ModelAndView redirect(@PathVariable("id") String id, ModelMap modelMap) {
        try {
            UrlDTO dto = urlService.getUrl(id);
            return new ModelAndView("redirect:" + dto.getUrl());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            UrlDTO dto = UrlDTO.builder().errorMessage(e.getMessage()).build();
            modelMap.addAttribute(URL_DTO_ATTR_NAME, dto);
        }
        return new ModelAndView(INDEX_PAGE, modelMap);
    }
}
