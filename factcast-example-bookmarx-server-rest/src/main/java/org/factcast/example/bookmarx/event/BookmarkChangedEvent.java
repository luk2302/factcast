package org.factcast.example.bookmarx.event;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Wither;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Wither
@AllArgsConstructor
public class BookmarkChangedEvent {
    @NonNull
    private final UUID bookmarkId;

    @JsonProperty
    Optional<String> newTitle = Optional.empty();

    @JsonProperty
    Optional<URL> newURL = Optional.empty();

    @JsonProperty
    Optional<String> newDescription = Optional.empty();

    // public static void main(String[] args) throws JsonProcessingException {
    //
    // final ObjectMapper m = new ObjectMapper();
    //
    // // m.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
    // m.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    //
    // final BookmarkChangedEvent e = new BookmarkChangedEvent();
    // System.out.println(m.writeValueAsString(e));
    // e.foo = Optional.of("hubba");
    // System.out.println(m.writeValueAsString(e));
    // }
}
