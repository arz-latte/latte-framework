package com.jvmhub.tutorial.entity;

import java.io.Serializable;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppUser implements Serializable {
 
	private static final long serialVersionUID = 1L;

	public AppUser() {
 
	};
 
	public AppUser(String login) {
		this.login = login;
 
	};
 
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	
	private String login;
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getLogin() {
		return login;
	}
 
	public void setLogin(String login) {
		this.login = login;
	}
}