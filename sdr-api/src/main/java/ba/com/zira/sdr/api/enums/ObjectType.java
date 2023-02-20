package ba.com.zira.sdr.api.enums;

public enum ObjectType {
    /** SONG **/
    SONG("SONG"),
    /** ALBUM */
    ALBUM("ALBUM"),
    /** ARTIST */
    ARTIST("ARTIST");

    private final String value;

    ObjectType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}