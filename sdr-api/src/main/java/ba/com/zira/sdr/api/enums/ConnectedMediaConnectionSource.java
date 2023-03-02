package ba.com.zira.sdr.api.enums;

public enum ConnectedMediaConnectionSource {
    /** VGR **/
    VGR("VGR - Video Game Repository"),
    /** CBR */
    CBR("CBR - Comic Book Repository"),
    /** MDR */
    MDR("MDR - Movie Repository");

    private final String value;

    ConnectedMediaConnectionSource(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
