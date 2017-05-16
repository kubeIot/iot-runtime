package cz.cvut.fel.pauliada.iot.runtime;

import io.silverspoon.bulldog.raspberrypi.RaspiNames;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RaspberryPiGpioTest extends TestCase {

    private Map<String, String> map = new HashMap<>();

    public RaspberryPiGpioTest(String testName) {
        super(testName);
        map.put("1", "2");
        map.put("2", "3");
        map.put("3", "4");
        map.put("4", "14");
        map.put("5", "15");
        map.put("6", "17");
        map.put("7", "18");
        map.put("8", "27");
        map.put("9", "22");
        map.put("10", "23");
        map.put("11", "24");
        map.put("12", "10");
        map.put("13", "9");
        map.put("14", "25");
        map.put("15", "11");
        map.put("16", "8");
        map.put("17", "7");
    }

    public static Test suite() {
        return new TestSuite(RaspberryPiGpioTest.class);
    }

    protected void setUp() throws IOException {

        try {
            for (String v : map.values()) {
                BufferedWriter bfwr = Files.newBufferedWriter(Paths.get("/sys/class/gpio/unexport"));
                bfwr.write(v);
                bfwr.flush();
                bfwr.close();
            }
        } catch (IOException ioe) {
        }
        for (String v : map.values()) {
            BufferedWriter bfwr = Files.newBufferedWriter(Paths.get("/sys/class/gpio/export"));
            bfwr.write(v);
            bfwr.flush();
            bfwr.close();

            bfwr = Files.newBufferedWriter(Paths.get("/sys/class/gpio/gpio" + v + "/direction"));
            bfwr.write("out");
            bfwr.flush();
            bfwr.close();

            bfwr = Files.newBufferedWriter(Paths.get("/sys/class/gpio/gpio" + v + "/value"));
            bfwr.write("0");
            bfwr.flush();
            bfwr.close();
        }
    }

    public void testGpio() throws IOException {

        for (Map.Entry<String, String> e : map.entrySet()) {

            // 1
            // POST
            {
                URL obj = new URL("http://localhost:8080/gpio/" + e.getKey());
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // Setting basic post request
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json");

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes("{\"state\":true}");
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();

            }

            // read
            BufferedReader bfrd = Files.newBufferedReader(Paths.get("/sys/class/gpio/gpio" + e.getValue() + "/value"));

            // compare
            assertEquals("1", bfrd.readLine().trim());
            bfrd.close();

            // GET
            {
                URL url = new URL("http://localhost:8080/gpio/" + e.getKey());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
                //compare
                assertEquals("{\"state\":true}", response.toString().trim());
            }

            /**********************************************************************************************************/

            // 0
            // POST
            {
                URL obj = new URL("http://localhost:8080/gpio/" + e.getKey());
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // Setting basic post request
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json");

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes("{\"state\":false}");
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
            }

            // read
            bfrd = Files.newBufferedReader(Paths.get("/sys/class/gpio/gpio" + e.getValue() + "/value"));

            // compare
            assertEquals("0", bfrd.readLine().trim());
            bfrd.close();

            // GET
            {
                URL url = new URL("http://localhost:8080/gpio/" + e.getKey());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
                //compare
                assertEquals("{\"state\":false}", response.toString().trim());
            }



        }

    }

    protected void tearDown() throws IOException {
        for (String v : map.values()) {
            BufferedWriter bfwr = Files.newBufferedWriter(Paths.get("/sys/class/gpio/unexport"));
            bfwr.write(v);
            bfwr.flush();
            bfwr.close();
        }
    }
}
