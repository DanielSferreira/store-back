package com.shop.Shop.v1.api.Repository;

import java.util.List;

public interface IRepo <E extends Object> {
    public void GenerateData(String TableName);
    public E CreateTable(E objectE);
    public E GetItemById(String id);
    public List<E> GetAll();
    public List<E> GetItensByParameter(String key, String value);
    public E Insert(E objectE);
    public E Put(String key, String keyVal, String name, String updateVal);
    public boolean DeleteById(String id);
}
