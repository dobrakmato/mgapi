/**
 * mgslave - MGAPI - Slave
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * <p>
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p>
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * <p>
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
package fw.state;

import eu.matejkormuth.mgapi.api.Game;
import eu.matejkormuth.mgapi.slave.api.Event;
import eu.matejkormuth.mgapi.slave.api.GameRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public abstract class StateGameRoom extends GameRoom {

    private static final Logger log = LoggerFactory.getLogger(StateGameRoom.class);

    // All possible game states.
    private final Map<Class<? extends GameState>, GameState> allStates = new HashMap<>();
    // Current (last) state.
    private GameState lastState = null;

    /**
     * Called before state is going to be changed.
     */
    public final Event<GameState> onStateChange = new Event<>();

    protected StateGameRoom(UUID uuid, String name, Game game, int maxPlayers) {
        super(uuid, name, game, maxPlayers);

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

        // Process event hooks.
        try {
            onStateChange.call(stateInstance);
        } catch (Exception e) {
            log.error("Error while executing event onStateChange!", e);
        }

        // Deactivate last game state.
        if (this.lastState != null) {
            this.lastState.onDeactivate(stateInstance);
        }

        // Copy shared values.
        if (this.lastState != null) {
            copySharedFromTo(this.lastState, stateInstance);
        }

        // Activate new game state.
        this.setRoomState(stateInstance.getRoomState());
        stateInstance.onActivate(stateInstance);
        this.lastState = stateInstance;
    }

    private void copySharedFromTo(GameState from, GameState to) {
        for (Map.Entry<String, Field> entry : from.shared.entrySet()) {
            // Fix accessibility.
            if (!entry.getValue().isAccessible()) {
                entry.getValue().setAccessible(true);
            }

            // Set value.
            try {
                Field target = to.getClass().getDeclaredField(entry.getKey());
                if (!target.getType().equals(entry.getValue().getType())) {
                    // Type mismatch.
                    log.error("Shared field type mismatch in {}.{} of type {} and {}.{} of type {}!",
                            from.getClass().getName(), entry.getValue().getName(), entry.getValue().getType().getName(),
                            to.getClass().getName(), target.getName(), target.getType().getName());
                }
                try {
                    target.set(to, entry.getValue().get(from));
                } catch (IllegalAccessException e) {
                    log.error("Can't copy shared field!", e);
                }
            } catch (NoSuchFieldException e) {
                log.error("Shared field {} of type {} not present in {}!", entry.getKey(),
                        entry.getValue().getType().getName(), to.getClass().getName());
            }

        }
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
}
