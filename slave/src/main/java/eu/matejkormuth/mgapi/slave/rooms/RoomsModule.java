package eu.matejkormuth.mgapi.slave.rooms;

import eu.matejkormuth.mgapi.slave.Dependency;
import eu.matejkormuth.mgapi.slave.Module;
import eu.matejkormuth.mgapi.slave.api.GameRoom;
import eu.matejkormuth.mgapi.slave.api.MGAPI;
import eu.matejkormuth.mgapi.slave.modules.configuration.ConfigurationsModule;

import java.util.ArrayList;
import java.util.List;

public class RoomsModule extends Module {

    @Dependency
    private ConfigurationsModule configurationsModule;

    private List<GameRoom> gameRooms = new ArrayList<>();

    @Override
    public void onEnable() {
        MGAPI.roomsModule = this;
    }

    @Override
    public void onDisable() {

    }
}
