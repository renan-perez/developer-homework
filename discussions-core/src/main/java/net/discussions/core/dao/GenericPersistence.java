package net.discussions.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.discussions.core.exception.SystemException;

public interface GenericPersistence<T, ID extends Serializable> {

    T save(final T instance) throws SystemException;
    
    void saveAll(final Collection<T> AllInstances) throws SystemException;

    void delete(T t) throws SystemException;
    
    void deleteAll(final Collection<T> AllInstances) throws SystemException;
    
    T update(final T instance) throws SystemException;

    T get(final Class<T> clazz, final ID id) throws SystemException;

    List<T> list(final Class<T> clazz) throws SystemException;

    T getByNamedQuery(final String namedQuery, final Map<String,Object> paramValueMap, final Class<T> clazz) throws SystemException;

    List<T> listByNamedQuery(final String namedQuery, final Map<String,Object> paramValueMap, final Class<T> clazz) throws SystemException;
    
    void executeNamedQuery(final String namedQuery, final Map<String, Object> paramValueMap, final Class<T> clazz) throws SystemException;
}
