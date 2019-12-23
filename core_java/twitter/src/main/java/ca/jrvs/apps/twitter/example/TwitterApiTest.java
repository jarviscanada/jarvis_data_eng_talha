package ca.jrvs.apps.twitter.example;

import java.util.Arrays;

import oauth.signpost.OAuthConsumer;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.HttpClientBuilder;

public class TwitterApiTest {

    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    public static void main(String[] args) throws Exception {

        //first, setup oath
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        //next, create an HTTP GET request
        String status = "today is a good day";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpPost request = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status="
                + percentEscaper.escape(status));

        //now we sign the request (add the headers)
        consumer.sign(request);

        System.out.println("HTTP Request Headers: ");
        Arrays.stream(request.getAllHeaders())
                .forEach(System.out::println);

        //finally, we send the request
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }
}