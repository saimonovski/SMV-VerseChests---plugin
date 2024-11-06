package io.github.saimonovski.smvchestpvpplugin.core.data.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.saimonovski.smvchestpvpplugin.core.CoolDownManager;
import lombok.Data;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


@Data
public class DropChest  {
    YamlDocument config;
    private Location chestLocation;
    private   CoolDownManager manager;


    @SuppressWarnings("unchecked") // in the config is defined strictly types and managed by the plugin, other type is impossible
    private void loadChest(@NotNull Map<String, Object> map){
        chestLocation = Location.deserialize((Map<String, Object>) map.get("location"));
        manager = CoolDownManager.deserialize((Map<String, Object>) map.get("playersOnCoolDown"));
    }
    public DropChest(Map<String, Object> map){
        loadChest(map);
    }
    public DropChest(Location loc){
        Map<String, Object> map = new HashMap<>();
        map.put("location", loc.serialize());
        map.put("playersOnCoolDown", new CoolDownManager().serialize());
        loadChest(map);
    }
    public Map<String, Object> serialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("location", chestLocation.serialize());
        map.put("playersOnCoolDown", manager.serialize());
        return map;
    }
    public static DropChest deserialize(Map<String, Object> map){
        return new DropChest(map);
    }


}
