package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.repository.TweetRepository;

@Service
public class TweetService {

	private TweetRepository tRepo;

	public TweetService(TweetRepository tRepo) {
		this.tRepo = tRepo;
	}
	
	public List<Tweet> getAll() {
		return tRepo.findAll(new Sort(Sort.Direction.ASC, "posted")).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.collect(Collectors.toList());
	}

}
