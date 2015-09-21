/**
 * api - Minigame API and master server.
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
package eu.matejkormuth.mgapi.api;

import java.util.Set;
import java.util.UUID;

/**
 * Interface representing either lobby or game Minecraft server instance.
 */
public interface Server {
    /**
     * Returns UUID of this server.
     *
     * @return UUID of this server
     */
    UUID getUUID();

    /**
     * Returns human readable name of this server.
     *
     * @return name of this server
     */
    String getName();

    /**
     * Returns API access key assigned to this server.
     *
     * @return this server's API access key
     */
    AccessKey getAccessKey();

    /**
     * Returns usage type of this server.
     *
     * @return type of this server
     */
    ServerType getServerType();

    /**
     * Returns current matchmaking state of this server.
     *
     * @return current matchmaking state
     */
    ServerState getState();

    /**
     * Returns set of all rooms that are created on this server.
     *
     * @return set of all rooms of this server
     */
    Set<Room> getRooms();

    /**
     * Returns set of all installed game on this server.
     *
     * @return set of all installed games on this server
     */
    Set<Game> getInstalledGames();
}
