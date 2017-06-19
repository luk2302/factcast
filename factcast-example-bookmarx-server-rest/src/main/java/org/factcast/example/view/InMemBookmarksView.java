package org.factcast.example.view;

import lombok.NonNull;

public interface InMemBookmarksView {

    BookmarksView forNamespace(@NonNull String ns);

}
