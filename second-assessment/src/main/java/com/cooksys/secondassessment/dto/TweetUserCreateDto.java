package com.cooksys.secondassessment.dto;

import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Profile;

public class TweetUserCreateDto {

	private Credentials credentials;
	private ProfileDto profile;

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

}
