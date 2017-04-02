import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Example {
    public void request() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("https://www.bnm.md/ro/official_exchange_rates?get_xml=1&date=02.04.2017");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No file!");
        }
    }

    public static void main(String[] args) {
        new Example().request();
    }
}
