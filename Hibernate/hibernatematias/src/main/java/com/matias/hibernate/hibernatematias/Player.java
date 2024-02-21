package com.matias.hibernate.hibernatematias;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Player")
public class Player implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlayer")
	private int IdPlayer;
	
	@Column(name = "nick")
	private String Nick;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	public Player() {

	}
	
	public Player(String nick, String password, String email) {
		setNick(nick);
		setPassword(password);
		setEmail(email);
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
