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
package game;

import eu.matejkormuth.mgapi.api.RoomState;
import fw.config.Config;
import fw.state.GameState;
import fw.state.Shared;
import fw.state.StateGameRoom;
import fw.teams.properties.ColoredNickname;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TowerWarsLobbyState extends GameState {

    /*
     * We create default values to shared fields in default game state. Then
     * these values are copied to all other states when they are activated.
     */
    @Shared
    TowerWarsShared shared = new TowerWarsShared();

    // Will be injected.
    final Config config;

    // Minimum amount of players needed to start the game.
    private final int minimumPlayers;

    public TowerWarsLobbyState(StateGameRoom gameRoom, Config config) {
        super(RoomState.WAITING, gameRoom);
        this.config = config;

        this.minimumPlayers = config.getInt("minimumPlayers", 4);

        // Initialize team properties.
        shared.redTeam.addProperty(new ColoredNickname(ChatColor.RED));
        shared.greenTeam.addProperty(new ColoredNickname(ChatColor.GREEN));
        shared.yellowTeam.addProperty(new ColoredNickname(ChatColor.YELLOW));
        shared.blueTeam.addProperty(new ColoredNickname(ChatColor.BLUE));
        shared.defenseTeam.addProperty(new ColoredNickname(ChatColor.WHITE));
    }

    @Override
    public void onActivate(GameState oldState) {
        // Subscribe to events.
        getRoom().OnPlayerJoinEvent.subscribe(this::tryToStart);
    }

    @Override
    public void onDeactivate(GameState newState) {
        getRoom().OnPlayerJoinEvent.unsubscribe(this::tryToStart);
    }

    // Tries to start the game.
    private void tryToStart(Player player) {
        int currentAmount = getRoom().getPlayers().size();
        if (currentAmount >= minimumPlayers) {
            getRoom().activate(TowerWarsPhase1State.class);
        } else {
            getRoom().broadcast("Waiting for at least " + (minimumPlayers - currentAmount) + " player(s) to start!");
        }
    }

}
