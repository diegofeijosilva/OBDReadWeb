package com.obdread.infra;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import com.obdread.infra.persistence.FrameworkED;

public abstract class FrameworkFormMB<ED extends FrameworkED<PK>, PK> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FrameworkRN<ED, PK> rn;
	private PK id;
	private ED ed;
	
	
	// Actions
	
	public void init() {
		// N�o executar caso seja uma ajax-request
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		// N�o passou id por par�metro, logo � inclus�o
		if (id == null) {
			ed = criaED();
			return;
        }

        // Consulta/Edi��o
		ed = (ED) rn.consulta(id);

        if (ed == null) {
        	//throw new RuntimeException("Registro n�o encontrado!");
        	FacesUtil.addErrorMessage("Registro n�o encontrado com id " + id);
        }		
	}

	public void salva() {
		if (ed.getId() == null) {
			ed = rn.inclui(ed);
			//FacesUtil.addInfoMessage(provider.getMessage("registro.inclui-sucesso"));
			FacesUtil.addInfoMessage("Registro inclu�do com Sucesso.");
		} else {
			ed = rn.altera(ed);
			//FacesUtil.addInfoMessage(provider.getMessage("registro.altera-sucesso"));
			FacesUtil.addInfoMessage("Registro alterado com Sucesso.");
		}
		ed = rn.consulta(ed.getId());
	}

	public String exclui() {
		rn.exclui(ed);
		//FacesUtil.addInfoMessage(provider.getMessage("registro.exclui-sucesso"));
		FacesUtil.addInfoMessage("Registro excluido com Sucesso.");
		return getPaginaListaObjeto();
	}
	
	/**
	 * Prepara o ED do objeto para ser inclu�do
	 * 
	 * @return
	 */
	public abstract ED criaED();
	
	// 	Getters & Setters
	
	public boolean isInclusao() {
		if (ed == null || ed.getId() == null) {
			return true;
		}
		return false;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public ED getEd() {
		return ed;
	}

	public void setEd(ED ed) {
		this.ed = ed;
	}
	
	private String getPaginaListaObjeto() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.delete(sb.length() - 6, sb.length()); // remove sufixo FormMB
        sb.append("-list?faces-redirect=true");
        return sb.toString().toLowerCase();
	}
	
	public void setRN(FrameworkRN<ED, PK> rn) {
		this.rn = rn;
	}
	
	public FrameworkRN<ED, PK> getRN() {
		return rn;
	}

}
