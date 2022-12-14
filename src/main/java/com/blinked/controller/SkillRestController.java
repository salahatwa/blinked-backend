package com.blinked.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.model.OtherSkill;
import com.blinked.model.Skill;
import com.blinked.model.TechnicalSkill;
import com.blinked.model.User;
import com.blinked.repositories.OtherSkillRepository;
import com.blinked.repositories.SkillRepository;
import com.blinked.repositories.TechnicalSkillRepository;
import com.blinked.repositories.UserRepository;

@RestController
public class SkillRestController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	TechnicalSkillRepository technicalSkillRepository;
	
	@Autowired
	OtherSkillRepository otherSkillRepository;
	
	@PostMapping("/saveTechnicalSkill/{uId}")
	public TechnicalSkill saveTechnicalSkill(@RequestBody TechnicalSkill technicalSkill, @PathVariable("uId") Long uId) {
		
		System.out.println(technicalSkill.getName());
		
		User user = userRepository.getOne(uId);
		Skill skill = user.getSkill();
		
		if(skill == null) {
			skill = new Skill();
			skill = skillRepository.save(skill);
			user.setSkill(skill);
		}
		
		technicalSkill = technicalSkillRepository.save(technicalSkill);
		
		List<TechnicalSkill> techSkills = skill.getTechnicalSkills();
		
		if(techSkills==null) {
			techSkills = new ArrayList<TechnicalSkill>();
		}
		
		techSkills.add(technicalSkill);
		
		userRepository.save(user);
		
		System.out.println(technicalSkill.getId());
		

		
		return technicalSkill;
		
	}
	
	@GetMapping("/getAllTechnicalSkill/{uId}")
	public List<TechnicalSkill> getAllTechnicalSkills(@PathVariable("uId") Long uId){
		return userRepository.getAllTechnicalSkills(uId);
	}
	
	@DeleteMapping("/deleteTechnicalSkill/{technicalSkillId}")
	public void deleteTechnicalSkillById(@PathVariable("technicalSkillId") Long technicalSkillId) {
		technicalSkillRepository.deleteById(technicalSkillId);
	}
	
	@PostMapping("/saveOtherSkill/{uId}")
	public OtherSkill saveOtherSkill(@RequestBody OtherSkill otherSkill, @PathVariable("uId") Long uId) {
		
		User user = userRepository.getOne(uId);
		Skill skill = user.getSkill();
		
		if(skill == null) {
			skill = new Skill();
			skill = skillRepository.save(skill);
			user.setSkill(skill);
		}
		
		otherSkill = otherSkillRepository.save(otherSkill);
		
		List<OtherSkill> otherSkills = skill.getOtherSkills();
		
		if(otherSkills==null) {
			otherSkills = new ArrayList<OtherSkill>();
		}

		
		otherSkills.add(otherSkill);
		
		userRepository.save(user);
		
		
		
		return otherSkill;
	}
	
	@GetMapping("/getAllOtherSkill/{uId}")
	public List<OtherSkill> getAllOtherSkills(@PathVariable("uId") Long uId){
		return userRepository.getAllOtherSkills(uId);
	}
	
	@DeleteMapping("/deleteOtherSkill/{otherSkillId}")
	public void deleteOtherSkillById(@PathVariable("otherSkillId") Long otherSkillId) {
		otherSkillRepository.deleteById(otherSkillId);
	}
	
}
