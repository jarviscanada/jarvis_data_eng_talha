package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "Incorrect Usage: TwitterCLIApp post \"tweet_text\" \"longitude:latitude\"");
        }

        String tweet_text = args[1];
        String coordinates = args[2];
        String coordArray[] = coordinates.split(COORD_SEP);

        //Check if we have long and lat, and if tweet_text is empty
        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_text)) {
            throw new IllegalArgumentException(
                    "Incorrect Location, Usage: TwitterCLIApp post \"tweet_text\" \"longitude:latitude\"");
        }

        Double lon = null;
        Double lat = null;

        try {
            lon = Double.parseDouble(coordArray[0]);
            lat = Double.parseDouble(coordArray[1]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(
                    "Incorrect Location, Usage: TwitterCLIApp post \"tweet_text\" \"longitude:latitude\"");
        }

        //Create a new tweet to post with specified text and coordinates
        Tweet postTweet = new Tweet();
        Coordinates tweetCoords = new Coordinates();
        tweetCoords.setCoordinates(Arrays.asList(lon, lat));
        tweetCoords.setType("Point");
        postTweet.setText(tweet_text);
        postTweet.setCoordinates(tweetCoords);

        //Finally we can all the postTweet method with our created tweet
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Incorrect Usage: TwitterCLIApp show \"tweet_id\"");
        }

        String id = args[1];

        //Check if ID specified by user is valid
        if (!Pattern.matches("^\\d+$", id)) {
            throw new IllegalArgumentException("Tweet ID is not correct format");
        }

        //Finally we can call showTweet method in service
        return service.showTweet(id, null);
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Incorrect Usage: TwitterCLIApp delete \"tweet_id,tweet_id,..\" (seperated by commas)");
        }

        String[] allIDs = args[1].split(COMMA);

        //Check if all the IDs specified by user are valid
        for (String id : allIDs){
            if (!Pattern.matches("^\\d+$", id)) {
                throw new IllegalArgumentException("Tweet ID is not correct format");
            }
        }

        //Lastly, we can call our deleteTweets method with given args
        return service.deleteTweets(allIDs);
    }
}