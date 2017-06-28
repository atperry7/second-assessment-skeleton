package com.cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.service.TagService;

@RestController
@RequestMapping("tag")
public class TagController {
	
	private TagService tService;

	public TagController(TagService tService) {
		this.tService = tService;
	}

	@GetMapping("validate/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label, HttpServletResponse response) {
		return tService.tagExists(label);
	}
	
	@GetMapping("tags")
	public List<HashTag> getAll(HttpServletResponse response) {
		return tService.getAll();
	}
	
	@GetMapping("tags/{label}")
	public List<Tweet> getTag(@PathVariable String label, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}

}
