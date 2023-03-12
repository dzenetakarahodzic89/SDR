package ba.com.zira.sdr.api.enums;

public enum MusicMatchStatus {
    COMPLETED("COMPLETED"),
    /** UPLOADING */
    NOTFOUND("DOES NOT EXIST"),
    /** SAVED */
    FORMATNOTSUPPORTED("FORMAT NOT SUPPORTED");

    private final String value;

    MusicMatchStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
