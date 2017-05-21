package org.factcast.store.pgsql.internal;

import java.util.concurrent.CompletableFuture;

import org.factcast.core.Fact;
import org.factcast.core.subscription.Subscription;
import org.factcast.core.subscription.SubscriptionImpl;
import org.factcast.core.subscription.SubscriptionRequestTO;
import org.factcast.core.subscription.observer.FactObserver;
import org.factcast.store.pgsql.internal.catchup.PGCatchUpFactory;
import org.factcast.store.pgsql.internal.query.PGFactIdToSerialMapper;
import org.factcast.store.pgsql.internal.query.PGLatestSerialFetcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import lombok.RequiredArgsConstructor;

/**
 * Creates Subscription
 * 
 * @author uwe.schaefer@mercateo.com
 *
 */
// TODO integrate with PGQuery
@RequiredArgsConstructor
@Component
class PGSubscriptionFactory {
    final JdbcTemplate jdbcTemplate;

    final EventBus eventBus;

    final PGFactIdToSerialMapper idToSerialMapper;

    final PGLatestSerialFetcher fetcher;

    final PGCatchUpFactory catchupFactory;

    public Subscription subscribe(SubscriptionRequestTO req, FactObserver observer) {
        final SubscriptionImpl<Fact> subscription = SubscriptionImpl.on(observer);

        PGFactStream pgsub = new PGFactStream(jdbcTemplate, eventBus, idToSerialMapper,
                subscription, fetcher, catchupFactory);
        CompletableFuture.runAsync(() -> pgsub.connect(req));

        return subscription.onClose(pgsub::close);
    }

}
