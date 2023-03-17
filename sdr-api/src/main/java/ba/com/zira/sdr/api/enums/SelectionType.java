package ba.com.zira.sdr.api.enums;

public enum SelectionType {
    TOURNAMENT("Tournament"), SUS("Stohastic unversal sampling");

    private final String value;

    SelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
