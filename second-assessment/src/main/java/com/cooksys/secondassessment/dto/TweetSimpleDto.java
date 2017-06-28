package com.cooksys.secondassessment.dto;

import java.util.Date;

public class TweetSimpleDto {
	private Integer id;
	private String content;
	private Date posted;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
