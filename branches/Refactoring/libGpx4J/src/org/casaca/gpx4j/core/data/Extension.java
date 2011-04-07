package org.casaca.gpx4j.core.data;

public class Extension extends BaseObject {
	private String key;
	private String value;
	
	public Extension(String key, String value) throws IllegalArgumentException{
		if(key == null || value == null) throw new IllegalArgumentException("Key and value must not be null");
		
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
