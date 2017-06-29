package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.repository.HashTagRepository;
import com.cooksys.secondassessment.repository.TweetRepository;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class TweetService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TweetRepository tRepo;
	private UserRepository uRepo;
	private HashTagRepository hRepo;

	public TweetService(TweetRepository tRepo, UserRepository uRepo, HashTagRepository hRepo) {
		this.tRepo = tRepo;
		this.uRepo = uRepo;
		this.hRepo = hRepo;
	}
	
	public List<Tweet> getAll() {
		return tRepo.findAll(new Sort(Sort.Direction.ASC, "posted")).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.collect(Collectors.toList());
	}

	public Tweet createSimpleTweet(TweetCreateSimpleDto tweet) {
		TweetUser tweetUser = uRepo
				.findByCredentials_UsernameAndCredentials_Password(
						tweet.getCredentials().getUsername(), tweet.getCredentials().getPassword());
		
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
				if (uRepo.findByCredentials_UsernameAndIsActiveEquals(username, true) != null) {
					tweetUsers.add(uRepo.findByCredentials_Username(username));
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
		TweetUser tweetUser = uRepo
				.findByCredentials_UsernameAndCredentials_Password(
						creds.getCredentials().getUsername(), creds.getCredentials().getPassword());
		Tweet tweet = tRepo.findOne(id);
		
		if (tweetUser != null && tweet != null) {
			tweetUser.getLikedTweets().add(tweet);
			uRepo.save(tweetUser);
		}
	
	}

	public Set<TweetUser> getLikesForTweetById(Integer id) {
		return tRepo.getOne(id).getUsersWhoLiked();
	}
}
