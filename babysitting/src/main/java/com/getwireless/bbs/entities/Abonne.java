package com.getwireless.bbs.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
@Data
@Entity
public class Abonne extends Utilisateur {
	@OneToMany(mappedBy = "abonne")
	private List<Annonce>annonces;
	


}
