package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetSimpleDto;
import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.service.TweetService;

@RestController
@RequestMapping("tweet")
public class TweetController {
	
	private TweetService tService;
	private TweetMapper tMapper;

	public TweetController(TweetService tService, TweetMapper tMapper) {
		this.tService = tService;
		this.tMapper = tMapper;
	}
	
	@GetMapping("tweets")
	public List<TweetWithIdDto> getAll(HttpServletResponse response) {
		return tService.getAll()
				.stream()
				.map(tMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@PostMapping("tweets")
	public TweetSimpleDto createSimpTweet(@RequestBody TweetCreateSimpleDto tweet, HttpServletResponse response) {
		return tMapper.tweetSimpleDto(tService.createSimpleTweet(tweet));
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
