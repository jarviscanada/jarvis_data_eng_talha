package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TwitterControllerIntTest {

    //Create a private twitterController to test
    private static TwitterController controller;
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

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);

        //Create a new twitter Dao object
        TwitterDao dao = new TwitterDao(httpHelper);

        //Pass that created dao to our twitterService
        Service twitterService = new TwitterService(dao);

        //Now we pass that twitterService to our controller
        controller = new TwitterController(twitterService);
    }

    @Test
    public void postTweets() throws Exception {
        //Now lets set up a few tweets to post, lets do it in a loop and post 3 tweets
        String[] args;

        for (int i = 0; i < 3; i++) {
            //args is an array of strings we need to pass to the controller postTweet method
            args = new String[] {"post", "test_tweet_" + System.currentTimeMillis(), "-1.0:1.0" };

            Tweet tweet = controller.postTweet(args);

            //Test to make sure the tweet we just posted is not Null
            assertNotNull(tweet);

            //Add its Id to our list of ids (needed later)
            IdList.add(tweet.getIdStr());
        }
    }

    @Test
    public void findTweets() throws Exception {
        //Now lets do a test to find and show those tweets we just created
        String[] args;

        //Since we added all the ids to our IdList variable, lets loop through them
        for (String id : IdList) {
            //setup arguments for each tweet
            args = new String[]{"show", id, "id"};

            //Call our showTweet method in controller
            Tweet tweet = controller.showTweet(args);

            //Make sure the tweet we found is not Null
            assertNotNull(tweet);

            //Check to see if the Id's match
            assertEquals(tweet.getIdStr(), id);
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }

    @After
    public void deleteTweets() throws Exception {

        //Lets build the string of arrays by appending commands inbetween
        StringBuilder stringOfIds = new StringBuilder();

        for (String id : IdList) {
            stringOfIds.append(id + ",");
        }

        //Remove the last comma
        stringOfIds.deleteCharAt(stringOfIds.length() -1);

        //Now lets create args String to pass to controller method
        String[] args = {"delete", stringOfIds.toString()};

        List<Tweet> deleteTweets = controller.deleteTweet(args);

        //Go through each tweet and make sure it is not Null
        for (Tweet tweet : deleteTweets) {
            assertNotNull(tweet);
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }

}