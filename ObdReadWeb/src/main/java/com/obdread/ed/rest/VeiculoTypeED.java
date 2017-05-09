package com.obdread.ed.rest;

import java.io.Serializable;

/**
 * Classe utilizada para receber os dados vindos do android atrav�s do servi�o
 * Rest
 * 
 * @author diego-silva
 *
 */

public class VeiculoTypeED implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9186535305608152233L;

	/// Hash do usu�rio
	private Long id;
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
