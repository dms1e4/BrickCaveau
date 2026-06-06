package model;

import java.sql.SQLException;
import java.util.Collection;

public interface DAOInterface<T, K> {
    
    T doRetrieveByKey(K key) throws SQLException;
    
    Collection<T> doRetrieveAll(String order) throws SQLException;
    
    void doSave(T item) throws SQLException;
    
    void doUpdate(T item) throws SQLException;
    
    boolean doDelete(K key) throws SQLException;
}