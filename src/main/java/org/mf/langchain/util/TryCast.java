package org.mf.langchain.util;

import org.mf.langchain.exception.InvalidData;
import org.modelmapper.ModelMapper;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TryCast {

    public static <T> T cast(Object obj, Class<T> clazz, Supplier<Throwable> exception) throws Throwable {
        var mm = new ModelMapper();
        var x = mm.map(obj, clazz);
        return x;
    }

}
