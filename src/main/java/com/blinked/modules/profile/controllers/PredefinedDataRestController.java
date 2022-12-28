package com.blinked.modules.profile.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.profile.entities.PredefinedCity;
import com.blinked.modules.profile.entities.PredefinedCountry;
import com.blinked.modules.profile.entities.PredefinedCourse;
import com.blinked.modules.profile.entities.PredefinedFieldOfStudy;
import com.blinked.modules.profile.entities.PredefinedJobTitle;
import com.blinked.modules.profile.entities.PredefinedSkill;
import com.blinked.modules.profile.entities.PredefinedState;
import com.blinked.modules.profile.repositories.PredefinedCityRepository;
import com.blinked.modules.profile.repositories.PredefinedCountryRepository;
import com.blinked.modules.profile.repositories.PredefinedCourseRepository;
import com.blinked.modules.profile.repositories.PredefinedFieldOfStudyRepository;
import com.blinked.modules.profile.repositories.PredefinedJobTitleRepository;
import com.blinked.modules.profile.repositories.PredefinedSkillRespository;
import com.blinked.modules.profile.repositories.PredefinedStateRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "LOV")
@RequestMapping("/api/lov")
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
