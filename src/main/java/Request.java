import org.apache.commons.lang3.text.StrBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
    private String url;

    public Request(final String url) {
        this.url = url;
    }

    public Object getExpectedInfo() {
        HttpURLConnection connection = null;
        StrBuilder info = new StrBuilder();

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.connect();

            return readInfo(connection, info);
        } catch (Throwable e) {
            e.printStackTrace();
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
            System.out.println("Fail " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }

        return info;
    }
}