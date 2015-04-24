package br.ufba.dcc.mestrado.computacao.xstream.converters;

import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

public class NullableISO8601SqlTimestampXStreamConverter extends ISO8601SqlTimestampConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		// TODO Auto-generated method stub
		return super.canConvert(type);
	}

	@Override
	public Object fromString(String str) {
		if (str == null || "".equals(str)) 
			return null;
		
		return super.fromString(str);
	}
	
}
