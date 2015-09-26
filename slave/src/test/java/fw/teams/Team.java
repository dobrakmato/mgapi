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
package fw.teams;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents collection of players.
 */
public class Team implements Iterable<Player> {

    private final List<Player> players = new ArrayList<>();
    private final Map<Class<? extends TeamProperty>, TeamProperty> properties = new HashMap<>();

    // Property collection methods.

    public void addProperty(TeamProperty property) {
        properties.put(property.getClass(), property);
    }

    public void removeProperty(TeamProperty property) {
        removeProperty(property.getClass());
    }

    public void removeProperty(Class<? extends TeamProperty> property) {
        properties.remove(property);
    }

    public boolean containsProperty(TeamProperty property) {
        return containsProperty(property.getClass());
    }

    public boolean containsProperty(Class<? extends TeamProperty> property) {
        return properties.containsKey(property);
    }

    public <T extends TeamProperty> T getProperty(Class<T> property) {
        return cast(properties.get(property));
    }

    @SuppressWarnings("unchecked")
    private <T extends TeamProperty> T cast(TeamProperty property) {
        return (T) property;
    }

    // Property functionality methods.

    /**
     * Applies all properties to all players of this team.
     */
    public void applyAllProperties() {
        properties.keySet().stream().forEach(this::applyProperty);
    }

    /**
     * Applies specified property to all players of this team.
     *
     * @param property property to apply
     * @throws NullPointerException if specified property is not found
     */
    public void applyProperty(Class<? extends TeamProperty> property) {
        TeamProperty property1 = getProperty(property);
        if (property1 == null) {
            throw new NullPointerException("Specified property not found!");
        }
        // Apply to all players.
        stream().forEach(property1::applyTo);
    }

    // Player collection delegate methods.

    @Override
    public Spliterator<Player> spliterator() {
        return players.spliterator();
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    public int size() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean contains(Object o) {
        return players.contains(o);
    }

    public boolean add(Player player) {
        return players.add(player);
    }

    public boolean remove(Object o) {
        return players.remove(o);
    }

    public void clear() {
        players.clear();
    }

    public Stream<Player> stream() {
        return players.stream();
    }

    public Stream<Player> parallelStream() {
        return players.parallelStream();
    }

    public void forEach(Consumer<? super Player> action) {
        players.forEach(action);
    }
}
