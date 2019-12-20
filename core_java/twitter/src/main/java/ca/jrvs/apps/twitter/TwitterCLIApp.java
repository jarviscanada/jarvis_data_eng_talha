package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TwitterCLIApp {

    private Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller){
        this.controller = controller;
    }

    public static void main(String[] args) {

        //First lets get secrets from environment variables
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        //Now lets create components and chain dependencies
        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);
        CrdDao dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        //Now we can start our app
        app.run(args);
    }

    public void run(String[] args) {

        //First check if there are no arguments
        if (args.length == 0){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete [options]");
        }

        //Now lets handle the user input by sending it to controller
        switch (args[0].toLowerCase()){
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweet(args)
                        .forEach(this :: printTweet);
                break;
            default:
                throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete [options]");
        }
    }

    private void printTweet(Tweet tweet){
        try{
            System.out.println(JsonUtil.toJson(tweet,true, true));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert tweet object to string ",  e);
        }
    }


}
