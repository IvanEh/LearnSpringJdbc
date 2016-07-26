package com.gmail.at.ivanehreshi;

import java.io.Serializable;

public interface Dao<T, PK extends Serializable> {
    PK create(T t);
    void update(T t);
    T read(PK pk);
    void delete(PK pk);
}
