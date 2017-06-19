package org.factcast.example.bookmarx.event;

import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Wither
public class BookmarkCreatedEvent {

    @NonNull
    private final UUID bookmarkId;

    @NonNull
    private String title;

    @NonNull
    private String url;

    private String description;

    @NonNull
    private final Set<String> tags = Sets.newHashSet();

}
