package com.hidiscuss.backend.hateoas;


import com.hidiscuss.backend.controller.LoginController;
import com.hidiscuss.backend.controller.dto.response.Response;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class LoginResource extends EntityModel<Response> {
    public LoginResource(Response content, Link... links) {
        super(content, Arrays.asList(links));
        add(linkTo(LoginController.class).slash("login").withSelfRel());
    }
}
