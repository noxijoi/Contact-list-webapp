package contactsapp.service;

import java.util.List;

public interface Service<T> {
    void delete(List<T> list);
    T getById(Number id);
    List<T> selectAll();
    void update(T t);
    void insert(T t);
}
