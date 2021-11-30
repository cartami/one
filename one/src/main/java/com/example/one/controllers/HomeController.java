package com.example.one.controllers;

import com.example.one.services.OneDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


//all methods in controller return a rest response it has to be converted into a json response and vice versa
//@RestController
@Controller //makes this as Spring controller so we can render home.html
public class HomeController {

    //auto-wiring the Service here
    @Autowired
    OneDataService oneDataService;

    /*
    Makes this the root URL Spring Boot knows to do this with the Thymeleaf dependency
    when you access a URL and call a controller you have opportunity here to fetch data and set it in the context of
    whatever it is rendering on the page(Model) and from the page you can access things from the Model
    */
    @GetMapping("/") //root URL
    public String home(Model model) throws IOException, InterruptedException{

        //set attributes for model chapter list in the view
        model.addAttribute("allChapterNames", oneDataService.getAllChapterNames());
        return "home"; //returns home.html for the view
    }


}
