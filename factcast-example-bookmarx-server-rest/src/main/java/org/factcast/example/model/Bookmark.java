package org.factcast.example.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bookmark {

    @JsonProperty
    final UUID id;

    @JsonProperty
    long lastModified = System.currentTimeMillis();

    @JsonProperty
    String title;

    @JsonProperty
    String url;

    @JsonProperty
    String description;

    @JsonProperty
    Set<String> tags = new HashSet<>();

}
