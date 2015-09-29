package fw;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class Summoner {
    private final EntityType type;
    private final Location location;
    private final Time period;
    private int taskId;

    public Summoner(EntityType type, Location summonerLocation, Time period) {
        this.type = type;
        this.location = summonerLocation;
        this.period = period;
    }

    public void activate() {
        // TODO: Fix!!!
        Plugin plugin = null;
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::spawn,
                period.toLongTicks(), period.toLongTicks());
    }

    public void deactivate() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    private void spawn() {
        Entity entity = this.location.getWorld().spawnEntity(this.location, this.type);
        if (entity instanceof LivingEntity) {
            // Make them one hit to kill.
            ((LivingEntity) entity).setHealth(1D);
        }
    }
}
