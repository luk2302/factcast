package org.factcast.example.view;

import java.util.Optional;
import java.util.UUID;

import org.factcast.example.model.Bookmark;

import lombok.NonNull;

public interface BookmarksView {

    Optional<Bookmark> find(@NonNull UUID id);
}
