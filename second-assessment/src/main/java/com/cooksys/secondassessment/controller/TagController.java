package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashTagNoIdDto;
import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.mapper.HashTagMapper;
import com.cooksys.secondassessment.service.TagService;

@RestController
@RequestMapping("tag")
public class TagController {
	
	private TagService tService;
	private HashTagMapper hMapper;

	public TagController(TagService tService, HashTagMapper hMapper) {
		this.tService = tService;
		this.hMapper = hMapper;
	}

	@GetMapping("validate/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label, HttpServletResponse response) {
		return tService.tagExists(label);
	}
	
	@GetMapping("tags")
	public List<HashTagNoIdDto> getAll(HttpServletResponse response) {
		return tService.getAll().stream()
				.map(hMapper::hashTagNoIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("tags/{label}")
	public List<Tweet> getTag(@PathVariable String label, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}

}
