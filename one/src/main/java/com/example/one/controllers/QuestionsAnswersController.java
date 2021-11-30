package com.example.one.controllers;

import com.example.one.models.Score;
import com.example.one.services.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class QuestionsAnswersController {
    //auto-wiring the Service here
    @Autowired
    QuestionAnswerService questionAnswerService;
    @GetMapping(path="/quiz")
    public String main(Model model) throws IOException, InterruptedException {
        //execute method to retrieve questions
        questionAnswerService.fetchQuestions();
        //set questions to add in the model for the questionsAnswers.html view
        model.addAttribute("allQuestions", questionAnswerService.getAllQuestions());
        //execute method to retrieve character choices
        questionAnswerService.fetchChoices();
        //set character choices to add in the model for the questionsAnswers.html view dropdown list
        model.addAttribute("allChoices", questionAnswerService.getAllChoices());
        Score score = new Score();
        //set Score Model in the view to store selected character choices from view
        model.addAttribute("score", score);
        return "questionsAnswers"; //returns view
    }

    //path for when submit form is sent when clicking on save
    @PostMapping(path="/quiz")
    public String submit(@ModelAttribute("score") Score score, Model model){

        //questionAnswerService.getAllQuestions();
        //questionAnswerService.calculate(score);

        //set questions to add in the model for the questionsAnswers.html view
        model.addAttribute("score", questionAnswerService.calculate(score));
        return "score"; //returns view with final score
    }
}
