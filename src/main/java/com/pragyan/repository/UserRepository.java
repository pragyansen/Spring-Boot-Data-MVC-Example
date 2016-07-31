package com.pragyan.repository;

import org.springframework.data.repository.CrudRepository;

import com.pragyan.model.User;

public interface UserRepository extends CrudRepository<User, String>{

}
