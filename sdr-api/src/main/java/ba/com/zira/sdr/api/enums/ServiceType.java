package ba.com.zira.sdr.api.enums;

public enum ServiceType {
    /** SDR **/
    SDR("SDR"),
    /** Spotify */
    SPOTIFY("Spotify"),
    /** Deezer */
    DEEZER("Deezer"),
    /** Youtube music */
    YT_MUSIC("Youtube music"),
    /** Tidal */
    TIDAL("Tidal"),
    /** I-tunes */
    ITUNES("I-tunes"),
    /** Google play */
    GOOGLE_PLAY("Google play");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
