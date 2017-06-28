package com.cooksys.secondassessment.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	private Date posted;

	@ManyToOne
	private TweetUser author;

	private String content;

	private Integer inReplyTo;

	private Integer repostOf;

	private Boolean isDeleted = false;

	@ManyToMany
	private Set<TweetUser> mentions;

	@ManyToMany
	private Set<HashTag> labels;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Integer inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Integer getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Integer repostOf) {
		this.repostOf = repostOf;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public TweetUser getAuthor() {
		return author;
	}

	public void setAuthor(TweetUser author) {
		this.author = author;
	}

	public Set<TweetUser> getMentions() {
		return mentions;
	}

	public void setMentions(Set<TweetUser> list) {
		this.mentions = list;
	}

	public Set<HashTag> getLabels() {
		return labels;
	}

	public void setLabels(Set<HashTag> labels) {
		this.labels = labels;
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
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
