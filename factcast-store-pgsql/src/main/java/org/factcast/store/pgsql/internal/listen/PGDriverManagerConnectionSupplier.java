package org.factcast.store.pgsql.internal.listen;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.factcast.store.pgsql.PGConfigurationProperties;
import org.postgresql.jdbc.PgConnection;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PGDriverManagerConnectionSupplier implements Supplier<PgConnection> {
    @NonNull
    private final org.apache.tomcat.jdbc.pool.DataSource ds;

    @NonNull
    private final PGConfigurationProperties pgConfProperties;

    @Inject
    PGDriverManagerConnectionSupplier(@NonNull DataSource dataSource,
            @NonNull PGConfigurationProperties pgConfProperties) {
        this.pgConfProperties = pgConfProperties;

        if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
            ds = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
        } else {
            throw new IllegalStateException("expected "
                    + org.apache.tomcat.jdbc.pool.DataSource.class.getName() + " , but got "
                    + dataSource.getClass().getName());
        }
    }

    @Override
    public PgConnection get() {
        try {

            return (PgConnection) DriverManager.getDriver(ds.getUrl()).connect(ds.getUrl(),
                    buildCredentialProperties(ds));
        } catch (SQLException e) {
            final String msg = "Cannot acquire Connection from DriverManager: " + ds.getUrl();
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    @VisibleForTesting
    Properties buildCredentialProperties(org.apache.tomcat.jdbc.pool.DataSource ds) {

        Properties dbp = new Properties();

        final PoolConfiguration poolProperties = ds.getPoolProperties();
        if (poolProperties != null) {
            final String user = poolProperties.getUsername();
            if (user != null) {
                dbp.setProperty("user", user);
            }
            final String pwd = poolProperties.getPassword();
            if (pwd != null) {
                dbp.setProperty("password", pwd);
            }
        }

        dbp.setProperty("socketTimeout",
                String.valueOf(pgConfProperties
                        .getListenSocketTimeoutSeconds()));
        dbp.setProperty("connectionTimeout",
                String.valueOf(pgConfProperties
                        .getListenConnectionTimeoutSeconds()));
        dbp.setProperty("cancelSignalTimeout",
                String.valueOf(pgConfProperties
                        .getListenCancelSignalTimeoutSeconds()));

        return dbp;
    }

}
