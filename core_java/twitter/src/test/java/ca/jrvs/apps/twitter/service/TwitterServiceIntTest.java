package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TwitterServiceIntTest {

    //Create a private twitterService to test
    private static TwitterService twitterService;
    //Create a list of Ids
    private List<String> IdList = new ArrayList<String>();

    @BeforeClass
    public static void setUp() throws JsonProcessingException {

        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

        //Create a new twitter Dao object
        TwitterDao dao = new TwitterDao(httpHelper);

        //Pass that created dao to our twitterService
        twitterService = new TwitterService(dao);
    }

    @Test
    public void postTweets() throws JsonProcessingException {

        //Now lets set up a few tweets to post, lets do it in a loop and post 3 tweets
        String tweetText;
        Double lat = 1d;
        Double lon = -1d;

        for (int i = 0; i < 3; i++) {
            tweetText = "test_tweet_" + System.currentTimeMillis();

            Tweet postTweet = new Tweet();
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");
            postTweet.setText(tweetText);
            postTweet.setCoordinates(coordinates);

            Tweet postedTweet = twitterService.postTweet(postTweet);

            //Test to make sure the tweet we just posted is not Null
            assertNotNull(postedTweet);

            //Check to see if both texts are the same
            assertEquals(postedTweet.getText(), postTweet.getText());

            //add that tweet id to the list
            IdList.add(postedTweet.getIdStr());
            System.out.println(JsonUtil.toJson(postedTweet, true, true));
        }
    }

    @Test
    public void findTweets() throws JsonProcessingException {

        //Go through the list of ids and try to find each tweet by that id
        for (String id : IdList) {

            Tweet tweet = twitterService.showTweet(id, null);
            assertNotNull(tweet);

            //Check to see if the Id's match
            assertEquals(tweet.getIdStr(), id);
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }

    @After
    public void deleteTweets() throws JsonProcessingException {

        //convert IdList to array containing those IDs and pass to deleteTweets method in twitter service
        //This will delete all those tweets, we can save those tweet objects in a list to test
        List<Tweet> deleteTweets = twitterService.deleteTweets(IdList.toArray(new String[0]));

        //Go through each item and test to see if Ids match
        for (int i = 0; i < IdList.size(); i++) {
            assertEquals(deleteTweets.get(i).getIdStr(), IdList.get(i));
            System.out.println(JsonUtil.toJson(deleteTweets.get(i), true, true));
        }
    }
}