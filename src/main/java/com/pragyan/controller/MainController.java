package com.pragyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pragyan.model.User;
import com.pragyan.service.UserService;

@Controller
@RequestMapping("/")
public class MainController implements ErrorController{

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String homepage(){
		return "home";
	}
	
	@RequestMapping(value = "/profile", method=RequestMethod.GET)
	public String profile(Model model) {
		model.addAttribute("russian", "Добрый день");
		return "myprofile";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage(
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		if(error != null){
			model.addObject("error", "error");
		}
		if(logout != null){
			model.addObject("logout", "logout");
		}
		return model;
	}
	
	@RequestMapping("/error")
	public String errorView(){
		return "error";
	}
	
	@RequestMapping(value = "/signup", method=RequestMethod.GET)
	public String signup(){
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ModelAndView signupComplete(User user){
		
		if(user != null){
			userService.registerNewUser(user);
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/");
		return model;
	}
	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
}
