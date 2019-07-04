package contactsapp.validation;

public interface Validator<T> {
    boolean validate(T t);
}
