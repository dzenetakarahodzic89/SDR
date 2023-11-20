package ba.com.zira.sdr.rssfeed;

public enum RssAdapterType {
    /** BILLBOARD **/
    BILLBOARD("BILLBOARD"),
    /** LOUDWIRE */
    LOUDWIRE("LOUDWIRE"),
    /** EDM */
    EDM("EDM"),
    /** DX */
    DX("DX"),
    /** UCR */
    UCR("UCR");

    private final String value;

    RssAdapterType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
