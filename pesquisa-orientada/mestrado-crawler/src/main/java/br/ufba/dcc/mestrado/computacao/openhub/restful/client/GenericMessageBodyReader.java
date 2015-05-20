package br.ufba.dcc.mestrado.computacao.openhub.restful.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubBaseResponse;

public class GenericMessageBodyReader<T extends OpenHubBaseResponse> implements Serializable, MessageBodyReader<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208720085359746791L;
	
	private Class<T> dtoClass;

	public GenericMessageBodyReader(Class<T> dtoClass) {
		this.dtoClass = dtoClass;
	}
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == dtoClass;
	}

	@Override
	public T readFrom(
			Class<T> type, Type genericType,
			Annotation[] annotations, 
			MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, 
			InputStream entityStream)
			throws IOException, WebApplicationException {
		
		
		T readed = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			readed = (T) unmarshaller.unmarshal(entityStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return readed;
	}

}
