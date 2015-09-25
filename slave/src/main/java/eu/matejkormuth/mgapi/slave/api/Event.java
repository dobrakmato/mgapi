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
package eu.matejkormuth.mgapi.slave.api;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents observer-like subscribe event using Java 8 Consumer(s).
 *
 * @param <T> type of argument of this event
 */
public final class Event<T> {

    // List of all event consumers.
    private final List<Consumer<T>> consumers;

    /**
     * Creates new Event object.
     */
    public Event() {
        consumers = new ArrayList<>();
    }

    /**
     * Adds specified consumer to list of subscribers, so it will receive event arguments when
     * event is called.
     *
     * @param consumer event argument consumer
     */
    public void subscribe(@Nonnull Consumer<T> consumer) {
        consumers.add(consumer);
    }

    /**
     * Removes specified consume from list of subscribers, so it will no more receive event
     * arguments when event is called.
     *
     * @param consumer event argument consumer
     */
    public void unsubscribe(@Nonnull Consumer<T> consumer) {
        consumers.remove(consumer);
    }

    /**
     * Passes specified argument object to all consumers of this event.
     *
     * @param argument object that will be passed to consumers of this event
     */
    public void call(T argument) {
        for (Consumer<T> consumer : consumers) {
            consumer.accept(argument);
        }
    }
}
