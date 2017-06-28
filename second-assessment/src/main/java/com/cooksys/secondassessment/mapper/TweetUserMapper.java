package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.TweetUserCreateDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.dto.TweetUserDto;
import com.cooksys.secondassessment.entity.TweetUser;

@Mapper(componentModel = "spring", uses = {CredentialsMapper.class, ProfileMapper.class})
public interface TweetUserMapper {
	
	TweetUser toTweetUser(TweetUserDto t);
	TweetUserDto tUserDto(TweetUser t);
	
	TweetUser toTweetUser(TweetUserCreateDto t);
	TweetUserCreateDto tCreateDto(TweetUser t);
	
	TweetUser toTweetUser(TweetUserCredOnlyDto t);
	TweetUserCredOnlyDto tweetUserCredOnlyDto(TweetUser t);
}
