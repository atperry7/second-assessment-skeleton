package com.cooksys.secondassessment.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.repository.TweetRepository;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private UserRepository userRepository;
	private TweetRepository tRepository;
	
	public UserService(UserRepository userRepository, TweetUserMapper tMapper, TweetRepository tRepository) {
		this.userRepository = userRepository;
		this.tRepository = tRepository;
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
			tUser.setProfile(user.getProfile());
			
			if (tUser.getCredentials().getPassword().equals(user.getCredentials().getPassword()) 
					&& tUser.getIsActive().equals(false)) {
				tUser.setIsActive(true);
				return userRepository.save(tUser);
				
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
			
			if (userToFollow.getFollowersOfUser().contains(user) && userToFollow.getIsActive().equals(true)) {
				userToFollow.getFollowersOfUser().remove(user);
				save(userToFollow);
			}
			
			if (user.getUserFollowing().contains(userToFollow) && userToFollow.getIsActive().equals(true)) {
				user.getUserFollowing().remove(userToFollow);
				save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
		
	}

	public Set<TweetUser> getFollowers(String username) {
		if (exists(username)) {
			return getUser(username).getFollowersOfUser();
		}
		
		throw new EntityNotFoundException();
	}

	public Set<TweetUser> getUserFollowing(String username) {
		if (exists(username)) {
			return getUser(username).getUserFollowing();
		}
		
		throw new EntityNotFoundException();
	}

	public Set<Tweet> getMentions(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_Username(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			return tweetUser.getTweetsMentionedUser();
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getUserTweets(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_Username(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			return tRepository.findByAuthor_IdOrderByPostedDesc(tweetUser.getId());
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getUsersFeed(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_Username(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			Set<TweetUser> tweetUsers = tweetUser.getFollowersOfUser().stream()
					.filter(user -> user.getIsActive().equals(true))
					.collect(Collectors.toSet());
			
			tweetUsers.add(tweetUser);
			
			List<Tweet> tweetU = new ArrayList<>();
			
			for(TweetUser tweetUser2 : tweetUsers) {
				Iterator<Tweet> tIterator = tweetUser2.getTweets().iterator();
				while(tIterator.hasNext()) {
					tweetU.add(tIterator.next());
				}
			}
			
			return tweetU;
		}
		
		throw new EntityNotFoundException();
	}
	
	
}
