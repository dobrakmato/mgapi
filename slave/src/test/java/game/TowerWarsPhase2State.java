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

import eu.matejkormuth.mgapi.api.RoomState;
import fw.Countdown;
import fw.Time;
import fw.state.GameState;
import fw.state.Shared;
import fw.state.StateGameRoom;
import fw.teams.Team;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.function.Predicate;

public class TowerWarsPhase2State extends GameState {

    @Shared
    Team redTeam;
    @Shared
    Team greenTeam;
    @Shared
    Team yellowTeam;
    @Shared
    Team blueTeam;
    @Shared
    Team defenseTeam;

    /**
     * At least one player has SURVIVAL game mode.
     */
    private Predicate<Team> isAlive = (team) -> team
            .stream()
            .anyMatch(player -> player.getGameMode() == GameMode.SURVIVAL);
    /**
     * Phase One length is 10 minutes.
     */
    private Countdown countdown = new Countdown(Time.ofMinutes(10), "Phase Two");
    /**
     * Location of obsidian block.
     */
    private Location obsidianLocation;
    /**
     * Current amount of lives of obsidian block.
     */
    private int obsidianLives = 3;

    // TODO: PVP
    // TODO: Spectator & Dead
    // TODO: Bedrock layer
    // TODO: Whole defense team is killed win
    // TODO: All colored teams are killed win

    // Constructor.
    protected TowerWarsPhase2State(StateGameRoom gameRoom) {
        super(RoomState.PLAYING, gameRoom);
    }

    // When obsidian is broken.
    @EventHandler
    private void onBlockBroken(final BlockBreakEvent event) {
        // We only care about obsidian block breaks.
        if (event.getBlock().getLocation().equals(obsidianLocation)) {
            // Decrease amount of lives.
            obsidianLives--;
            // Check for win.
            if (obsidianLives <= 0) {
                // TODO: Some colored team won.
            }
        }
    }

    @Override
    public void onActivate(GameState oldState) {
        // Start the countdown and subscribe.
        countdown.start();
        countdown.TimeUpEvent.subscribe(this::timeUp);
    }

    private void timeUp(Void aVoid) {
        if (isAlive.test(defenseTeam)) {
            // TODO: Defense team won.
        }
    }

    @Override
    public void onDeactivate(GameState newState) {
        // Unsubscribe and reset countdown.
        countdown.reset();
        countdown.TimeUpEvent.unsubscribe(this::timeUp);

        // Reset obsidian lives.
        obsidianLives = 3;
    }
}
