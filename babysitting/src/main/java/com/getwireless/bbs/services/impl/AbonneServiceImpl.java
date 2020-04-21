package com.getwireless.bbs.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.getwireless.bbs.entities.Abonne;
import com.getwireless.bbs.model.MessageResponse;
import com.getwireless.bbs.repositories.AbonneRepository;
import com.getwireless.bbs.repositories.UtilisateurRepository;
import com.getwireless.bbs.services.AbonneService;

@Service
public class AbonneServiceImpl implements AbonneService {

	@Autowired
	private AbonneRepository abonneRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public MessageResponse register(Abonne abonne) {

		boolean exist = utilisateurRepository.existsByEmail(abonne.getEmail());
		if (exist) {
			return new MessageResponse(false, "Email existe déja");
		}
		exist = utilisateurRepository.existsByLogin(abonne.getLogin());
		if (exist) {
			return new MessageResponse(false, "Login existe déja");
		}
		abonne.setEnabled(true);
		String cryptedPassword = passwordEncoder.encode(abonne.getPassword());
		abonne.setPassword(cryptedPassword);

		abonneRepository.save(abonne);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	@Override
	public MessageResponse update(Abonne abonne) {
		boolean exist = utilisateurRepository.existsByEmailAndId(abonne.getEmail(), abonne.getId());
		if (!exist) {
			exist = utilisateurRepository.existsByEmail(abonne.getEmail());
			if (exist) {
				return new MessageResponse(false, "Email existe déja");
			}
		}
		exist = utilisateurRepository.existsByLoginAndId(abonne.getLogin(), abonne.getId());
		if (!exist) {
			exist = utilisateurRepository.existsByLogin(abonne.getLogin());
			if (exist) {
				return new MessageResponse(false, "Login existe déja");
			}
		}

		abonneRepository.save(abonne);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	@Override
	public List<Abonne> findAll() {
		// TODO Auto-generated method stub
		return abonneRepository.findAll();
	}

	@Override
	public Abonne findById(Integer id) {
		// TODO Auto-generated method stub
		return abonneRepository.findById(id).orElse(null);
	}

}
