package contactsapp.validation;

public class ValidationExeption extends Throwable {
    public ValidationExeption(String message) {
        super(message);
    }

    public ValidationExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationExeption(Throwable cause) {
        super(cause);
    }

    protected ValidationExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }
}
