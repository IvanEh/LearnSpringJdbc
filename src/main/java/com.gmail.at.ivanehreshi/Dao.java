package com.gmail.at.ivanehreshi;

import java.io.Serializable;

// A useful abstraction. Used in DAO patter
public interface Dao<T, PK extends Serializable> {
    PK create(T t);
    void update(T t);
    T read(PK pk);
    void delete(PK pk);
}
