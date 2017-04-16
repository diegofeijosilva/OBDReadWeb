package com.obdread.jsf.conversor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteData")
public class ConverteData implements Converter {
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		df.setLenient(false);
		try {
			// return df.parse(arg2);
			Calendar cal = null;

			cal = Calendar.getInstance();
			cal.setTime((Date) df.parse(arg2));
			return cal;
		} catch (Exception e) {
			throw new ConverterException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			df.setLenient(false);
			String d = df.format(arg2);
			return d;
			
		} catch (Exception e) {
			return "";
		}
	}
}
