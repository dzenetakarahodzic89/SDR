package ba.com.zira.sdr.api.enums;

public enum ServiceType {
    SDR("SDR"), SPOTIFY("Spotify"), DEEZER("Deezer"), YT_MUSIC("Youtube music"), TIDAL("Tidal"), ITUNES("I-tunes"), GOOGLE_PLAY(
            "Google play");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
