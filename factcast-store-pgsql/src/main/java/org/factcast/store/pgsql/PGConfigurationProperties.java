package org.factcast.store.pgsql;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.experimental.Accessors;

@Component
@ConfigurationProperties(prefix = "factcast.pg")
@Data
@Accessors(fluent = false)
public class PGConfigurationProperties {
    /**
     * defines the number of Facts being retrieved with one Page Query for
     * PageStrategy.PAGED
     */
    int pageSize = 1000;

    /**
     * The capacity of the queue for PageStrategy.QUEUED
     */
    int queueSize = 1000;

    /**
     * The factor to apply, when fetching/queuing Ids rather than Facts
     * (assuming, that needs just a fraction of Heap and is way fater to flush
     * to the client)
     */
    int idOnlyFactor = 100;

    /**
     * Defines the Strategy used for Paging in the Catchup Phase.
     */
    CatchupStrategy catchupStrategy = CatchupStrategy.getDefault();

    /**
     * Fetch Size used when filling the Queue, defaults to 4 (25% of the
     * queue-size)
     */
    int queueFetchRatio = 4;

    public int getPageSizeForIds() {
        return pageSize * idOnlyFactor;
    }

    public int getQueueSizeForIds() {
        return queueSize * idOnlyFactor;
    }

    public int getFetchSizeForIds() {
        return getQueueSizeForIds() / queueFetchRatio;
    }

    public int getFetchSize() {
        return getQueueSize() / queueFetchRatio;
    }

}
