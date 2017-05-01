package com.obdread.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.obdread.ed.VeiculoED;
import com.obdread.veiculo.VeiculoRN;

@FacesConverter("veiculoConverter")
//@FacesConverter(forClass = VeiculoED.class)
public class VeiculoConverter implements Converter {
	
	@Inject
	VeiculoRN veiculoRN;

//	@Override
//	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
//		if (value != null && !value.isEmpty()) {
//			return (VeiculoED) uiComponent.getAttributes().get(value);
//		}
//		return null;
//	}
//
//	@Override
//	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
//		if (value instanceof VeiculoED) {
//			VeiculoED pergunta = (VeiculoED) value;
//			if (pergunta != null && pergunta instanceof VeiculoED && pergunta.getId() != null) {
//				uiComponent.getAttributes().put(pergunta.getId().toString(), pergunta);
//				return pergunta.getId().toString();
//			}
//		}
//		return "";
//	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {   
		return veiculoRN.consulta(new Long(value));		
	}
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {	
		
		 if (value instanceof VeiculoED) {
			 VeiculoED tabela = (VeiculoED) value;
	            return String.valueOf(tabela.getId());
	        }
	        return "";
		
		//return value.toString();		
	}

}
