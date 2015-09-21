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
package eu.matejkormuth.mgapi.slave.api.impl;

import eu.matejkormuth.mgapi.api.PlayerProfile;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class PlayerProfileImpl implements PlayerProfile {

    private UUID uuid;
    private String name;
    private Set<PlayerProfile> friends;
    private String ipAddress;

    public PlayerProfileImpl() {
    }

    public PlayerProfileImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(this.name);
    }

    @Override
    public Optional<Set<PlayerProfile>> getFriends() {
        return Optional.ofNullable(this.friends);
    }

    @Override
    public Optional<String> getIPAddress() {
        return Optional.ofNullable(this.ipAddress);
    }

    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setFriends(Set<PlayerProfile> friends) {
        this.friends = friends;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
