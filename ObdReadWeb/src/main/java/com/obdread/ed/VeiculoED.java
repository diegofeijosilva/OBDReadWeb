package com.obdread.ed;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.obdread.infra.AppED;
import com.obdread.util.UtilRN;

@Entity
@Table(name = "TBL_VEICULO")
@NamedQueries(value = {
		@NamedQuery(name = "VeiculoED.consulta", query = "SELECT C FROM VeiculoED C WHERE C.veiculoID = :id") })
public class VeiculoED extends AppED<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEICULO_ID")
	private Long veiculoID;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
	private UsuarioED usuarioED;

	@Column(name = "NOME")
	@NotNull
	private String nome;

	@Column(name = "MODELO")
	private String modelo;

	@Column(name = "ANO_FABRICACAO", length = 4)
	private String anoFabricacao;

	@Column(name = "DTH_ULT_TROCA_OLEO")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dthUltTrocaOleo;

	@Column(name = "DTH_ULT_REVISAO")
	@Temporal(TemporalType.DATE)
	private Calendar dthUltRevisao;

	@Override
	public Long getId() {
		return veiculoID;
	}
	
	public String dthUltRevisaoFormat(){
		return UtilRN.converteddmmyy(this.dthUltRevisao);
	}

	public Long getVeiculoID() {
		return veiculoID;
	}

	public void setVeiculoID(Long veiculoID) {
		this.veiculoID = veiculoID;
	}

	public UsuarioED getUsuarioED() {
		return usuarioED;
	}

	public void setUsuarioED(UsuarioED usuarioED) {
		this.usuarioED = usuarioED;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(String anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public Calendar getDthUltTrocaOleo() {
		return dthUltTrocaOleo;
	}

	public void setDthUltTrocaOleo(Calendar dthUltTrocaOleo) {
		this.dthUltTrocaOleo = dthUltTrocaOleo;
	}

	public Calendar getDthUltRevisao() {
		return dthUltRevisao;
	}

	public void setDthUltRevisao(Calendar dthUltRevisao) {
		this.dthUltRevisao = dthUltRevisao;
	}

}