package org.factcast.core;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestHelper {

    public static void expectNPE(Callable<?> e) {
        expect(NullPointerException.class, e);
    }

    public static void expect(Class<? extends Throwable> ex, Callable<?> e) {
        try {
            e.call();
            fail("expected " + ex);
        } catch (Throwable actual) {
            if (!ex.isInstance(actual)) {
                fail("Wrong exception, expected " + ex + " but got " + actual);
            }

        }
    }

}
