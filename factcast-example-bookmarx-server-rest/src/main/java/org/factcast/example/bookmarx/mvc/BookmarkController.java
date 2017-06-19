package org.factcast.example.bookmarx.mvc;

import java.util.UUID;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(BookmarkResource.class)
@RequestMapping("/{ns}/bookmark")
public class BookmarkController {

    @RequestMapping("/{id}")
    public HttpEntity<BookmarkResource> bookmark(
            @PathVariable("ns") String ns, @PathVariable(value = "id") String id) {

        BookmarkResource b = new BookmarkResource(UUID.fromString(id), System.currentTimeMillis(),
                "buh", "https://ibm.com",
                "da big blue ");

        b.add(ControllerLinkBuilder.linkTo(this.getClass(), ns).slash(id).withSelfRel());

        return new ResponseEntity<BookmarkResource>(b, HttpStatus.OK);
    }
}