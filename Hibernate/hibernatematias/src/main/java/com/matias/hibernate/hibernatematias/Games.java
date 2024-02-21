package com.matias.hibernate.hibernatematias;

import java.sql.Time;

import jakarta.persistence.*;

@Entity
@Table(name = "Games")
public class Games {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGames")
    private int idGames;
    
    @Column(name = "Nombre")
    private String nombre;
    
    @Column(name = "tiempoJugado")
    private Time tiempoJugado;

    public Games() {
    	
    }
    
	public Games(String nombre, Time tiempoJugado) {
		this.nombre = nombre;
		this.tiempoJugado = tiempoJugado;
	}
    // Getters y Setters

	public int getIdGames() {
		return idGames;
	}

	public void setIdGames(int idGames) {
		this.idGames = idGames;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Time getTiempoJugado() {
		return tiempoJugado;
	}

	public void setTiempoJugado(Time tiempoJugado) {
		this.tiempoJugado = tiempoJugado;
	}
	
}