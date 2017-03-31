package com.obdread.infra;

import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

public class AppListMB<ED extends AppED<?>, PED extends ED> extends FrameworkListMB<ED, PED> {

	private static final long serialVersionUID = 1L;
	
	public void pesquisa() { 
		pesquisa("form:lista");
	}

	public void pesquisa(String id) {
		DataTable datatable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
		if (datatable != null) {
			datatable.setFirst(0);
		}
	}

}
