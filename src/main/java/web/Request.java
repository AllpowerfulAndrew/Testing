package web;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Request {
    public static final Logger LOG = Logger.getLogger(Request.class);
    private Properties properties;

    public String getExpectedInfo() {
        LOG.info("Create request");
        HttpURLConnection connection = null;
        StrBuilder strBuilder = new StrBuilder();
        LOG.info("Get request properties");
        properties = new Properties();

        try {
            properties.load(new FileInputStream("src/test/resources/testing.properties"));
        } catch (IOException e) {
            LOG.error("Trouble with file: ", e);
        }

        return connectionResult(connection, strBuilder);
    }

    private String connectionResult(HttpURLConnection connection, StrBuilder strBuilder) {
        String info = null;

        try {
            connection = (HttpURLConnection) new URL(properties.getProperty("expected.info")).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.connect();

            LOG.info("Get expected content as XML file");
            info = readInfo(connection, strBuilder).toString();
        } catch (Throwable e) {
            LOG.info("Connection error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return info;
    }

    private Object readInfo(HttpURLConnection connection, StrBuilder info) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                info.append(line);
                info.append("\n");
            }

            return info;
        } else {
            LOG.info("Fail " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }

        return info;
    }
}