package org.factcast.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.factcast.core.store.FactStore;

import lombok.NonNull;

/**
 * Main interface to work against as a client.
 * 
 * FactCast provides methods to publish Facts in a sync/async fashion, as well
 * as a subscription mechanism to listen for changes and catching up.
 * 
 * @author uwe.schaefer@mercateo.com
 *
 */
public interface FactCast extends ReadFactCast {

    void publish(@NonNull List<? extends Fact> factsToPublish);

    /// ---------- defaults

    default void publish(@NonNull Fact factToPublish) {
        publish(Arrays.asList(factToPublish));
    }

    default UUID publishWithMark(@NonNull Fact factToPublish) {
        MarkFact mark = new MarkFact();
        publish(Arrays.asList(factToPublish, mark));
        return mark.id();
    }

    default UUID publishWithMark(@NonNull List<Fact> factsToPublish) {

        MarkFact mark = new MarkFact();

        List<Fact> factsWithMarkAdded = new ArrayList<>(factsToPublish);
        factsWithMarkAdded.add(mark);
        publish(factsWithMarkAdded);

        return mark.id();
    }

    // Factory

    static FactCast from(@NonNull FactStore store) {
        return new DefaultFactCast(store);
    }

    static ReadFactCast fromReadOnly(@NonNull FactStore store) {
        return new DefaultFactCast(store);
    }

}
