package ba.com.zira.sdr.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import ba.com.zira.sdr.dao.SampleDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SampleDaoTest extends BasicTestConfiguration {

    @Autowired
    private SampleDAO sampleDAO;

    @Test(enabled = false, description = "find samples")
    public void findSamplesDaoTest1() {
        
    }

}
