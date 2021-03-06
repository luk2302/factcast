package org.factcast.store.inmem;

import static org.junit.Assert.*;

import java.util.UUID;

import org.factcast.core.Fact;
import org.junit.Test;

public class InMemFact0Test {

    @Test
    public void testAddMeta() throws Exception {
        Fact f1 = Fact.of("{\"id\":\"" + UUID.randomUUID() + "\"}", "{}");
        InMemFact uut = new InMemFact(21, f1);
        assertEquals(21, uut.serial());
    }

    @Test
    public void testAddToExistingMeta() throws Exception {
        Fact f1 = Fact.of("{\"id\":\"" + UUID.randomUUID() + "\", \"meta\":{\"foo\":\"bar\"}}",
                "{}");
        InMemFact uut = new InMemFact(12, f1);
        assertEquals(12, uut.serial());
        assertEquals("bar", uut.meta("foo"));
    }

    @Test
    public void testReplaceFraudulentSer() throws Exception {
        Fact f1 = Fact.of("{\"id\":\"" + UUID.randomUUID() + "\", \"meta\":{\"_ser\":99999}}",
                "{}");
        assertEquals(99999, f1.serial());

        InMemFact uut = new InMemFact(12, f1);
        assertEquals(12, uut.serial());

    }

}
