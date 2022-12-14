package com.blinked.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.model.Template;
import com.blinked.model.User;
import com.blinked.repositories.TemplateRepository;
import com.blinked.repositories.UserRepository;

@RestController
public class TemplateRestController {

	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/saveTemplateToSystem")
	public Template saveTemplateToSystem(@RequestBody Template template) {
		return templateRepository.save(template);
	}
	
	@PostMapping("/saveTemplateForUser/{uId}/{templateId}")
	public Template saveTemplateForUser( @PathVariable("templateId") Long templateId, @PathVariable("uId") Long uId) {
		
		User user = userRepository.getOne(uId);
		
		Template template = templateRepository.getOne(templateId);
		
		user.setTemplate( template );
		
		userRepository.save(user);
		
		return template;
	}
	
	@GetMapping("/getTemplateByUserId/{uId}")
	public Template getTemplateByUserId( @PathVariable("uId") Long uId){
		return userRepository.getTemplateByUserId(uId);
	}
	
	
	@GetMapping("/getAllTemplates")
	public List<Template> getAllTemplates(){
		return templateRepository.findAll();
	}
	
	
	
}
