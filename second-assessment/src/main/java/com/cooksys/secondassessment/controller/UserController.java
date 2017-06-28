package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.TweetUserCreateDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.dto.TweetUserDto;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.exception.UsernameExistsException;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(getClass());
	private UserService uService;
	private TweetUserMapper tMapper;

	public UserController(UserService uService, TweetUserMapper tMapper) {
		this.uService = uService;
		this.tMapper = tMapper;
	}
	
	//Checks whether or not a given username exists.
	@GetMapping("validate/username/exists/@{username}")
	public boolean exists(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.exists(username);
	}
	
	//Checks whether or not a given username is available.
	@GetMapping("validate/username/available/@{username}")
	public boolean available(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return !uService.exists(username);
	}
	
	//Retrieves all active (non-deleted) users as an array.
	@GetMapping("users")
	public List<TweetUserDto> getAll(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.getAll().stream().filter(user -> user.getIsActive().equals(true)).map(tMapper::tUserDto).collect(Collectors.toList());
	}
	
	@PostMapping("users")
	public TweetUserDto create(@RequestBody TweetUserCreateDto user, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		TweetUser uTweetUser = uService.save(tMapper.toTweetUser(user));
		return tMapper.tUserDto(uTweetUser);
		
	}
	
	@GetMapping("users/@{username}")
	public TweetUserDto getUser(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return tMapper.tUserDto(uService.getUser(username));
	}
	
	@PatchMapping("users/@{username}")
	public TweetUserDto updateUser(@RequestBody TweetUserCreateDto user, @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		TweetUser uTweetUser = uService.save(tMapper.toTweetUser(user));
		return tMapper.tUserDto(uTweetUser);
	}
	
	@DeleteMapping("users/@{username}")
	public TweetUserDto deleteUser(@RequestBody TweetUserCredOnlyDto creds,  @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return tMapper.tUserDto(uService.delete(username, tMapper.toTweetUser(creds)));
	}
	
	//Subscribes the user whose credentials are provided by the request body to 
	//the user whose username is given in the url
	@PostMapping("users/@{username}/follow")
	public void followUser(@RequestBody TweetUserCredOnlyDto creds, @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		uService.followUser(username, creds);
		
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
