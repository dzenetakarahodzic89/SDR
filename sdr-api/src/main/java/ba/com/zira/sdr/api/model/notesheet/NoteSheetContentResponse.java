package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class NoteSheetContentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<StaveResponse> staves;

}
