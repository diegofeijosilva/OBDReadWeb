package com.obdread.veiculo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.exception.RNException;
import com.obdread.infra.AppRN;
import com.obdread.security.SessionMB;

@Stateless
public class VeiculoRN extends AppRN<VeiculoED, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	SessionMB logon;

	@Inject
	VeiculoBD bd;

	@Inject
	public void setBD(VeiculoBD bd) {
		super.setBD(bd);
	}

	@Override
	public VeiculoED consulta(Long id) {
		if (id == null || id < 1)
			throw new RNException(this.getClass() + ": Informar o id a pesquisar.");

		return super.consulta(id);
	}

	@Override
	public VeiculoED inclui(VeiculoED ed) {
		ed.setNome(ed.getNome().toUpperCase());
		return super.inclui(ed);
	}

	@Override
	public VeiculoED altera(VeiculoED ed) {
		ed.setNome(ed.getNome().toUpperCase());
		return super.altera(ed);
	}

	public List<VeiculoED> listaVeiculosUsusario(UsuarioED usuarioED) {
		if (usuarioED == null)
			throw new RNException(this.getClass() + ": Informar o usuário");
		return bd.listaVeiculosUsusario(usuarioED);
	}

	public List<VeiculoED> listaVeiculosUsuarioLogado() {

		UsuarioED ed = logon.getUsuarioLogado();

		return this.listaVeiculosUsusario(ed);
	}
}