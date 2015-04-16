package br.ufba.dcc.mestrado.computacao.xstream.converters;

import com.thoughtworks.xstream.converters.basic.LongConverter;

public class NullableLongXStreamConverter extends LongConverter {
	
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
