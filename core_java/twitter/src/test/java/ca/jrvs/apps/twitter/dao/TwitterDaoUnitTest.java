package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    //Define tweetJsonStr to use later
    String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Wed Dec 18:01:10 +000 2019\", \n"
            + "   \"id\":1207828981791252480,\n"
            + "   \"id_str\":\"1207828981791252480\",\n"
            + "   \"text\":\"Sample Tweet Text\",\n"
            + "   \"entities\":{\n"
            + "       \"hashtag\":[],\n"
            + "       \"user_mentions\":[]\n"
            + "   },\n"
            + "   \"coordinated\":null,\n"
            + "   \"retweet_count\":0,\n"
            + "   \"favorited_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";

    //Save expected tweet to use later
    private Tweet expectedTweet;

    //Set a sample ID to test later
    String testId = "1207828981791252480";

    @Test
    public void createTweet() throws Exception {

        String text = "test_tweet_" + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            Tweet postTweet = new Tweet();
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");

            postTweet.setText(text);
            postTweet.setCoordinates(coordinates);

            dao.create(postTweet);
            fail();
        }
        catch (RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

        //Now we mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet2 = new Tweet();
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setCoordinates(Arrays.asList(lon, lat));
        coordinates2.setType("Point");

        tweet2.setText(text);
        tweet2.setCoordinates(coordinates2);

        Tweet tweet = spyDao.create(tweet2);

        //Make sure tweet and its text are not null, should return the expectedTweet
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void findTweet() throws Exception {

        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.findById(testId);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        //Now we mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet = spyDao.findById(testId);

        //This should also return expectedTweet so make sure it is not null
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteTweet() throws Exception {

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.deleteById(testId);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        //Now we mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet = spyDao.deleteById(testId);

        //This should also return expectedTweet so make sure it is not null
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}