package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	TweetWithIdDto tWithIdDto(Tweet t);
	Tweet toTweet(TweetWithIdDto t);

}
