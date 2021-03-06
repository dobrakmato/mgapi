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
import fw.config.Config;
import fw.state.GameState;
import fw.state.Shared;
import fw.state.StateGameRoom;
import fw.teams.Team;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.function.Predicate;

public class TowerWarsPhase2State extends GameState {

    @Shared
    TowerWarsShared shared;

    // Will be injected.
    final Config config;

    /**
     * At least one player has SURVIVAL game mode.
     */
    private Predicate<Team> isAlive = (team) -> team
            .stream()
            .anyMatch(player -> player.getGameMode() == GameMode.SURVIVAL);
    /**
     * Phase One length is 10 minutes.
     */
    private final Countdown countdown;
    /**
     * Location of obsidian block.
     */
    private final Location obsidianLocation;
    /**
     * Current amount of lives of obsidian block.
     */
    private int obsidianLives;
    /**
     * Whether is block break protection in defense region enabled or not.
     */
    private boolean disableBlockBreaks;

    // TODO: Spectator & Dead
    // TODO: Whole defense team is killed win
    // TODO: All colored teams are killed win

    // Constructor.
    protected TowerWarsPhase2State(StateGameRoom gameRoom, Config config) {
        super(RoomState.PLAYING, gameRoom);
        this.config = config;

        // Initialize by configuration.
        this.countdown = new Countdown(config.getTime("phase2.length", Time.ofMinutes(10)), "Phase Two");
        this.obsidianLocation = config.getLocation("obsidian.location");
        this.obsidianLives = config.getInt("obsidian.lives", 3);

        // TODO: Register Bukkit events.
    }

    @EventHandler
    private void onEntityDamaged(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            if (this.getRoom().getPlayers().contains(event.getEntity())) {
                Player damaged = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();

                // If they are both from defense or colored team.
                if (!(getTeam(damaged) == shared.defenseTeam || getTeam(damager) == shared.defenseTeam)) {
                    // Cancel PVP.
                    event.setCancelled(true);
                }
            }
        }
    }

    private Team getTeam(Player player) {
        for (Player p : shared.redTeam) {
            if (player == p) {
                return shared.redTeam;
            }
        }

        for (Player p : shared.blueTeam) {
            if (player == p) {
                return shared.blueTeam;
            }
        }

        for (Player p : shared.greenTeam) {
            if (player == p) {
                return shared.greenTeam;
            }
        }

        for (Player p : shared.yellowTeam) {
            if (player == p) {
                return shared.yellowTeam;
            }
        }

        for (Player p : shared.defenseTeam) {
            if (player == p) {
                return shared.defenseTeam;
            }
        }

        return null;
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
        } else if (disableBlockBreaks && shared.defenseRegion.isInside(event.getBlock())) {
            // Cancel this block break event.
            event.setCancelled(true);
            event.getPlayer().sendMessage("You can't break blocks in phase 2.");
        }
    }

    @Override
    public void onActivate(GameState oldState) {
        // Start the countdown and subscribe.
        countdown.start();
        countdown.TimeUpEvent.subscribe(this::timeUp);

        // Activate block break protection.
        disableBlockBreaks = true;
    }

    private void timeUp(Void aVoid) {
        if (isAlive.test(shared.defenseTeam)) {
            // TODO: Defense team won.
        }
    }

    @Override
    public void onDeactivate(GameState newState) {
        // Unsubscribe and reset countdown.
        countdown.reset();
        countdown.TimeUpEvent.unsubscribe(this::timeUp);

        // Disable block break protection.
        disableBlockBreaks = false;

        // Reset obsidian lives.
        obsidianLives = config.getInt("obsidian.lives", 3);
    }
}
