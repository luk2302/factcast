package org.factcast.store.pgsql.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import com.impossibl.postgres.api.jdbc.PGConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * Used to test if a connection is still alive.
 * 
 * Even though CPools provide this already, this one is intended to used with
 * the cone {@link PGConnection}, that listens to changes on the fact table and
 * thus should not be reused in a CPool.
 * 
 * @author usr
 *
 */

@Slf4j
class ConnectionTester implements Predicate<Connection> {

    @Override
    public boolean test(@Nonnull Connection connection) {

        log.trace("Testing Connection :", connection);

        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT 42");
                    ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                if (resultSet.getInt(1) == 42) {
                    log.trace("Connection test passed");
                    return true;
                } else {
                    log.trace("Connection test failed");
                }
            } catch (SQLException e) {
                log.warn("Connection test failed with exception", e);
            }
        }
        return false;
    }
}
