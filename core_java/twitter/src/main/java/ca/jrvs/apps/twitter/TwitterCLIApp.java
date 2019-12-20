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

import java.util.List;

public class TwitterCLIApp {

    private Controller controller;

    //@Autowired
    public TwitterCLIApp(Controller controller){
        this.controller = controller;
    }

    public static void main(String[] args) {

        //First lets get secrets from environment variables
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        //Create components and chain dependencies
        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);
        CrdDao dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.run(args);
    }

    private void run(String[] args) {
        if (args.length == 0){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete [options]");
        }

        //Handle the user input by sending it to controller
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
