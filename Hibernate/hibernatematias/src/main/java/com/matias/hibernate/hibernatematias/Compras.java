package com.matias.hibernate.hibernatematias;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Compras")
public class Compras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompra")
    private int idCompra;
    
    @ManyToOne
    @JoinColumn(name = "idPlayer")
    private Player player;
    
    @ManyToOne
    @JoinColumn(name = "idGames")
    private Games games;
    
    @Column(name = "Cosa")
    private String cosa;
    
    @Column(name = "Precio")
    private Double precio;
    
    @Column(name = "FechaCompra")
    private Date fechaCompra;
    
	public Compras() {

	}

	public Compras(Player player, Games games, String cosa, Double precio, Date fechaCompra) {
		this.player = player;
		this.games = games;
		this.cosa = cosa;
		this.precio = precio;
		this.fechaCompra = fechaCompra;
	}
    // Getters y Setters

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Games getGames() {
		return games;
	}

	public void setGames(Games games) {
		this.games = games;
	}

	public String getCosa() {
		return cosa;
	}

	public void setCosa(String cosa) {
		this.cosa = cosa;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	
    
}