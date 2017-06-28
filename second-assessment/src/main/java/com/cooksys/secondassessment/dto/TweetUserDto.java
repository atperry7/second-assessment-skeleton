package com.cooksys.secondassessment.dto;

import java.util.Date;

public class TweetUserDto {

	private CredentialsNoPwdDto credentials;
	private ProfileDto profile;
	private Date joined;

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	public Date getJoined() {
		return joined;
	}

	public void setJoined(Date joined) {
		this.joined = joined;
	}

	public CredentialsNoPwdDto getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsNoPwdDto credentials) {
		this.credentials = credentials;
	}

}
