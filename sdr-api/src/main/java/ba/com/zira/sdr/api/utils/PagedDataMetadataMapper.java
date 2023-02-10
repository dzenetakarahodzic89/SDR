package ba.com.zira.sdr.api.utils;

import ba.com.zira.commons.model.PagedData;

public class PagedDataMetadataMapper {

    private PagedDataMetadataMapper() {
    }

    public static void remapMetadata(PagedData<?> source, PagedData<?> target) {
        target.setHasMoreRecords(source.hasMoreRecords());
        target.setNumberOfPages(source.getNumberOfPages());
        target.setNumberOfRecords(source.getNumberOfRecords());
        target.setPage(source.getPage());
        target.setRecordsPerPage(source.getRecordsPerPage());
    }

}
