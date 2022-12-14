package com.blinked.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.model.PredefinedCity;
import com.blinked.model.PredefinedCountry;
import com.blinked.model.PredefinedCourse;
import com.blinked.model.PredefinedFieldOfStudy;
import com.blinked.model.PredefinedJobTitle;
import com.blinked.model.PredefinedSkill;
import com.blinked.model.PredefinedState;
import com.blinked.repositories.PredefinedCityRepository;
import com.blinked.repositories.PredefinedCountryRepository;
import com.blinked.repositories.PredefinedCourseRepository;
import com.blinked.repositories.PredefinedFieldOfStudyRepository;
import com.blinked.repositories.PredefinedJobTitleRepository;
import com.blinked.repositories.PredefinedSkillRespository;
import com.blinked.repositories.PredefinedStateRepository;

@RestController
public class PredefinedDataRestController {

	@Autowired
	private PredefinedCityRepository predefinedCityRepository;
	
	@Autowired
	private PredefinedStateRepository predefinedStateRepository;
	
	@Autowired
	private PredefinedCountryRepository predefinedCountryRepository;
	
	@Autowired
	private PredefinedCourseRepository predefinedCourseRepository;
	
	@Autowired
	private PredefinedSkillRespository predefinedSkillRespository;
	
	@Autowired
	private PredefinedFieldOfStudyRepository predefinedFieldOfStudyRepository;
	
	@Autowired
	private PredefinedJobTitleRepository predefinedJobTitleRepository;
	
	
	@GetMapping("/getAllSkill")
	public List<PredefinedSkill> getAllSkill() {
		return predefinedSkillRespository.findAll();
	}
	
	@GetMapping("/getAllCourse")
	public List<PredefinedCourse> getAllCourse() {
		return predefinedCourseRepository.findAll();
	}
	
	@GetMapping("/getAllJobTitle")
	public List<PredefinedJobTitle> getAllJobTitle() {
		return predefinedJobTitleRepository.findAll();
	}
	
	@GetMapping("/getAllFieldOfStudy")
	public List<PredefinedFieldOfStudy> getAllFieldOfStudy() {
		return predefinedFieldOfStudyRepository.findAll();
	}
	
	@GetMapping("/getAllCountry")
	public List<PredefinedCountry> getAllCountry() {
		return predefinedCountryRepository.findAll();
	}
	
	@GetMapping("/getStatesByCountryId/{cId}")
	public List<PredefinedState> getStatesByCountryId(@PathVariable("cId") Long cId){
		return predefinedStateRepository.getStatesByCountryId(cId);
	}
	
	@GetMapping("/getCitiesByStateId/{sId}")
	public List<PredefinedCity> getCitiesByStateId(@PathVariable("sId") Long sId){
		return predefinedCityRepository.getStatesByStateId(sId);
	}
	
}
