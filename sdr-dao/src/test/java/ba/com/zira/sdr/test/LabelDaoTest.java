package ba.com.zira.sdr.test;

import ba.com.zira.sdr.dao.LabelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class LabelDaoTest {

    @Autowired
    private LabelDAO labelDAO;

    @Test(enabled = false, description = "find songInstrument")
    public void findSongInstrumentsDaoTest1() {

    }
}
