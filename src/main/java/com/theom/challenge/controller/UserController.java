package com.theom.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theom.challenge.api.Response;
import com.theom.challenge.common.ResourceNotFoundException;
import com.theom.challenge.model.User;
import com.theom.challenge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")

/**
 * Controller class to handle user requests
 */
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public Response create(@RequestBody User request) throws JsonProcessingException, InterruptedException {

        logger.info("Create User request received");
        logger.debug("Request payload:" + new ObjectMapper().writeValueAsString(request));
        userService.insertUser(request);
        Response resp = new Response();
        resp.setMessage("Successfully inserted user");
        return resp;
    }

    @GetMapping("/{uuid}")
    public Response getUser(@PathVariable String uuid) {

        logger.info("Get User request received for ID - "+uuid);
        User user = userService.getUser(uuid);
        Response resp = new Response();
        if (user != null) {

            resp.setMessage("Success");
            resp.setData(user);
        }

        else {
            logger.info("User Not found - "+uuid);
            throw new ResourceNotFoundException(uuid);
        }
        return resp;
    }

    @DeleteMapping("/{uuid}")
    public Response deleteUser(@PathVariable String uuid) {

        logger.info("Delete User request received for ID - "+uuid);
        Response resp = new Response();
        if (userService.deleteUser(uuid)) {
            resp.setMessage("Deleted");
            return resp;
        }
        else {
            logger.info("User Not found - "+uuid);
            throw new ResourceNotFoundException(uuid);
        }
    }
}
