package com.obdread.ed;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.obdread.infra.AppED;

@Entity
@Table(name = "TBL_ERROS_ECU")
public class ErrosEcuED extends AppED<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERROS_ECU_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long errosEcuId;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
	private UsuarioED usuarioED;

	@ManyToOne
	@JoinColumn(name = "VEICULO_ID", referencedColumnName = "VEICULO_ID")
	private VeiculoED veiculoED;

	@Column(name = "COD_ERRO", length = 5)
	private String codigoErro;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "LEVEL")
	private Integer level;

	@Column(name = "DTH_LOG")
	private Calendar dthLog;

	@Override
	public Long getId() {
		return errosEcuId;
	}

	public Long getErrosEcuId() {
		return errosEcuId;
	}

	public void setErrosEcuId(Long errosEcuId) {
		this.errosEcuId = errosEcuId;
	}

	public UsuarioED getUsuarioED() {
		return usuarioED;
	}

	public void setUsuarioED(UsuarioED usuarioED) {
		this.usuarioED = usuarioED;
	}

	public VeiculoED getVeiculoED() {
		return veiculoED;
	}

	public void setVeiculoED(VeiculoED veiculoED) {
		this.veiculoED = veiculoED;
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

	public Calendar getDthLog() {
		return dthLog;
	}

	public void setDthLog(Calendar dthLog) {
		this.dthLog = dthLog;
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}

}