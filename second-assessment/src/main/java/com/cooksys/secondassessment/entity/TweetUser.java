package com.cooksys.secondassessment.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class TweetUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Credentials credentials;

	@ManyToOne
	private Profile profile;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp joined;

	@ManyToMany
	private Set<TweetUser> followersOfUser;

	@ManyToMany
	private Set<TweetUser> userFollowing;

	@OneToMany(mappedBy = "author")
	private Set<Tweet> tweets;
	
	private Boolean isActive = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getJoined() {
		return joined;
	}

	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<TweetUser> getFollowersOfUser() {
		return followersOfUser;
	}

	public void setFollowersOfUser(Set<TweetUser> followersOfUser) {
		this.followersOfUser = followersOfUser;
	}

	public Set<TweetUser> getUserFollowing() {
		return userFollowing;
	}

	public void setUserFollowing(Set<TweetUser> userFollowing) {
		this.userFollowing = userFollowing;
	}

	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetUser other = (TweetUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
