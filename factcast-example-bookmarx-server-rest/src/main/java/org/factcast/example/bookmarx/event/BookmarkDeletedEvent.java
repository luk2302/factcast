package org.factcast.example.bookmarx.event;

import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkDeletedEvent {

    @NonNull
    private final UUID bookmarkId;
}
