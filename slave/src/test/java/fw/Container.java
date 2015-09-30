/**
 * mgslave - MGAPI - Slave
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Represents application dependency injection container.
 *
 * @author Matej Kormuth
 */
public class Container {

    // Internal map.
    private static final Map<Class<?>, Object> container = new HashMap<>();

    /**
     * Returns value for specified class. If container contains no mapping
     * for specified class, this will throw IllegalArgumentException. If the
     * instance was previously set to null by put call, or factory produced
     * null value, this will return null.
     * <p>
     * If you don't want null values, use optional(Class) method.
     *
     * @param clazz class of type of instance
     * @param <T>   type to object
     * @return instance or null
     * @throws IllegalArgumentException if clazz is null or if mapping for
     *                                  this type was not found
     */
    public static <T> T get(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }

        if (!container.containsKey(clazz)) {
            throw new IllegalArgumentException("no mapping was found for class " + clazz.getName());
        }

        Object obj = container.get(clazz);

        // instanceof operator returns false if obj is null.
        if (obj instanceof Supplier) {
            return cast(((Supplier) obj).get());
        } else {
            return cast(obj);
        }
    }

    /**
     * Returns optional value for specified class. If container contains no
     * mapping for specified class, this will return empty optional. Also
     * if instance was set to null, or factory did return null, this will
     * also return null.
     * <p>
     * If you don't care about null values, use get(Class) method.
     *
     * @param clazz class of type of instance
     * @param <T>   type of object
     * @return optional of instance of specified type
     * @throws IllegalArgumentException if clazz is null
     */
    public static <T> Optional<T> optional(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }

        if (!container.containsKey(clazz)) {
            return Optional.empty();
        }

        Object obj = container.get(clazz);

        // instanceof operator returns false if obj is null.
        if (obj instanceof Supplier) {
            return Optional.ofNullable(cast(((Supplier) obj).get()));
        } else {
            return Optional.ofNullable(cast(obj));
        }
    }

    /**
     * Puts specified instance to container by specified class. This
     * instance will be returned by every call to get with same class.
     *
     * @param clazz    class of specified type
     * @param instance instance of specified type
     * @param <T>      type of object
     */
    public static <T> void put(Class<T> clazz, T instance) {
        container.put(clazz, instance);
    }

    /**
     * Puts specified factory to container, so every call to get will
     * invoke passed factory to create instance of specified type.
     *
     * @param clazz   class of specified type
     * @param factory factory of specified type
     * @param <T>     type of object
     */
    public static <T> void put(Class<T> clazz, Supplier<T> factory) {
        container.put(clazz, factory);
    }

    // Internally used to cast (un)safely.
    @SuppressWarnings("unchecked")
    private static <T> T cast(Object o) {
        return (T) o;
    }

    /**
     * Specifies that this value should be injected.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Inject {

    }
}
