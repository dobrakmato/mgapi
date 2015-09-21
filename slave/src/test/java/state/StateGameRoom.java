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
package state;

import eu.matejkormuth.mgapi.api.Game;
import eu.matejkormuth.mgapi.slave.api.GameRoom;
import eu.matejkormuth.mgapi.slave.comunication.NotifyingService;

import java.util.*;

public abstract class StateGameRoom extends GameRoom {

    // All possible game states.
    private final Map<Class<? extends GameState>, GameState> allStates = new HashMap<>();
    // Current (last) state.
    private GameState lastState = null;

    protected StateGameRoom(NotifyingService notifyingService, UUID uuid, String name, Game game, int maxPlayers) {
        super(notifyingService, uuid, name, game, maxPlayers);

        // Gather all state.
        List<GameState> allStates = new ArrayList<>();
        this.register(allStates);
        // Insert all states to map.
        for (GameState state : allStates) {
            if (state == null) {
                throw new NullPointerException("state can't be null!");
            }
            this.allStates.put(state.getClass(), state);
        }
    }

    public void activate(Class<? extends GameState> state) {
        GameState stateInstance = getInstance(state);

        if (stateInstance == null) {
            throw new IllegalArgumentException("invalid state class!",
                    new NullPointerException("state instance for class" + state.getName() + " was not found!"));
        }

        // Deactivate last game state.
        if (this.lastState != null) {
            this.lastState.onDeactivate(stateInstance);
        }

        // Activate new game state.
        this.setRoomState(stateInstance.getRoomState());
        stateInstance.onActivate(this.lastState);
        this.lastState = stateInstance;
    }

    // Provide empty implementations for no more useful methods.
    @Override
    protected void onCreate() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onReset() {
    }

    @Override
    protected void onDestroy() {
    }

    private GameState getInstance(Class<? extends GameState> state) {
        return allStates.get(state);
    }

    protected abstract void register(List<GameState> states);

    protected abstract Class<? extends GameState> getDefaultState();
}
