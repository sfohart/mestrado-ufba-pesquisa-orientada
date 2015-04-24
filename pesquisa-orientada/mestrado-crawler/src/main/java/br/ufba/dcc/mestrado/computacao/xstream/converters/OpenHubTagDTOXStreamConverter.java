package br.ufba.dcc.mestrado.computacao.xstream.converters;

import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubTagDTO;

import com.thoughtworks.xstream.converters.basic.StringConverter;

public class OpenHubTagDTOXStreamConverter extends StringConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(final Class type) {
		if (OpenHubTagDTO.class.isAssignableFrom(type)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public OpenHubTagDTO fromString(String str) {
		OpenHubTagDTO openHubTagDTO = new OpenHubTagDTO();
		openHubTagDTO.setName(str);
		return openHubTagDTO;
	}

	@Override
	public String toString(Object obj) {
		
		if (obj instanceof OpenHubTagDTO) {
			return ((OpenHubTagDTO) obj).getName();
		} else {
			return null;
		}
	}
}
