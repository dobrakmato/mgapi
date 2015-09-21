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

/**
 * State of Room. Each state describes what is currently happening in game room.
 */
public enum RoomState {
    /**
     * The room is disabled and players can't join the room at this moment.
     */
    DISABLED(false),
    /**
     * The room is waiting for players to join to meet minimum amount of users needed to start the game.
     */
    WAITING(true),
    /**
     * The room is in playing state, game is in progress and new players can't join the game.
     */
    PLAYING(false),
    /**
     * The room is in playing state, game is in progress but new players can still join the game.
     */
    PLAYING_CANJOIN(true),
    /**
     * The room is resetting itself. This is much like temporary disabled state, which is fully automated and
     * lasts about 1-15 seconds. Players can't join the room at this moment.
     */
    RESETING(false);

    /**
     * Whether the room is at this state joinable by players.
     */
    private final boolean canJoin;

    /**
     * Creates a new RoomState.
     *
     * @param canJoin whether player can join room at this state
     */
    RoomState(boolean canJoin) {
        this.canJoin = canJoin;
    }

    /**
     * Returns whether the players can join the room at this state.
     *
     * @return true if this room is joinable at this moment, false otherwise
     */
    public boolean canJoin() {
        return this.canJoin;
    }
}
