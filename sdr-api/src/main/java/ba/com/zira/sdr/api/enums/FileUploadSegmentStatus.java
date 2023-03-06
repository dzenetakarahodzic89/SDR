package ba.com.zira.sdr.api.enums;

public enum FileUploadSegmentStatus {
    /** READY_TO_MERGE **/
    READY_TO_MERGE("READY_TO_MERGE"),
    /** UPLOADING */
    UPLOADING("UPLOADING"),
    /** SAVED */
    SAVED("SAVED");

    private final String value;

    FileUploadSegmentStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
