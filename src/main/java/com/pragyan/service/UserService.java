package com.pragyan.service;

import java.security.NoSuchAlgorithmException;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragyan.model.User;
import com.pragyan.repository.UserRepository;
import com.pragyan.util.PasswordGeneratorUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordGeneratorUtil generator;
	
	public boolean registerNewUser(User user){
		if(repository.exists(user.getEmail().toLowerCase())){
			return false;
		} else {
			user.setRole("USER");
			user.setEmail(user.getEmail().toLowerCase());
			try {
				user.setPassword(generator.generatePassword(user.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			repository.save(user);
			return true;
		}
	}
	
}
