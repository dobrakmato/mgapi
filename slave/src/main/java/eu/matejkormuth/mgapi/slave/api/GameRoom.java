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
package eu.matejkormuth.mgapi.slave.api;

import eu.matejkormuth.mgapi.api.Game;
import eu.matejkormuth.mgapi.api.PlayerProfile;
import eu.matejkormuth.mgapi.api.Room;
import eu.matejkormuth.mgapi.api.RoomState;
import eu.matejkormuth.mgapi.slave.api.impl.PlayerProfileImpl;
import eu.matejkormuth.mgapi.slave.comunication.Notifier;
import eu.matejkormuth.mgapi.slave.comunication.NotifyingService;
import net.jodah.expiringmap.ExpiringMap;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class GameRoom implements Room, MatchmakingTarget {

    // Slot is reserved for 7 seconds.
    private static final int RESERVED_SLOT_EXPIRATION = 7;

    private final NotifyingService notifyingService;
    protected final Notifier notifier;

    // State changing variables.
    private final State<RoomState> state = State.of(RoomState.DISABLED);

    private final Map<PlayerProfile, Object> reservations = ExpiringMap.builder()
            .expiration(RESERVED_SLOT_EXPIRATION, TimeUnit.SECONDS)
            .build();
    private final Set<Player> players = new HashSet<>();

    // Game room events.
    public final Event<Player> OnPlayerJoinEvent = new Event<>();
    public final Event<Player> OnPlayerLeaveEvent = new Event<>();

    // Basic variables.
    private final UUID uuid;
    private final String name;
    private final Game game;
    private final int maxPlayers;

    protected GameRoom(NotifyingService notifyingService, UUID uuid, String name, Game game, int maxPlayers) {
        this.notifyingService = notifyingService;
        this.uuid = uuid;
        this.name = name;
        this.game = game;
        this.maxPlayers = maxPlayers;

        // Create state notifier.
        this.notifier = new Notifier(this.notifyingService, this);

        // Bind all state variables.
        this.state.bind(this.notifier::push);
    }

    protected void setRoomState(RoomState state) {
        this.state.set(state);
    }

    public void create() {
        this.state.set(RoomState.WAITING);

        this.onCreate();
    }

    public void start() {
        this.state.set(RoomState.PLAYING);

        this.onStart();
    }

    public void reset() {
        this.state.set(RoomState.RESETING);

        this.onReset();
    }

    public void destroy() {
        this.state.set(RoomState.DISABLED);

        this.onDestroy();
    }

    protected abstract void onCreate();

    protected abstract void onStart();

    protected abstract void onReset();

    protected abstract void onDestroy();

    @Override
    public void addReservation(PlayerProfile profile) {
        this.reservations.put(profile, null);
    }

    @Override
    public boolean isReserved(PlayerProfile profile) {
        return this.reservations.containsKey(profile);
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);

        // Call event.
        OnPlayerJoinEvent.call(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);

        // Call event.
        OnPlayerLeaveEvent.call(player);
    }

    public void broadcast(String message) {
        for (Player player : this.players) {
            player.sendMessage(message);
        }
    }

    @Override
    public Set<PlayerProfile> getPlayerProfiles() {
        Set<PlayerProfile> profiles = new HashSet<>();
        for (Player item : this.players) {
            PlayerProfileImpl current = new PlayerProfileImpl(item.getUniqueId());
            current.setName(item.getName());
            profiles.add(current);
        }
        return profiles;
    }

    public Set<Player> getPlayers() {
        return new HashSet<>(players);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public RoomState getState() {
        return state.get();
    }

}
