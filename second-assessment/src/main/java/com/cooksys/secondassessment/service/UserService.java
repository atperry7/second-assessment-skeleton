package com.cooksys.secondassessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean exists(String username) {
		return userRepository.findByCredentials_username(username).hasContent();
	}
	
	public List<TweetUser> getAll() {
		return userRepository.findAll();
	}

	public TweetUser create(TweetUser user) {
		return userRepository.save(user);
	}

	public TweetUser getUser(String username) {
		List<TweetUser> tweetUsers = userRepository.findByCredentials_username(username).getContent();
		return null;
	}
	
	
}
