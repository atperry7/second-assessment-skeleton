package com.cooksys.secondassessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.exception.UsernameExistsException;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean exists(String username) {
		return userRepository.findByCredentials_Username(username) != null;
	}
	
	public List<TweetUser> getAll() {
		return userRepository.findAll();
	}

	public TweetUser save(TweetUser user) {
		if (user.getCredentials().getUsername() == null || user.getCredentials().getPassword() == null
				|| user.getProfile().getEmail() == null) {
			throw new InvalidArgumentPassedException();
		}
		
		if (exists(user.getCredentials().getUsername())) {
			
			TweetUser tUser = getUser(user.getCredentials().getUsername());
			
			if (tUser.getCredentials().getPassword().equals(user.getCredentials().getPassword()) 
					&& tUser.getIsActive().equals(false)) {
				tUser.setIsActive(true);
				return tUser;
				
			} else if (tUser.getCredentials().getPassword().equals(user.getCredentials().getPassword()) 
					&& tUser.getIsActive().equals(true)) {
				return userRepository.save(tUser);
			} 
		} else {
			throw new UsernameExistsException();
		}
		
		return userRepository.save(user);
	}

	public TweetUser getUser(String username) {
		return userRepository.findByCredentials_Username(username);

	}

	public TweetUser delete(String username, TweetUser creds) {
		TweetUser user = userRepository.findByCredentials_UsernameAndCredentials_Password(creds.getCredentials().getUsername(), creds.getCredentials().getPassword());	
		if (user != null && username.equals(user.getCredentials().getUsername())) {
			user.setIsActive(false);
		} else {
			throw new EntityNotFoundException();
		}
		
		return user;
	}

	public void followUser(String username, TweetUserCredOnlyDto creds) {
		TweetUser user = userRepository.findByCredentials_UsernameAndCredentials_Password(creds.getCredentials().getUsername(), creds.getCredentials().getPassword());
		if (user != null && exists(username)) {
			
			TweetUser userToFollow = getUser(username);
			
			if (!userToFollow.getFollowersOfUser().contains(user)) {
				userToFollow.getFollowersOfUser().add(user);
				save(userToFollow);
			}
			
			if (!user.getUserFollowing().contains(userToFollow)) {
				user.getUserFollowing().add(userToFollow);
				save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
	}
	
	
}
