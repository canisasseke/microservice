package com.zopenlab.microservice.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="All details about the product. ")
@Entity
@JsonIgnoreProperties(value={"prixAchat"})
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ApiModelProperty(notes="Name should have at least 3 characters and can not null")
	@Length(min=3,max=20,message="longueur min 3 et max 20")
	@NotNull
	private String nom;
	@Basic(fetch=FetchType.EAGER)
	@Lob
	private String description;
	private float prix;
	private float prixAchat;
	
	public Product() {
		
	}

	
	public Product(String nom, String description, float prix, float prixAchat) {
		super();
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.prixAchat = prixAchat;
	}


	

	public Product(Long id, String nom,
			String description, float prix, float prixAchat) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.prixAchat = prixAchat;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public float getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(float prixAchat) {
		this.prixAchat = prixAchat;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + ", prixAchat="
				+ prixAchat + "]";
	}
	
	
	

}
