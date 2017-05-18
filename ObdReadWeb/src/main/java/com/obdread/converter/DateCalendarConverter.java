package com.obdread.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.obdread.exception.RNException;

/**
 * 
 * @author Daniel Rafaelli
 * 
 */
@FacesConverter(value = "dateCalendarConverter")
public class DateCalendarConverter implements Converter {

  private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
    if (arg2 != null) {
      df.setLenient(false);
      try {
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(arg2));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
      } catch (ParseException e) {
        e.printStackTrace();
        throw new RNException("A data deve ser válida.");
      }
    } else {
      return arg2;
    }
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
    if (arg2 != null) {
      Date date = ((Calendar) arg2).getTime();
      df.setLenient(false);
      return df.format(date);
    } else {
      return "";
    }
  }
}