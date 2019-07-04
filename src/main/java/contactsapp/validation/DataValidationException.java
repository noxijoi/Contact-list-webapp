package contactsapp.validation;

public class DataValidationException extends Throwable {
    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

    protected DataValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
