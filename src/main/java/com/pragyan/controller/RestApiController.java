package com.pragyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pragyan.model.User;
import com.pragyan.service.UserService;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	UserService userService;


	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public ResponseEntity<User> profile(){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUser(email);
		if(null == user){
			user.setPassword(null);
			user.setRole(null);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ResponseEntity<String> signup(@RequestBody User user){
		if(null == user){
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
		boolean status = userService.registerNewUser(user);
		if(status){
			return new ResponseEntity<String>("Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error", HttpStatus.CONFLICT);
		}

	}


	@RequestMapping(value="/user/{userId}/profile", method=RequestMethod.GET)
	public ResponseEntity<User> profileOfUser(@PathVariable String userId){
		if(null != userId){
			User user = userService.findUser(userId);
			if(null == user){
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			} else {
				user.setPassword(null);
				user.setRole(null);
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} else return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}


}
