package com.blinked.modules.profile.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.Lov;
import com.blinked.modules.profile.repositories.LovRepository;
import com.blinked.modules.user.dtos.Authorized;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "LOV Predefined Data")
@RequestMapping("/api/lov")
public class LovController {

	@Autowired
	private LovRepository lovRepository;

	@PostMapping
	@Operation(summary = "Create New Lov")
	@PreAuthorize("hasAnyAuthority('ADM')")
	public Lov create(@CurrentUser Authorized authorized, @RequestBody Lov lov) {
		return lovRepository.save(lov);
	}

	@PutMapping
	@Operation(summary = "Update Lov")
	@PreAuthorize("hasAnyAuthority('ADM')")
	public Lov update(@CurrentUser Authorized authorized, @RequestBody Lov lov) {
		return lovRepository.save(lov);
	}

	@GetMapping("/list/all")
	@Operation(summary = "List All Lovs")
	public List<Lov> listAllLovs() {
		return lovRepository.findAll();
	}

	@GetMapping("/list/lov/{code}")
	@Operation(summary = "List Lov By Code")
	public List<Lov> getLovByCode(@PathVariable("code") String lovCode) {
		return lovRepository.getLovByCode(lovCode);
	}

}
