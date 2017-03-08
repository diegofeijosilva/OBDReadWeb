package com.obdread.ed;

import java.io.Serializable;

/**
 * Classe utilizada para receber os dados vindos do android através do serviço Rest
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
  private String            hashUser;

  private Integer           data;
  private Integer           hora;

  /// Dados vindos do GPS
  private String            latitude;
  private String            longitude;

  /// Dados vindos da ECU
  private String            obdRpm;
  private String            obdVelocidade;
  private String            obdQtdeCombustivel;

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

  public String getObdRpm() {
    return obdRpm;
  }

  public void setObdRpm(String obdRpm) {
    this.obdRpm = obdRpm;
  }

  public String getObdVelocidade() {
    return obdVelocidade;
  }

  public void setObdVelocidade(String obdVelocidade) {
    this.obdVelocidade = obdVelocidade;
  }

  public String getObdQtdeCombustivel() {
    return obdQtdeCombustivel;
  }

  public void setObdQtdeCombustivel(String obdQtdeCombustivel) {
    this.obdQtdeCombustivel = obdQtdeCombustivel;
  }

  public String getHashUser() {
    return hashUser;
  }

  public void setHashUser(String hashUser) {
    this.hashUser = hashUser;
  }

}
