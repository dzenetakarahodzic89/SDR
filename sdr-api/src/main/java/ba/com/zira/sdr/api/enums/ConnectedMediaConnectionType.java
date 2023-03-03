package ba.com.zira.sdr.api.enums;

public enum ConnectedMediaConnectionType {
    /** SOUNDTRACK **/
    SOUNDTRACK("Soundtrack"),
    /** REFERENCE **/
    REFERENCE("Reference"),
    /** HOMAGE **/
    HOMAGE("Homage"),
    /** ADDITIONAL_MEDIA **/
    ADDITIONAL_MEDIA("Additional Media");

    private final String value;

    ConnectedMediaConnectionType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
