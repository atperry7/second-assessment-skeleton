package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.TweetUser;

public interface UserRepository extends JpaRepository<TweetUser, Integer> {
	
	TweetUser findByCredentials_Username(String username);
	TweetUser findByCredentials_UsernameAndCredentials_Password(String username, String password);	
	TweetUser findByCredentials_UsernameEqualsAndCredentials_PasswordEquals(String username, String password);
	
	List<TweetUser> findAllFollowersOfUserfindByCredentials_Username(String username);

	
}
