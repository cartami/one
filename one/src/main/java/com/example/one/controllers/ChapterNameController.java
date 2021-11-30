package com.example.one.controllers;

import com.example.one.services.ChapterNameService;
import com.example.one.services.OneDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller //makes this as Spring controller so we can render an html UI
public class ChapterNameController {
    //auto-wiring the Service here
    @Autowired
    ChapterNameService ChapterNameService;

    /*
    Makes this the root URL Spring Boot knows to do this with the Thymeleaf dependency
    when you access a URL and call a controller you have opportunity here to fetch data and set it in the context of
    whatever it is rendering on the page(Model)
    */
    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public String chapterName(Model model, @RequestParam(value="chapterName") String chapterName) throws IOException, InterruptedException {

        //System.out.println("nothing yet"+ chapterName);
        //model.addAttribute("chapterName", chapterName);
        ChapterNameService.fetchOneData(chapterName);
        model.addAttribute("allChapterNames", ChapterNameService.getAllChapterNames());
        return "chapterName"; //returns chapterName.html for the view
    }
}
