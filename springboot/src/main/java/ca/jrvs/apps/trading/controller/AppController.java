package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AppController {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/health")
    public String health() {
        return "I'm very very healthy!";
    }
}
