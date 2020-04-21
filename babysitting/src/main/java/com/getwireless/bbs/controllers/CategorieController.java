package com.getwireless.bbs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getwireless.bbs.entities.Categorie;
import com.getwireless.bbs.model.MessageResponse;
import com.getwireless.bbs.services.CategorieService;

@RestController
@RequestMapping("/categorie")
@CrossOrigin("*")
public class CategorieController {
	@Autowired
	private CategorieService categorieService;

	@PostMapping
	public MessageResponse save(@RequestBody Categorie categorie) {
		return categorieService.save(categorie);
	}

	@PutMapping
	public MessageResponse update(@RequestBody Categorie categorie) {
		return categorieService.update(categorie);
	}

	@DeleteMapping("/{id}")
	public MessageResponse delete(@PathVariable Integer id) {
		return categorieService.delete(id);
	}

	@GetMapping
	public List<Categorie> findAll() {
		return categorieService.findAll();
	}
	
	@GetMapping("/{id}")
	public Categorie findById(@PathVariable Integer id) {
		return categorieService.findById(id);
	}
}
