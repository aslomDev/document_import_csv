package com.uz.controller;


import com.uz.service.CsvService;
import com.uz.service.Sorted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping()
public class CsvController {

    @Autowired private CsvService csvService;

//    @GetMapping
//    public String csvPage(Model model){
//
//            model.addAttribute("form", csvService.getForm());
//
//        return "form";
//    }

    @GetMapping("/")
    public String csvPage(@ModelAttribute Sorted sort, Model model){

        if (sort.getSort() != null && sort.getSort().equals("id")){
            model.addAttribute("form", csvService.orderById());
        }else {
            model.addAttribute("form", csvService.getForm());
        }

        return "form";
    }

    @PostMapping(value = "/add")
    public String csvPagePost(@RequestBody MultipartFile file, ModelMap modelMap){
        modelMap.addAttribute("form", csvService.makeDb(file));

        return "redirect:/";
//        return "form";
    }

    @PostMapping(value = "/delete")
    public String csvPagePost(Model model){
        csvService.delete();
        model.addAttribute("form");

        return "redirect:/";
//        return "form";
    }



}
