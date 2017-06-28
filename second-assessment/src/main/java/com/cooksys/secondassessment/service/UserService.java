package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.exception.UsernameExistsException;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository, TweetUserMapper tMapper) {
		this.userRepository = userRepository;
	}

	public boolean exists(String username) {
		return userRepository.findByCredentials_UsernameExists(username);
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
		TweetUser user = userRepository
				.findByCredentials_UsernameAndCredentials_Password(
						creds.getCredentials().getUsername(), creds.getCredentials().getPassword());
		
		if (user != null && exists(username) && user.getIsActive().equals(true)) {
			
			TweetUser userToFollow = getUser(username);
			
			if (!userToFollow.getFollowersOfUser().contains(user) && userToFollow.getIsActive().equals(true)) {
				userToFollow.getFollowersOfUser().add(user);
				save(userToFollow);
			}
			
			if (!user.getUserFollowing().contains(userToFollow) && userToFollow.getIsActive().equals(true)) {
				user.getUserFollowing().add(userToFollow);
				save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
	}

	public void unfollowUser(String username, TweetUserCredOnlyDto creds) {
		TweetUser user = userRepository
				.findByCredentials_UsernameAndCredentials_Password(
						creds.getCredentials().getUsername(), creds.getCredentials().getPassword());
		
		if (user != null && exists(username) && user.getIsActive().equals(true)) {
			
			TweetUser userToFollow = getUser(username);
			
			if (!userToFollow.getFollowersOfUser().contains(user) && userToFollow.getIsActive().equals(true)) {
				userToFollow.getFollowersOfUser().remove(user);
				save(userToFollow);
			}
			
			if (!user.getUserFollowing().contains(userToFollow) && userToFollow.getIsActive().equals(true)) {
				user.getUserFollowing().remove(userToFollow);
				save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
		
	}

	public List<TweetUser> getFollowers(String username) {
		
		if (exists(username)) {
			return getUser(username).getFollowersOfUser().stream()
					.filter(user -> user.getIsActive().equals(true))
					.collect(Collectors.toList());
		}
		
		throw new EntityNotFoundException();
	}

	public List<TweetUser> getUserFollowing(String username) {
		if (exists(username)) {
			return getUser(username).getUserFollowing().stream()
					.filter(user -> user.getIsActive().equals(true))
					.collect(Collectors.toList());
		}
		
		throw new EntityNotFoundException();
	}
	
	
}
