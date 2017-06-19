package org.factcast.example.bookmarx.event;

import java.util.UUID;

import lombok.Data;
import lombok.NonNull;

@Data
public class TagRemovedEvent {

    @NonNull
    private final UUID bookmarkId;

    @NonNull
    private final String tag;

}
