//package com.webtask.springboot.controller;
//
//import com.webtask.springboot.domain.Task;
//import com.webtask.springboot.domain.User;
//import com.webtask.springboot.dto.RegistrationDto;
//import com.webtask.springboot.service.TaskService;
//import com.webtask.springboot.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class MainpageController {
//
//
//    @Autowired
//    TaskService taskService;
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/")
//    public String landingPage(){
//        return "redirect:/login";
//    }
//
//    @GetMapping("/mainpage")
//    public String welcome(Model model){
//        System.out.println("We here");
////        String username = SecurityUtil.getSessionUser();
//        String username = "user1";
//        System.out.println("Username: " + username);
//        User user = userService.findUserByUsername(username);
//        List<Task> tasks = taskService.findByUser(user);
//        model.addAttribute("tasks", tasks);
//        return "mainpage";
//    }
//
//    @PostMapping("/mainpage")
//    public String newTask(String task){
//        String username = "user1";
//        User user = userService.findUserByUsername(username);
//
//        //either add or delete
//        taskService.newTask(task, user);
//        return "redirect:/mainpage";
//    }
//
//    @GetMapping("/login")
//    public String showLogin(){
//        System.out.println("COCK");
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String showRegister(Model model){
//        RegistrationDto user = new RegistrationDto();
//        model.addAttribute("user", user);
//        System.out.println("Before");
//        return "register";
//    }
//
//    @PostMapping("/register/save")
//    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
//                           BindingResult result, Model model){
//        System.out.println("Masuks");
//        User existingUser = userService.findUserByUsername(user.getUsername());
//        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
//            System.out.println("eepy");
//            return "redirect:/register?fail";
//        }
//        if(result.hasErrors()){
//            model.addAttribute("user", user);
//            return "register";
//        }
//        System.out.println("Saving user");
//        userService.saveUser(user);
//        return "redirect:/login?success";
//    }
//}
