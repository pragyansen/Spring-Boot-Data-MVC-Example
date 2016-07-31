package com.pragyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pragyan.model.User;
import com.pragyan.service.UserService;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	UserService userService;
	
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(){
		return "data";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ResponseEntity<String> signup(@RequestBody User user){
		userService.registerNewUser(user);
		return new ResponseEntity<String>("Success", HttpStatus.CREATED);
		
	}
	
}
