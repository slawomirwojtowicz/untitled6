package pl.ninebits.qa.automated.tests.core.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class HttpsUtils {

  public static void sendGetRequest(String href, List<String> errors) {
    //TODO: cleanup deprecated
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet(href);

    HttpResponse response = null;
    try {
      response = client.execute(request);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (response == null || response.getStatusLine().getStatusCode() != 200)
      errors.add(MessageFormat.format("Link {0} nie działa", href));
  }

}
