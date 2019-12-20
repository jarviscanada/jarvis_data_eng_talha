package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweets() throws JsonProcessingException {
        //Whenever create is called then a new tweet is returned
        when(dao.create(any())).thenReturn(new Tweet());

        //Lets create a non null tweet to compare and test
        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(1d, -1d));
        coordinates.setType("Point");
        tweet.setText("test_tweet");
        tweet.setCoordinates(coordinates);

        service.postTweet(tweet);
        System.out.println(JsonUtil.toJson(tweet, true, true));

        //This should be true as a new tweet will be returned instead of creating our specified tweet
        assertEquals(tweet.getId(), null);
    }

    @Test
    public void findTweets(){
        Tweet tweet = new Tweet();

        String id = "1207064947664019457";

        //Whenever findById is called then a new tweet is returned
        when(dao.findById(any())).thenReturn(tweet);
        tweet = service.showTweet(id, null);

        //These should be true as it is just a new Tweet object
        assertEquals(tweet.getId(), null);
        assertEquals(tweet.getText(), null);
    }

    @Test
    public void deleteTweets(){
        //Lets create an array of Ids to pass to deleteTweets method
        String[] idList = {"1207064947664019457", "1207064949656375297", "0987064949656987873"};

        //When deleteById is called, just return a new Tweet
        when(dao.deleteById(any())).thenReturn(new Tweet());

        //Save returned tweets in a list of tweets
        List<Tweet> tweets = service.deleteTweets(idList);

        //Go through those saved tweets and test, all of them should be new tweets so this test should pass
        for (Tweet tweet : tweets) {
            assertEquals(tweet.getId(), null);
            assertEquals(tweet.getText(), null);
        }
    }
}