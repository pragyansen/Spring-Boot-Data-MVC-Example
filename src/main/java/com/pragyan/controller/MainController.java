package com.pragyan.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pragyan.Spring4MvCandSecurityApplication;
import com.pragyan.model.User;
import com.pragyan.service.UserService;

@Controller
@RequestMapping("/")
public class MainController implements ErrorController{

	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(Spring4MvCandSecurityApplication.class);
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String homepage(){
		return "home";
	}
	
	@RequestMapping(value = "/profile", method=RequestMethod.GET)
	public String profile(Model model) {
		logger.debug("Entering into MainController.profile()");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUser(email);
		model.addAttribute("russian", "Добрый день");
		model.addAttribute("user", user);
		
		logger.debug("Exiting from MainController.profile()");
		return "myprofile";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage(
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout){
		
		logger.debug("Entering into MainController.loginPage()");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		if(error != null){
			model.addObject("error", "error");
		}
		if(logout != null){
			model.addObject("logout", "logout");
		}
		logger.debug("Exiting from MainController.loginPage()");
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
		
		doAutoLogin(user.getEmail(),user.getPassword());
		
		model.setViewName("redirect:/profile");
		return model;
	}
	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	private void doAutoLogin(String username, String password) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
