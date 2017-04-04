package dataWork;

import org.apache.log4j.Logger;
import web.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.Properties;

/**
 * Class data.Data.
 * Create XML file with request dates.
 */
public class Data {
    public static final Logger LOG = Logger.getLogger(Data.class);
    private Properties properties;
    private Request request;
    private Formatter formatter;

    public void getXMLData() {
        try {
            LOG.info("Get request properties");
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/testing.properties"));
            LOG.info("Create request");
            request = new web.Request(properties.getProperty("expected.info"));

            formatter = new Formatter("src/main/resources/data.xml");
        } catch (IOException e) {
            LOG.error("Error!", e);
        }

        LOG.info("Write data to file");
        formatter.format("%s", request.getExpectedInfo().toString());
        LOG.info("End getXMLData operation");
        formatter.close();
    }
}
