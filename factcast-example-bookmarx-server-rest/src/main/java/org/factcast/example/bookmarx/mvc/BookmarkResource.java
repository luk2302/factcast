package org.factcast.example.bookmarx.mvc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Relation(value = "bookmark", collectionRelation = "bookmarks")
@Getter
public class BookmarkResource extends ResourceSupport {

    @JsonProperty
    UUID id;

    @JsonProperty
    long lastModified;

    @JsonProperty
    String title;

    @JsonProperty
    String url;

    @JsonProperty
    String description;

    @JsonProperty
    Set<String> tags = new HashSet<>();

}