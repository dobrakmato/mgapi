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
package fw.chat;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Channel implements Iterable<Player> {

    private final String name;
    private List<Player> receivers = new ArrayList<>();

    public Channel(String name) {
        this.name = name;
    }

    public int size() {
        return receivers.size();
    }

    public boolean isEmpty() {
        return receivers.isEmpty();
    }

    public Iterator<Player> iterator() {
        return receivers.iterator();
    }

    public void clear() {
        receivers.clear();
    }

    public boolean add(Player player) {
        return receivers.add(player);
    }

    public boolean remove(Player o) {
        return receivers.remove(o);
    }

    public Stream<Player> stream() {
        return receivers.stream();
    }

    public void broadcast(String message) {
        stream().forEach(p -> p.sendMessage(message));
    }

    public void broadcast(Player sender, String message) {
        String formatted = sender + ": " + message;
        stream().forEach(p -> p.sendMessage(formatted));
    }
}
