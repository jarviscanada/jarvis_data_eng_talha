package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

    //URI Constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    private static final String STATUS = "status";

    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Create an entity(Tweet) to the underlying storage
     *
     * @param tweet entity that to be created
     * @return created entity
     */
    @Override
    public Tweet create(Tweet tweet) {
        //First lets construct URI
        URI uri;

        try {
            //first coordinate in this list is long and second is lat
            List<Double> coordinatesList = tweet.getCoordinates().getCoordinates();

            //now set the uri
            uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + STATUS + EQUAL + tweet.getText()
                    + "&long=" + coordinatesList.get(0) +"&lat=" + coordinatesList.get(1));
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input given", e);
        }

        //Execute HTTP Request
        HttpResponse response = httpHelper.httpPost(uri);

        //Validate response and deserialize response to tweet object
        return parseResponseBody(response, HTTP_OK);
    }

    private Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
        Tweet tweet = null;

        //Check response status
        int status = response.getStatusLine().getStatusCode();

        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
            catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP Status: " + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        //Convert Response Entity to string
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to string");
        }


        //finally, we deserialize JSON string to Tweet object
        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON to object", e);
        }

        return tweet;
    }

    /**
     * Find an entity(Tweet) by its id
     *
     * @param s entity id
     * @return Tweet entity
     */
    @Override
    public Tweet findById(String s) {
        //First lets construct URI
        URI uri;

        try {
            //now set the uri
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id=" + s);
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input given", e);
        }

        //Execute HTTP Request
        HttpResponse response = httpHelper.httpGet(uri);

        //Validate response and deserialize response to tweet object
        return parseResponseBody(response, HTTP_OK);
    }

    /**
     * Delete an entity(Tweet) by its ID
     *
     * @param s of the entity to be deleted
     * @return deleted entity
     */
    @Override
    public Tweet deleteById(String s) {
        //First lets construct URI
        URI uri;

        try {
            //now set the uri
            uri = new URI(API_BASE_URI + DELETE_PATH + "/" + s + ".json");
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input given", e);
        }

        //Execute HTTP Request
        HttpResponse response = httpHelper.httpPost(uri);

        //Validate response and deserialize response to tweet object
        return parseResponseBody(response, HTTP_OK);
    }

}
