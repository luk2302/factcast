package org.factcast.store.pgsql.internal.listen;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.factcast.store.pgsql.PGConfigurationProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

@SuppressWarnings("all")
public class PGDriverManagerConnectionSupplier0Test {

    PGDriverManagerConnectionSupplier uut;

    @Mock
    private org.apache.tomcat.jdbc.pool.DataSource ds;

    @Before
    public void prepare() {

        initMocks(this);
        uut = new PGDriverManagerConnectionSupplier(ds, new PGConfigurationProperties());
    }

    @Test(expected = IllegalStateException.class)
    public void test_wrongDataSourceImplementation() {

        DataSource ds = mock(DataSource.class);
        new PGDriverManagerConnectionSupplier(ds, new PGConfigurationProperties());

        failBecauseExceptionWasNotThrown(IllegalStateException.class);
    }

    @Test
    public void test_buildCredentials() {

        // given
        PoolConfiguration poolConf = mock(PoolConfiguration.class);
        when(poolConf.getPassword()).thenReturn("testPassword");
        when(poolConf.getUsername()).thenReturn("testUsername");
        when(ds.getPoolProperties()).thenReturn(poolConf);

        // when
        Properties creds = uut.buildCredentialProperties(ds);

        // then
        assertEquals("testUsername", creds.get("user"));
        assertEquals("testPassword", creds.get("password"));
    }

    @Test(expected = NullPointerException.class)
    public void test_constructor() {

        new PGDriverManagerConnectionSupplier(null, new PGConfigurationProperties());

        failBecauseExceptionWasNotThrown(NullPointerException.class);

    }

    @Test
    public void testExceptionOnDriverManager_getConnection() throws Exception {
        String url = "jdbc:xyz:foo";
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setUrl(url);

        PGDriverManagerConnectionSupplier uut = new PGDriverManagerConnectionSupplier(ds,
                new PGConfigurationProperties());
        try {
            uut.get();
            org.junit.Assert.fail("Was expecting Exception");
        } catch (Exception e) {
            if (!(e.getCause() instanceof SQLException)) {
                fail("Wrong Exception type. Was expecting SQLException wrapped in a RuntimeException, but got ",
                        e);
            }
        }
    }
}