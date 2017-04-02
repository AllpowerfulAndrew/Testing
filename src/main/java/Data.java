import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Properties;

/**
 * Class Data.
 * Create
 */
public class Data {
    private Properties properties;
    private Request request;
    private Formatter formatter;
    private String file;

    public Data(final String file) {
        this.file = file;
        write();
    }

    private void write() {

        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/testing.properties"));
            request = new Request(properties.getProperty("expected.info"));

            formatter = new Formatter("src/main/resources/" + file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        formatter.format("%s", request.getExpectedInfo().toString());
        formatter.close();
    }
}
