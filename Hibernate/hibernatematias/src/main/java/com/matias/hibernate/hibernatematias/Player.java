package com.matias.hibernate.hibernatematias;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {
	
	@Id
	private int IdPlayer;
	
	private String Nick;
	
	private String password;
	
	private String email;
	
	public Player() {
		
	}

	public int getIdPlayer() {
		return IdPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		IdPlayer = idPlayer;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String nick) {
		Nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
