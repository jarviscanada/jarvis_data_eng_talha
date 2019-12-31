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
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterControllerIntTest {

    //Create a private twitterController to test
    private TwitterController controller;
    //Create a list of tweets
    private List<Tweet> tweets;

    @Before
    public void setup() {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);

        TwitterDao dao = new TwitterDao(httpHelper);

        Service twitterService = new TwitterService(dao);

        controller = new TwitterController(twitterService);

        tweets = new ArrayList<>();
        String[] args;

        //Now lets set up a few tweets to post, lets do it in a loop and post 3 tweets
        for(int i = 0; i < 3; i++) {
            //args is an array of strings we need to pass to the controller postTweet method
            args = new String[] {"post", "test_tweet_" + System.currentTimeMillis(), "-1.0:1.0" };

            Tweet tweet = controller.postTweet(args);

            //Test to make sure the tweet we just posted is not Null
            assertNotNull(tweet);
            tweets.add(tweet);
        }
    }

    @Test
    public void showTweets() throws Exception {
        //Now lets do a test to find and show those tweets we just created
        String[] args;

        //Loop through the list of tweets
        for (Tweet foundTweet : tweets) {

            long id = foundTweet.getId();
            args = new String[]{"show", Long.toString(id)};
            Tweet tweet = controller.showTweet(args);

            //Make sure the tweet we found is not Null
            assertNotNull(tweet);

            //Check to see if the Id's match
            assertEquals(tweet.getIdStr(), Long.toString(id));
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }

    @After
    public void deleteTweets() throws Exception {
        //Finally lets delete all the created tweets
        String[] idList  = new String[3];
        String[] args;

        //Keep track of our index to set our idList with each tweetId
        int i = 0;

        for (Tweet tweet : tweets) {
            idList[i] = Long.toString(tweet.getId());
            i++;
        }

        args = new String[]{"delete", String.join(",", idList)};

        List<Tweet> deleteTweets = controller.deleteTweet(args);

        //Go through each tweet and make sure it is not Null
        for (Tweet tweet : deleteTweets) {
            assertNotNull(tweet);
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }
}
