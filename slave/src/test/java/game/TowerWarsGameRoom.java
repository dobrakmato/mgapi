package game; /**
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

import eu.matejkormuth.mgapi.api.Game;
import fw.config.Config;
import fw.state.GameState;
import fw.state.StateGameRoom;

import java.util.List;
import java.util.UUID;

public class TowerWarsGameRoom extends StateGameRoom {

    private final Config config;

    protected TowerWarsGameRoom(UUID uuid, String name, Game game, int maxPlayers) {
        super(uuid, name, game, maxPlayers);

        this.config = null; // TODO: Inject the config here!
        // ConfigManager.loadXml("room_" + name);
    }

    @Override
    protected void onCreate() {
        // Start state game room by entering first state.
        activate(TowerWarsLobbyState.class);
    }

    @Override
    protected void register(List<GameState> states) {
        states.add(new TowerWarsLobbyState(this, this.config));
        states.add(new TowerWarsPhase1State(this, this.config));
        states.add(new TowerWarsPhase2State(this, this.config));
        states.add(new TowerWarsResettingState(this));
    }
}
