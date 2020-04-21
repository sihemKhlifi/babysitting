package com.getwireless.bbs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.getwireless.bbs.entities.Utilisateur;
import com.getwireless.bbs.model.MessageResponse;
import com.getwireless.bbs.model.PasswordDto;
import com.getwireless.bbs.repositories.UtilisateurRepository;
import com.getwireless.bbs.services.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public MessageResponse changePassword(PasswordDto pwdDto) {

		Utilisateur user = utilisateurRepository.findById(pwdDto.getId()).orElse(null);
		if (user == null) {
			return new MessageResponse(false, "Utilisateur introuvable");

		}

		boolean valid = passwordEncoder.matches(pwdDto.getOldPassword(), user.getPassword());

		if (!valid) {
			return new MessageResponse(false, "Ancien mot de passe incorrect");
		}

		String cryptedPassword = passwordEncoder.encode(pwdDto.getNewPassword());
		user.setPassword(cryptedPassword);

		utilisateurRepository.save(user);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	@Override
	public MessageResponse activate(Integer id) {
		Utilisateur user = utilisateurRepository.findById(id).orElse(null);
		if (user == null) {
			return new MessageResponse(false, "Utilisateur introuvable");

		}
		
		user.setEnabled(!user.isEnabled());

		utilisateurRepository.save(user);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

}
