package com.cooksys.secondassessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.repository.HashTagRepository;

@Service
public class TagService {

	private HashTagRepository hRepo;

	public TagService(HashTagRepository hRepo) {
		this.hRepo = hRepo;
	}
	
	public boolean tagExists(String label) {
		return hRepo.findByLabel(label) != null;
	}

	public List<HashTag> getAll() {
		return hRepo.findAll();
	}

}
