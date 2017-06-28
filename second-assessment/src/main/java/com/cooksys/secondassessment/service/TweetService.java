package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.repository.TweetRepository;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class TweetService {

	private TweetRepository tRepo;
	private UserRepository uRepo;

	public TweetService(TweetRepository tRepo, UserRepository uRepo) {
		this.tRepo = tRepo;
		this.uRepo = uRepo;
	}
	
	public List<Tweet> getAll() {
		return tRepo.findAll(new Sort(Sort.Direction.ASC, "posted")).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.collect(Collectors.toList());
	}

	public Tweet createSimpleTweet(TweetCreateSimpleDto tweet) {
		
		return null;
	}

}
