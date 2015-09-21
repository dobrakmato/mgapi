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
 * Interface representing joinable game room.
 */
public interface Room {
    /**
     * Returns UUID of this game room.
     *
     * @return UUID of this room
     */
    UUID getUUID();

    /**
     * Returns human readable name of this room.
     *
     * @return name of this room
     */
    String getName();

    /**
     * Returns current state of this game room.
     *
     * @return current state of this game room
     */
    RoomState getState();

    /**
     * Returns game object describing the game that is played in this room.
     *
     * @return game of this room
     */
    Game getGame();

    /**
     * Returns maximum amount of players that can play in this room.
     *
     * @return macimum amount of player for this room
     */
    int getMaxPlayers();

    /**
     * Returns set of players profiles that are currently in this game room.
     *
     * @return set of profiles of this room players
     */
    Set<PlayerProfile> getPlayerProfiles();
}
