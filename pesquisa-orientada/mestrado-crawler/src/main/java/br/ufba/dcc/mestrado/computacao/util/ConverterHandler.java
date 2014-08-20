package br.ufba.dcc.mestrado.computacao.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import br.ufba.dcc.mestrado.computacao.beanutils.converters.OhLohDTO2EntityConverter;
import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OhLohRepositoryEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.kudoskore.OhLohKudoScoreEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisLanguagesDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohRepositoryDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.kudoskore.OhLohKudoScoreDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;

public class ConverterHandler<DTO extends OhLohResultDTO, ID extends Number, E extends BaseEntity<ID>> 
		implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3946201995848552435L;
	
	private Class<DTO> dtoClass;
	private Class<E> entityClass;

	static {
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohAnalysisLanguagesDTO.class, OhLohAnalysisLanguagesEntity.class), OhLohAnalysisLanguagesEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohAnalysisDTO.class, OhLohAnalysisEntity.class), OhLohAnalysisEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohKudoScoreDTO.class, OhLohKudoScoreEntity.class), OhLohKudoScoreEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohStackDTO.class, OhLohStackEntity.class), OhLohStackEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohAccountDTO.class, OhLohAccountEntity.class), OhLohAccountEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohEnlistmentDTO.class, OhLohEnlistmentEntity.class), OhLohEnlistmentEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohRepositoryDTO.class, OhLohRepositoryEntity.class), OhLohRepositoryEntity.class);
		ConvertUtils.register(new OhLohDTO2EntityConverter<>(OhLohProjectDTO.class, OhLohProjectEntity.class), OhLohProjectEntity.class);
		ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
	}
	
	public ConverterHandler(Class<DTO> dtoClass, Class<E> entityClass) {
		this.dtoClass = dtoClass;
		this.entityClass = entityClass;
	}

	protected Object deepCopySingleValue(Object origValue, Class<?> originClass, Class<?> destClass) throws Exception {
		if (origValue == null) {
			return null;
		}
		
		try {
			Object destValue = destClass.newInstance();		
			BeanUtils.copyProperties(destValue, origValue);
			
			for (Field field : originClass.getDeclaredFields()) {
				String getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				
				if (Collection.class.isAssignableFrom(field.getType())) {
					Method getOrigMethod = originClass.getDeclaredMethod(getMethodName);
					
					if (getOrigMethod.invoke(origValue) != null) {
						Collection destCollection = deepCopyCollectionValue(
								origValue, 
								field,
								destValue,
								destClass.getDeclaredField(field.getName()),
								getOrigMethod);
					
						
						ParameterizedType itemDestParameterizedType = (ParameterizedType) destClass.getDeclaredField(field.getName()).getGenericType();
						
						Method setDestMethod = destClass.getDeclaredMethod(
								setMethodName,
								(Class<?>) itemDestParameterizedType.getRawType());
						setDestMethod.invoke(destValue, field.getType().cast(destCollection));
					}
				} else if (OhLohResultDTO.class.isAssignableFrom(field.getType())) {			
					Method getMethod = originClass.getDeclaredMethod(getMethodName);
						
					if (getMethod.invoke(origValue) != null) {
						
						Class<?> destFieldClass = null;
						if (Field.class.equals(destClass.getDeclaredField(field.getName()).getClass())) {
							destFieldClass = destClass.getDeclaredField(field.getName()).getType();
						} else {
							destFieldClass = destClass.getDeclaredField(field.getName()).getClass();
						}
						
						Object destFieldObject = deepCopySingleValue(
								getMethod.invoke(origValue),  
								field.getType(), 
								destFieldClass);
						
						Method setDestMethod = destClass.getDeclaredMethod(
								setMethodName,
								destFieldClass);
						
						setDestMethod.invoke(destValue, destFieldObject);
					}
					
				}
			}
			return destValue;
		} catch (InstantiationException ex) {
			throw new IllegalArgumentException("N�o foi poss�vel instanciar objeto do tipo " + destClass.getName() + " como c�pia do objeto de tipo " + originClass.getName(), ex);
		}
		
	}
	
	/**
	 * @param origValue
	 * @param origField
	 * @param getOrigMethod
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private Collection deepCopyCollectionValue(Object origValue, Field origField, Object destValue, Field destField, Method getOrigMethod) throws Exception{
		
		ParameterizedType itemOrigParameterizedType = (ParameterizedType) origField.getGenericType();
		ParameterizedType itemDestParameterizedType = (ParameterizedType) destField.getGenericType();
		
		Class<?> itemOrigClass = (Class<?>) itemOrigParameterizedType.getActualTypeArguments()[0];
		Class<?> itemDestClass = (Class<?>) itemDestParameterizedType.getActualTypeArguments()[0];
		
		Collection origCollection = (Collection) getOrigMethod.invoke(origValue);
		Collection destCollection = new ArrayList();
		
		for (Object itemOrigValue : origCollection) {
			Object itemDestValue = deepCopySingleValue(itemOrigValue, itemOrigClass, itemDestClass);
			destCollection.add(itemDestValue);
		}
		
		return destCollection;
	}
	
	public E buildEntity(DTO dto) throws Exception {
		
		if (dto == null) {
			return null;
		}
		
		E entity = entityClass.newInstance();

		BeanUtils.copyProperties(entity, dto);

		for (Field field : dtoClass.getDeclaredFields()) {
			String getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			
			if (Collection.class.isAssignableFrom(field.getType())) {
				Method getOrigMethod = dtoClass.getDeclaredMethod(getMethodName);
				
				if (getOrigMethod.invoke(dto) != null) {
					Collection destCollection = deepCopyCollectionValue(
							dto, 
							field,
							entity,
							entityClass.getDeclaredField(field.getName()),
							getOrigMethod);
				
					Method setDestMethod = entityClass.getDeclaredMethod(
							setMethodName, 
							entityClass.getDeclaredField(field.getName()).getType()
							);
					setDestMethod.invoke(entity, field.getType().cast(destCollection));
				}
			} else if (OhLohResultDTO.class.isAssignableFrom(field.getType())) {
				Method getMethod = dtoClass.getDeclaredMethod(getMethodName);
				if (getMethod.invoke(dto) != null) {
					
					Class<?> destFieldClass = null;
					if (Field.class.equals(entityClass.getDeclaredField(field.getName()).getClass())) {
						destFieldClass = entityClass.getDeclaredField(field.getName()).getType();
					} else {
						destFieldClass = entityClass.getDeclaredField(field.getName()).getClass();
					}
					
					Object destFieldObject = deepCopySingleValue(
							getMethod.invoke(dto), 
							field.getType(), 
							destFieldClass);
					
					Method setMethod = entityClass.getDeclaredMethod(setMethodName, entityClass.getDeclaredField(field.getName()).getType());
					setMethod.invoke(entity, entityClass.getDeclaredField(field.getName()).getType().cast(destFieldObject));
				}
			} 
		}

		return entity;
	}
	
}