package org.factcast.store.pgsql.internal;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.factcast.store.pgsql.internal.listen.PGListener;
import org.factcast.store.pgsql.internal.listen.PGListener.FactInsertionEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.postgresql.PGNotification;
import org.postgresql.core.Notification;
import org.postgresql.jdbc.PgConnection;

import com.google.common.eventbus.AsyncEventBus;

@RunWith(MockitoJUnitRunner.class)
public class PGSqlListener0Test {

    @Mock
    Supplier<PgConnection> ds;

    @Mock
    AsyncEventBus bus;

    @Mock
    PgConnection conn;

    @Mock
    PreparedStatement ps;

    Predicate<Connection> tester = c -> true;

    @Captor
    ArgumentCaptor<PGListener> captor;

    @org.junit.Before
    public void setUp() throws SQLException {
        Mockito.when(ds.get()).thenReturn(conn);
        Mockito.when(conn.prepareStatement(anyString())).thenReturn(ps);
        Mockito.when(ps.execute()).thenReturn(true);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckFails() throws Exception {
        tester = mock(Predicate.class);
        Mockito.when(tester.test(any())).thenReturn(false, false, true, false);

        PGListener l = new PGListener(ds, bus, tester);
        l.afterPropertiesSet();
        verify(bus, times(3)).post(any(FactInsertionEvent.class));
        verifyNoMoreInteractions(bus);

    }

    @Test
    public void testListen() throws Exception {
        PGListener l = new PGListener(ds, bus, tester);
        l.afterPropertiesSet();

        sleep(100);
        verify(bus).post(any(FactInsertionEvent.class));
        verifyNoMoreInteractions(bus);

        verify(conn).prepareStatement(PGConstants.LISTEN_SQL);
        verify(ps).execute();

    }

    @Test
    public void testNotify() throws Exception {

        PGListener l = new PGListener(ds, bus, tester);

        when(conn.getNotifications(anyInt())).thenReturn(new PGNotification[] { //
                new Notification(PGConstants.CHANNEL_NAME, 1), //
                new Notification(PGConstants.CHANNEL_NAME, 1), //
                new Notification(PGConstants.CHANNEL_NAME, 1) }, //
                new PGNotification[] { new Notification(PGConstants.CHANNEL_NAME, 2) },
                new PGNotification[] { new Notification(PGConstants.CHANNEL_NAME, 3) }, //
                null);
        l.afterPropertiesSet();

        sleep(400);

        // 4 posts: one scheduled, 3 from notifications
        verify(bus, times(4)).post(any(FactInsertionEvent.class));

    }

    @Test
    public void testNotifyScheduled() throws Exception {

        PGListener l = new PGListener(ds, bus, tester);

        when(conn.getNotifications(anyInt())).thenReturn(null);
        l.afterPropertiesSet();

        sleep(200);

        // one scheduled
        verify(bus, times(1)).post(any(FactInsertionEvent.class));

    }

    @Test
    public void testStop() throws Exception {
        Mockito.when(ds.get()).thenReturn(conn);
        Mockito.when(conn.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        PGListener l = new PGListener(ds, bus, tester);
        l.afterPropertiesSet();
        l.destroy();

        sleep(50);
        verify(conn).close();
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testStopWithoutStarting() throws Exception {
        Mockito.when(ds.get()).thenReturn(conn);
        Mockito.when(conn.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        PGListener l = new PGListener(ds, bus, tester);
        l.destroy();

        verifyNoMoreInteractions(conn);
    }
}
