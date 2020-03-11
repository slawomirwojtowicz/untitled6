package com.rmn.testrail.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author mmerrell
 */
public class HTTPUtils implements Serializable {
    public HTTPUtils() {
    }

    private static final int REQUEST_TIMEOUT = 1 * 60 * 1000; //minutes * seconds * milliseconds
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Prepare everything about a GET request, including adding the headers, composing the URL, establishing the Connection
     *
     * @param completeUrl    The complete URL, including the apiCall, to send to the service
     * @param authentication a Base64-encoded String representing this username and password pair
     * @param headers        A map of header key-value pairs to send along with the HTTP request
     * @return An active, open connection in a post-response state
     * @throws IOException occurs when submitRequestFromConnectionWithRetry throws IOException
     */
    public HttpURLConnection getHTTPRequest(String completeUrl, String authentication, Map<String, String> headers) throws IOException {
        //Build the connection, then insert the pid and fp headers
        HttpURLConnection connection = (HttpURLConnection) new URL(completeUrl).openConnection();
        connection.setRequestProperty("Authorization", "Basic " + authentication);

        log.debug("Attempting to get {}", completeUrl);
        //Add a new header for each entry in the collection
        if (headers != null) {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                log.debug("Adding header [{}: {}]", key, value);
                connection.setRequestProperty(key, value);
            }
        }
        return submitRequestFromConnectionWithRetry(connection, 2);
    }

    /**
     * Take a fully-baked connection and send it to the server with retry
     *
     * @param connection A composed HTTP request containing the URL and any headers required
     * @return An active, open connection in a post-response state
     * @throws IOException occurs when unable to read response code from HttpUrlConnection
     */
    private HttpURLConnection submitRequestFromConnectionWithRetry(HttpURLConnection connection, int retries) throws IOException {
        boolean connected = false;
        int RETRY_DELAY_MS = 500; // initial default value
        int retryDelayInMS;

        connection.setReadTimeout(REQUEST_TIMEOUT);
        connection.setConnectTimeout(REQUEST_TIMEOUT);

        outer:
        for (int retry = 0; retry < retries && !connected; retry++) {
            if (retry > 0) {
                log.warn("retry " + retry + "/" + retries);
                try {
                    log.debug("Sleeping for retry: " + RETRY_DELAY_MS);
                    Thread.sleep(RETRY_DELAY_MS);
                    RETRY_DELAY_MS = 500; // reset to default value
                } catch (InterruptedException e) {
                    // lets ignore this
                }
            }

            // try connect
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    log.debug(" **OK**");
                    return connection;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    log.warn(" **gateway timeout**");
                    break;// retry
                case 429: // 429 isn't available in any of the enums
                    log.warn(" **429**");
                    retryDelayInMS = Integer.parseInt(connection.getHeaderField("Retry-After")) * 1000; // seconds to ms
                    RETRY_DELAY_MS = retryDelayInMS;
                    break;// retry
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    log.warn("**unavailable**");
                    break;// retry, server is unstable
                default:
                    log.error(" **unknown response code**.");
                    break outer; // abort
            }
        }

        return connection;
    }

    /**
     * Gathers the contents of a URL Connection and concatenates everything into a String
     *
     * @param connection A URLConnection object that presumably has a getContent() that will have some content to get
     * @return A Concatenated String of the Content contained in the URLConnection
     */
    public String getContentsFromConnection(URLConnection connection) {
        //Get the content from the connection. Since the content could be in many forms, this Java library requires us to marshall it into an InputStream, from which we get a...
        InputStreamReader in;
        try {
            in = new InputStreamReader((InputStream) connection.getContent());
        } catch (IOException e) {
            throw new RuntimeException("Could not read contents from connection: " + e.getMessage());
        }
        return getContentsFromInputStream(in);
    }

    /**
     * Returns a concatenated String of the contents of an HttpResponse
     *
     * @param response An HttpResponse
     * @return a String containing the contents of the HttpResponse
     * @throws IOException occurs when unable to read content from response
     */
    public String getContentsFromHttpResponse(HttpResponse response) throws IOException {
        InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
        return getContentsFromInputStream(in);
    }

    private String getContentsFromInputStream(InputStreamReader in) {
        //...Buffered reader, which we have to parse out in order to get a string we can use
        BufferedReader buff = new BufferedReader(in);
        String text = "";

        //The return values all seem to be contained within one line, so we probably don't need this
        String line;
        do {
            try {
                line = buff.readLine();
                text += line + "\n";
            } catch (Exception ex) {
                return text;
            }
        } while (line != null);
        return text;
    }

    /**
     * Takes a username and password, then returns a Base64-encoded String that can be sent for Basic Auth
     *
     * @param username the Username to be encoded
     * @param password the Password to be encoded
     * @return a Base64-encoded String representing this key-value pair
     */
    public String encodeAuthenticationBase64(String username, String password) {
        return Base64.encodeBase64String(String.format("%s:%s", username, password).getBytes());
    }
}

