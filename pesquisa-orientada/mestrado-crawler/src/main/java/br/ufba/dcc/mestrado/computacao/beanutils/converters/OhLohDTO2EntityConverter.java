package br.ufba.dcc.mestrado.computacao.beanutils.converters;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohBaseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.util.ConverterHandler;

public class OhLohDTO2EntityConverter<DTO extends OhLohResultDTO, ID extends Number, E extends OhLohBaseEntity<ID>> implements Converter {
	
	private Object defaultValue = null;
    private boolean useDefault = true;
    
    private Class<DTO> dtoClass;
	private Class<E> entityClass;
    
    private ConverterHandler<DTO, ID, E> converterUtil;
    
    public OhLohDTO2EntityConverter(Class<DTO> dtoClass, Class<E> entityClass) {
    	this.dtoClass = dtoClass;
		this.entityClass = entityClass;
    	this.converterUtil = new ConverterHandler<DTO, ID, E>(dtoClass,entityClass);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Class type, Object value) {
		
		if (value == null) {
			if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException("No value specified");
            }
		}
		
		if (value instanceof OhLohBaseEntity) {
			return value;
		}
		
		E entity = null;
		if (dtoClass.isInstance(value)) {
			try {
				entity = entityClass.newInstance();
				BeanUtils.copyProperties(entity, value);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
