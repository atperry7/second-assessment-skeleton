package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.TweetUser;

public interface UserRepository extends JpaRepository<TweetUser, Integer> {
	
	TweetUser findByCredentials_Username(String username);
	TweetUser findByCredentials_UsernameAndCredentials_Password(String username, String password);
}
