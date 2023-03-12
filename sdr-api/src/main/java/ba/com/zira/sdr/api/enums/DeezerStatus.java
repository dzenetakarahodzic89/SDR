package ba.com.zira.sdr.api.enums;

public enum DeezerStatus {
    /** READY_TO_MERGE **/
    ACTIVE("ACTIVE"),
    /** SAVED */
    SAVED("SAVED"),
    /** DONE */
    DONE("DONE");

    private final String value;

    DeezerStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
