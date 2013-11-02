package br.ufba.dcc.mestrado.computacao.xstream.converters;

import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohTagDTO;

import com.thoughtworks.xstream.converters.basic.StringConverter;

public class OhLohTagDTOXStreamConverter extends StringConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(final Class type) {
		if (OhLohTagDTO.class.isAssignableFrom(type)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public OhLohTagDTO fromString(String str) {
		OhLohTagDTO ohLohTagDTO = new OhLohTagDTO();
		ohLohTagDTO.setName(str);
		return ohLohTagDTO;
	}

	@Override
	public String toString(Object obj) {
		
		if (obj instanceof OhLohTagDTO) {
			return ((OhLohTagDTO) obj).getName();
		} else {
			return null;
		}
	}
}
