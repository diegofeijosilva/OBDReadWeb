package com.obdread.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

	private static final String KEY = "com.obdread.converter.EntityConverter";
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object entity) {
		if (entity == null || "".equals(entity)) {
			return null;
		}
		
		if (!getEntities(context).containsKey(entity)) {
			String uuid = UUID.randomUUID().toString();
			getEntities(context).put(entity, uuid);
			return uuid;
		} else {
			return getEntities(context).get(entity);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
		Set<Entry<Object, String>> entries = getEntities(context).entrySet();
		for (Entry<Object, String> entry : entries) {
			if (entry.getValue().equals(uuid)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Object, String> getEntities(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		Map<Object, String> entities = (Map<Object, String>) viewMap.get(KEY);
		if (entities == null) {
			entities = new HashMap<>();
			viewMap.put(KEY, entities);
		}
		return entities;
	}

}
