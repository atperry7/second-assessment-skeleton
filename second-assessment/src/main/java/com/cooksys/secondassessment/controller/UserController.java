package com.cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private UserService uService;

	public UserController(UserService uService) {
		this.uService = uService;
	}
	
	//Checks whether or not a given username exists.
	@GetMapping("validate/username/exists/@{username}")
	public boolean exists(@PathVariable String username, HttpServletResponse response) {
		return uService.exists(username);
	}
	
	//Checks whether or not a given username is available.
	@GetMapping("validate/username/available/@{username}")
	public boolean available(@PathVariable String username, HttpServletResponse response) {
		return !uService.exists(username);
	}
	
	//Retrieves all active (non-deleted) users as an array.
	@GetMapping("users")
	public List<TweetUser> getAll() {
		return uService.getAll();
	}
	
	@PostMapping("users")
	public TweetUser create(@RequestBody TweetUser user, HttpServletResponse response) {
		return uService.create(user);
	}
	
	@GetMapping("users/@{username}")
	public TweetUser getUser(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@PatchMapping("users/@{username}")
	public TweetUser updateUser(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@DeleteMapping("users/@{username}")
	public TweetUser deleteUser(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	//Subscribes the user whose credentials are provided by the request body to 
	//the user whose username is given in the url
	@PostMapping("users/@{username}/follow")
	public void followUser(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@PostMapping("users/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("users/@{username}/feed")
	public List<Tweet> getUsersFeed(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("users/@{username}/tweets")
	public List<Tweet> getUserTweets(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("users/@{username}/mentions")
	public List<Tweet> getUserMentions(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("users/@{username}/followers")
	public List<TweetUser> getUserFollowers(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("users/@{username}/following")
	public List<TweetUser> getUserFollowing(@PathVariable String username, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
}
