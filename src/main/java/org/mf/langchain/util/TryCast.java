package org.mf.langchain.util;

import org.mf.langchain.exception.InvalidData;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TryCast {

    public static <T> T cast(Object obj, Class<T> clazz, Supplier<Throwable> exception) throws Throwable {
        if (clazz.isInstance(obj)) {
            return (T) obj;
        }
        throw exception.get();
    }

}
