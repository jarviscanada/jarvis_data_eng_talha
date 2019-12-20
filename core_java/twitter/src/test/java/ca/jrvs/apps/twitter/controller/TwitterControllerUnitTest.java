package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService mock;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() {
        //Lets just set the expected tweet to a new tweet object (should be null)
        Tweet expectedTweet = new Tweet();

        //Return expectedTweet
        when(mock.postTweet(any())).thenReturn(expectedTweet);

        try {
            //If this works then test should fail, as we do not have 3 arguments
            controller.postTweet(new String[] {"post", "Test_Text"});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //This should work as it has 3 arguments, and return a null tweet
        Tweet tweet = controller.postTweet(new String[] {"post", "Test_Text", "1.0:-1.0"});
        assertEquals(tweet.getText(), null);
        assertEquals(tweet.getId(), null);
    }

    @Test
    public void showTweet() {
        //Lets just set the expected tweet to a new tweet object (should be null)
        Tweet expectedTweet = new Tweet();

        //Return expectedTweet
        when(mock.showTweet(any(), any())).thenReturn(expectedTweet);

        try {
            //Should not work because no id and no coordinates
            controller.showTweet(new String[]{"show", ""});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //This should work as it has 3 arguments, and return a null tweet
        Tweet tweet = controller.showTweet(new String[]{"show", "1207064947664019457", "id"});
        assertEquals(tweet.getText(), null);
        assertEquals(tweet.getId(), null);
    }

    @Test
    public void deleteTweet() {
        //Lets just set the expected tweet to a new tweet object (should be null)
        Tweet expectedTweet = new Tweet();

        //Return listOfTweets
        List<Tweet> listOfTweets = new ArrayList<Tweet>();

        //Lets add 3 new null tweets
        listOfTweets.add(expectedTweet);
        listOfTweets.add(expectedTweet);
        listOfTweets.add(expectedTweet);
        when(mock.deleteTweets(any())).thenReturn(listOfTweets);

        try {
            //Should not work because no id
            controller.deleteTweet(new String[] {"delete"});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //This should work as it has 2 arguments, and returns a list of null tweets
        List<Tweet> deleteTweets = controller.deleteTweet(new String[] {"delete"
                , "1207064947664019457,1207064947664019457,1207064947664019457"});

        //Go through each tweet in the list and make sure they are all null
        for (Tweet tweet : deleteTweets) {
            assertEquals(tweet.getText(), null);
            assertEquals(tweet.getId(), null);
        }
    }
}