package fw;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Summoner {
    private final EntityType type;
    private final Location location;

    public Summoner(EntityType type, Location summonerLocation) {
        this.type = type;
        this.location = summonerLocation;
    }

    public void activate() {

    }

    public void deactivate() {

    }
}
