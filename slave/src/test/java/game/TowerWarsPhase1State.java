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
import fw.Region;
import fw.Summoner;
import fw.Time;
import fw.state.GameState;
import fw.state.Shared;
import fw.state.StateGameRoom;
import fw.teams.Team;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class TowerWarsPhase1State extends GameState {

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

    Region redRegion;
    Region greenRegion;
    Region yellowRegion;
    Region blueRegion;
    Region defenseRegion;

    private Location summonerLocation;
    /**
     * Skeleton summon in the defense region.
     */
    private Summoner summoner = new Summoner(EntityType.SKELETON, summonerLocation);

    /**
     * Phase One length is 20 minutes.
     */
    private Countdown countdown = new Countdown(Time.ofMinutes(20), "Phase One");

    protected TowerWarsPhase1State(StateGameRoom gameRoom) {
        super(RoomState.PLAYING, gameRoom);
    }

    @Override
    public void onActivate(GameState oldState) {
        // Apply all team properties to all players.
        redTeam.applyAllProperties();
        greenTeam.applyAllProperties();
        blueTeam.applyAllProperties();
        yellowTeam.applyAllProperties();
        defenseTeam.applyAllProperties();

        // Start phase countdown and subscribe to countdown end.
        countdown.start();
        countdown.TimeUpEvent.subscribe(this::timeUp);

        // Activate summoner.
        summoner.activate();
    }

    private void timeUp(Void aVoid) {
        // Proceed to next phase.
        getRoom().activate(TowerWarsPhase2State.class);
    }

    @Override
    public void onDeactivate(GameState newState) {
        // Deactivate summoner.
        summoner.deactivate();

        // Unsubscribe and reset countdown.
        countdown.TimeUpEvent.unsubscribe(this::timeUp);
        countdown.reset();
    }
}
