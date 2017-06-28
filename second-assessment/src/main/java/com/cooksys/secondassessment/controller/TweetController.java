package com.cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.service.TweetService;

@RestController
@RequestMapping("tweet")
public class TweetController {
	
	private TweetService tService;

	public TweetController(TweetService tService) {
		this.tService = tService;
	}
	
	@GetMapping("tweets")
	public List<Tweet> getAll(HttpServletResponse response) {
		return tService.getAll();
	}
	
	@PostMapping("tweets")
	public Tweet createSimpTweet(HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}")
	public Tweet getTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@DeleteMapping("tweets/{id}")
	public Tweet deleteTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@PostMapping("tweets/{id}/like")
	public void followTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@PostMapping("tweets/{id}/reply")
	public Tweet replyToTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@PostMapping("tweets/{id}/repost")
	public Tweet repostTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/tags")
	public List<HashTag> getTagsForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/likes")
	public List<TweetUser> getUsersForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/context")
	public void getContext(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/replies")
	public List<Tweet> getRepliesToTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/reposts")
	public List<Tweet> getRepostsForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tweets/{id}/mentions")
	public List<TweetUser> getUserMentionedInTweetById(@PathVariable Integer id, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
}
