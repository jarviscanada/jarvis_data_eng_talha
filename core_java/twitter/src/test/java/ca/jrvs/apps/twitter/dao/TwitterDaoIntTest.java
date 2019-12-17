package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao dao;
    private String idString;
    private Tweet tweet;

    @Before
    public void setUp() throws JsonProcessingException {

        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

        // Creating new TwitterDao object using the created httpHelper
        this.dao = new TwitterDao(httpHelper);


        //Create new tweet and test it here

        String text = "test_tweet_" + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        Tweet postTweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(lon, lat));
        coordinates.setType("Point");

        postTweet.setText(text);
        postTweet.setCoordinates(coordinates);

        System.out.println(JsonUtil.toJson(postTweet, true, true));

        tweet = dao.create(postTweet);
        System.out.println(JsonUtil.toJson(tweet, true, true));

        //Test to see if text is the same
        assertEquals(text, tweet.getText());

        //Test both longitude and latitude values
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        //Set the id string so we can use it for the find method
        idString = tweet.getIdStr();
    }

    @Test
    public void findTweet() throws JsonProcessingException {
        Tweet findTweet = dao.findById(tweet.getIdStr());

        System.out.println(JsonUtil.toJson(findTweet, true, true));

        assertEquals(tweet.getText(), findTweet.getText());
        assertEquals(idString, findTweet.getIdStr());
    }

    @After
    public void deleteTweet() throws JsonProcessingException {
        Tweet deleteTweet = dao.deleteById(tweet.getIdStr());

        System.out.println(JsonUtil.toJson(deleteTweet, true, false));

        assertEquals(tweet.getText(), deleteTweet.getText());
        assertEquals(idString, deleteTweet.getIdStr());
    }
}