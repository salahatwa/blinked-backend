package com.blinked.modules.user.controllers;

import static com.blinked.modules.core.response.Responses.ok;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.dtos.RoleInformation;
import com.blinked.modules.user.repositories.RoleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Roles")
@RequestMapping("/api/roles")
//@PreAuthorize("hasAnyAuthority('ADM')")
public class RolesController {

	@Autowired
	private RoleRepository repository;

	@GetMapping
	@Operation(summary = "Returns a list of roles")
	public ResponseEntity<List<RoleInformation>> index() {
		return ok(repository.findAll().stream().map(RoleInformation::new).collect(Collectors.toList()));
	}
	
	
//	
	
	
	@GetMapping(value = "/test")
	@Operation(summary = "Check User Id sample")
	public ResponseEntity<List<RoleInformation>> test(@CurrentUser Authorized authorized) {
		System.out.println("User Id:"+authorized.getId());
		System.out.println(authorized.getAuthorities());
		
		
		return ok(repository.findAll().stream().map(RoleInformation::new).collect(Collectors.toList()));
	}
}