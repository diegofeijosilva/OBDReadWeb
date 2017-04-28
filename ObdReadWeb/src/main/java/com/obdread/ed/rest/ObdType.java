package com.obdread.ed.rest;

import java.io.Serializable;

/**
 * Classe utilizada para receber os dados vindos do android através do serviço
 * Rest
 * 
 * @author diego-silva
 *
 */

public class ObdType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9186535305608152233L;

	/// Hash do usuário
	private String hashUser;

	/// ID do Veículo
	/// Hash do usuário
	private Long idVeiculo;

	private Integer data;
	private Integer hora;

	/// Dados vindos do GPS
	private String latitude;
	private String longitude;

	/// Dados vindos da ECU
	private Long obdRpm;
	private Long obdVelocidade;
	private Long obdQtdeCombustivel;

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

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

	public Long getObdRpm() {
		return obdRpm;
	}

	public void setObdRpm(Long obdRpm) {
		this.obdRpm = obdRpm;
	}

	public Long getObdVelocidade() {
		return obdVelocidade;
	}

	public void setObdVelocidade(Long obdVelocidade) {
		this.obdVelocidade = obdVelocidade;
	}

	public Long getObdQtdeCombustivel() {
		return obdQtdeCombustivel;
	}

	public void setObdQtdeCombustivel(Long obdQtdeCombustivel) {
		this.obdQtdeCombustivel = obdQtdeCombustivel;
	}

}
