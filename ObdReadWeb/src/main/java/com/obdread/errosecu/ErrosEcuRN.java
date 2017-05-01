package com.obdread.errosecu;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.ErrosEcuED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.ed.rest.ErrosEcuType;
import com.obdread.exception.RNException;
import com.obdread.infra.AppRN;
import com.obdread.usuario.UsuarioRN;
import com.obdread.veiculo.VeiculoRN;

@Stateless
public class ErrosEcuRN extends AppRN<ErrosEcuED, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	ErrosEcuBD bd;
	
	@Inject
	UsuarioRN usuarioRN;
	
	@Inject
	VeiculoRN veiculoRN;

	@Inject
	public void setBD(ErrosEcuBD bd) {
		super.setBD(bd);
	}

	public ErrosEcuED inclui(ErrosEcuType edType) {
		
		UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(edType.getHashUser());
		VeiculoED veiculoED = veiculoRN.consulta(edType.getIdVeiculo());
		
		if(usuarioED == null || veiculoED == null)
			throw new RNException(this.getClass() + ": Informar o usuário e veiculo!");

		ErrosEcuED ed = new ErrosEcuED();
		ed.setUsuarioED(usuarioED);
		ed.setVeiculoED(veiculoED);
		ed.setDescricao(edType.getDescricao().toUpperCase());
		ed.setLevel(edType.getLevel());

		return super.inclui(ed);
	}

	public List<ErrosEcuED> listaErrosEcuVeiculo(VeiculoED ed) {
		if(ed != null)
			return bd.listaErrosEcuVeiculo(ed);
		
		return null;
	}

}