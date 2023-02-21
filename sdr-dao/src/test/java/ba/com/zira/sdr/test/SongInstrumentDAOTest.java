package ba.com.zira.sdr.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongInstrumentDAOTest extends BasicTestConfiguration {

    @Autowired
    private SongInstrumentDAO songInstrumentDAO;

    @Test(enabled = false, description = "find songInstrument")
    public void findSongInstrumentsDaoTest1() {
        
    }

}
