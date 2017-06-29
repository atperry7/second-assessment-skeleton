package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.repository.HashTagRepository;
import com.cooksys.secondassessment.repository.TweetRepository;

@Service
public class TweetService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TweetRepository tRepo;
	private UserService uService;
	private HashTagRepository hRepo;

	public TweetService(TweetRepository tRepo, UserService uService, HashTagRepository hRepo) {
		this.tRepo = tRepo;
		this.uService = uService;
		this.hRepo = hRepo;
	}
	
	public List<Tweet> getAll() {
		return tRepo.findAll();
	}

	public Tweet createSimpleTweet(TweetCreateSimpleDto tweet) {
		TweetUser tweetUser = uService.checkUserCredentials(tweet.getCredentials());
		
		if (tweetUser != null) {
			Tweet tweetCrea = new Tweet();
			tweetCrea.setContent(tweet.getContent());
			tweetCrea.setAuthor(tweetUser);
			tweetCrea.setMentions(parseUsersFromTweet(tweet.getContent(), tweetCrea.getMentions()));
			tweetCrea.setLabels(parseHashTagsFromTweet(tweet.getContent(), tweetCrea.getLabels()));
			return tRepo.save(tweetCrea);
		}
		
		return null;
	}
	
	private Set<TweetUser> parseUsersFromTweet(String contents, Set<TweetUser> tweetUsers) {	
		String[] split = contents.split(" ");
		
		for(String sp : split) {
			if (sp.startsWith("@")) {
				String username = sp.substring(1);
				log.debug(username);
				TweetUser tweetUser = uService.getUser(username);
				if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
					tweetUsers.add(tweetUser);
				}
			}
		}
		
		return tweetUsers;
	}
	
	private Set<HashTag> parseHashTagsFromTweet(String contents, Set<HashTag> hashTags) {
		String[] split = contents.split(" ");
		
		for(String sp : split) {
			if (sp.startsWith("#")) {
				String label = sp.substring(1);
				log.debug(label);
				if (hRepo.findByLabelEquals(label) != null) {
					HashTag hashTag = hRepo.findByLabel(label);
					hashTags.add(hRepo.save(hashTag));
				} else {
					HashTag hashTag = new HashTag();
					hashTag.setLabel(label);
					hashTags.add(hRepo.save(hashTag));
				}
			}
		}
		
		return hashTags;
	}

	public Tweet getById(Integer id) {
		return tRepo.findOne(id);
	}

	public Tweet deleteById(Integer id) {
		if (tRepo.exists(id)) {
			Tweet tweet = getById(id);
			tweet.setIsDeleted(true);
			return tweet;
		}
		return null;
	}

	public Set<HashTag> getTagsFromTweet(Integer id) {
		return tRepo.findOne(id).getLabels();
	}

	public Set<TweetUser> getUsersMentioned(Integer id) {
		return tRepo.findOne(id).getMentions();
	}

	public void likeTweetById(TweetUserCredOnlyDto creds, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(creds.getCredentials());
		
		Tweet tweet = tRepo.findOne(id);
		
		if (tweetUser != null && tweet != null) {
			tweetUser.getLikedTweets().add(tweet);
			uService.save(tweetUser);
		}
	
	}

	public Set<TweetUser> getLikesForTweetById(Integer id) {
		return tRepo.getOne(id).getUsersWhoLiked();
	}

	public Tweet replyToTweetById(TweetCreateSimpleDto simpleDto, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(simpleDto.getCredentials());
		Tweet replyToTweet = getById(id);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true) && replyToTweet.getIsDeleted().equals(false)) {
			Tweet tweet = createSimpleTweet(simpleDto);
			tweet.setInReplyTo(replyToTweet);
			return tRepo.save(tweet);
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getDirectReplies(Integer id) {
		return tRepo.findByInReplyTo_IdOrderByPostedDesc(id);
	}

	public Tweet repostTweetById(TweetUserCredOnlyDto creds, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(creds.getCredentials());
		Tweet tweetToRepost = tRepo.findOne(id);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true) && tweetToRepost.getIsDeleted().equals(false)) {
			TweetCreateSimpleDto tweetCreateSimpleDto = new TweetCreateSimpleDto();
			tweetCreateSimpleDto.setCredentials(tweetUser.getCredentials());
			tweetCreateSimpleDto.setContent(tweetToRepost.getContent());
			
			Tweet tweet = createSimpleTweet(tweetCreateSimpleDto);
			tweet.setRepostOf(tweetToRepost);
			return tRepo.save(tweet);
		}
		
		throw new EntityNotFoundException();
	}
	
	public List<Tweet> getDirectReposts(Integer id) {
		return tRepo.findByRepostOf_IdOrderByPostedDesc(id);
	}
}
