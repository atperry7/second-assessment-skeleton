package com.cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.entity.HashTag;

@RestController
@RequestMapping("tag")
public class TagController {

	@GetMapping("validate/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tags")
	public List<HashTag> getAll(HttpServletResponse response) {
		throw new NotYetImplementedException();
	}
	
	@GetMapping("tags/{label}")
	public HashTag getTag(@PathVariable String label, HttpServletResponse response) {
		throw new NotYetImplementedException();
	}

}
