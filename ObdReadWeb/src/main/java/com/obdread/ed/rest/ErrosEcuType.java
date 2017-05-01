package com.obdread.ed.rest;

import java.io.Serializable;

/**
 * Classe utilizada para receber os dados vindos do android através do serviço
 * Rest
 * 
 * @author diego-silva
 *
 */

public class ErrosEcuType implements Serializable {

	private static final long serialVersionUID = -9186535305608152233L;

	/// Hash do usuário
	private String hashUser;
	private Long idVeiculo;
	private Integer data;
	private String codigo;
	private String descricao;
	private Integer level;

	public String getHashUser() {
		return hashUser;
	}

	public void setHashUser(String hashUser) {
		this.hashUser = hashUser;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
