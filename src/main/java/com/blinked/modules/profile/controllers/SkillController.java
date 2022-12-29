package com.blinked.modules.profile.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.OtherSkill;
import com.blinked.modules.profile.entities.Skill;
import com.blinked.modules.profile.entities.TechnicalSkill;
import com.blinked.modules.profile.repositories.OtherSkillRepository;
import com.blinked.modules.profile.repositories.SkillRepository;
import com.blinked.modules.profile.repositories.TechnicalSkillRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Skill Information")
@RequestMapping("/api/skill-info")
public class SkillController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	TechnicalSkillRepository technicalSkillRepository;

	@Autowired
	OtherSkillRepository otherSkillRepository;

	@PostMapping("/technical-skill")
	@Operation(summary = "Save Technical Skill")
	public TechnicalSkill saveTechnicalSkill(@CurrentUser Authorized authorized,
			@RequestBody TechnicalSkill technicalSkill) {

		User user = userRepository.getReferenceById(authorized.getId());

		Skill skill = user.getSkill();

		System.out.println(skill);
		if (skill == null) {
			skill = new Skill();
			skill = skillRepository.save(skill);
			user.setSkill(skill);
//			user=userRepository.save(user);
//			skill=user.getSkill();
		}

		technicalSkill = technicalSkillRepository.save(technicalSkill);

		List<TechnicalSkill> techSkills = skill.getTechnicalSkills();

		if (techSkills == null) {
			techSkills = new ArrayList<TechnicalSkill>();
		}

		techSkills.add(technicalSkill);

		skill.setTechnicalSkills(techSkills);
		user.setSkill(skill);
		userRepository.save(user);

		return technicalSkill;

	}

	@PutMapping("/technical-skill")
	@Operation(summary = "Update Technical Skill")
	public TechnicalSkill updateTechnicalSkill(@CurrentUser Authorized authorized,
			@RequestBody TechnicalSkill technicalSkill) {

		technicalSkillRepository.save(technicalSkill);

		return technicalSkill;

	}

	@GetMapping("/technical-skill/list")
	@Operation(summary = "Get All Technical Skills")
	public List<TechnicalSkill> getAllTechnicalSkills(@CurrentUser Authorized authorized) {
		return userRepository.getAllTechnicalSkills(authorized.getId());
	}

	@Transactional
	@DeleteMapping("/technical-skill/{technicalSkillId}")
	@Operation(summary = "Delete Technical Skill By Id")
	public void deleteTechnicalSkillById(@CurrentUser Authorized authorized,
			@PathVariable("technicalSkillId") Long technicalSkillId) {

		User user = userRepository.getReferenceById(authorized.getId());
		user.getSkill().getTechnicalSkills().remove(technicalSkillRepository.getReferenceById(technicalSkillId));
		userRepository.save(user);
	}

	@PostMapping("/other-skill")
	@Operation(summary = "Save Other Skill")
	public OtherSkill saveOtherSkill(@CurrentUser Authorized authorized, @RequestBody OtherSkill otherSkill) {

		User user = userRepository.getReferenceById(authorized.getId());
		Skill skill = user.getSkill();

		if (skill == null) {
			skill = new Skill();
			skill = skillRepository.save(skill);
			user.setSkill(skill);
		}

		otherSkill = otherSkillRepository.save(otherSkill);

		List<OtherSkill> otherSkills = skill.getOtherSkills();

		if (otherSkills == null) {
			otherSkills = new ArrayList<OtherSkill>();
		}

		otherSkills.add(otherSkill);

		skill.setOtherSkills(otherSkills);
		user.setSkill(skill);
		userRepository.save(user);

		return otherSkill;
	}

	@GetMapping("/other-skill/list")
	@Operation(summary = "Get All Other Skills")
	public List<OtherSkill> getAllOtherSkills(@CurrentUser Authorized authorized) {
		return userRepository.getAllOtherSkills(authorized.getId());
	}

	@DeleteMapping("/other-skill/{otherSkillId}")
	@Operation(summary = "Delete Other Skill")
	public void deleteOtherSkillById(@CurrentUser Authorized authorized,
			@PathVariable("otherSkillId") Long otherSkillId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getSkill().getOtherSkills().remove(otherSkillRepository.getReferenceById(otherSkillId));
		userRepository.save(user);
	}

	@PostMapping("/technical-skill/update/view/{technicalSkillId}")
	@Operation(summary = "Change View Technical Skill")
	public Long changeViewTechnicalSkill(@PathVariable("technicalSkillId") Long technicalSkillId) {

		TechnicalSkill technicalSkill = technicalSkillRepository.getReferenceById(technicalSkillId);

		if (technicalSkill != null) {
			Boolean view = technicalSkill.getView();

			if (view == true) {
				technicalSkill.setView(false);
				technicalSkill = technicalSkillRepository.save(technicalSkill);
				System.out.println("hello1 : " + technicalSkill.getId());
				return technicalSkill.getId();
			} else {
				technicalSkill.setView(true);
				technicalSkill = technicalSkillRepository.save(technicalSkill);
				System.out.println("hello2: " + technicalSkill.getId());
				return technicalSkill.getId();
			}

		}

		return null;

	}

	@PostMapping("/other-skill/update/view/{otherSkillId}")
	@Operation(summary = "Change View Other Skill")
	public Long changeViewOtherSkill(@PathVariable("otherSkillId") Long otherSkillId) {

		OtherSkill otherSkill = otherSkillRepository.getReferenceById(otherSkillId);

		if (otherSkill != null) {
			Boolean view = otherSkill.getView();

			if (view == true) {
				otherSkill.setView(false);
				otherSkill = otherSkillRepository.save(otherSkill);
				System.out.println("hello1 : " + otherSkill.getId());
				return otherSkill.getId();
			} else {
				otherSkill.setView(true);
				otherSkill = otherSkillRepository.save(otherSkill);
				System.out.println("hello2: " + otherSkill.getId());
				return otherSkill.getId();
			}

		}

		return null;

	}

}
