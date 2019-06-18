package contactsapp.core.entity;

public enum Sex {

    MALE("M"),
    FEMALE("F");

    Sex(String f) {
        this.value = f;
    }

    public String getValue() {
        return value;
    }

    private final String value;
}
