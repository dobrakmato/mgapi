package fw.config;

import fw.Region;
import fw.Time;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface Config {

    int getInt(String key);

    int getInt(String key, int defaultValue);

    long getLong(String key);

    long getLong(String key, long defaultValue);

    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    double getDouble(String key);

    double getDouble(String key, double defaultValue);

    String getString(String key);

    String getString(String key, String defaultValue);

    Location getLocation(String key);

    Location getLocation(String key, Location defaultValue);

    ItemStack getItemStack(String key);

    ItemStack getItemStack(String key, ItemStack defaultValue);

    Time getTime(String key);

    Time getTime(String key, Time defaultValue);

    Region getRegion(String key);

    Region getRegion(String key, Region defaultValue);
}
