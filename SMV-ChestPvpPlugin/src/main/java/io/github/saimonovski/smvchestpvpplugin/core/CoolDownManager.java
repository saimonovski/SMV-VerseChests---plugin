package io.github.saimonovski.smvchestpvpplugin.core;


import io.github.saimonovski.smvchestpvpplugin.SMV_VerseChests;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CoolDownManager {
   @Getter
   final BukkitTask task;
    final SMV_VerseChests plugin = SMV_VerseChests.getInstance();
    @Getter
    public enum ConverterType{SECONDS(20, "s"), MINUTES(1200, "m"), HOURS(72000, "h"), DAYS(1728000, "d");
        private final int value;
        private final String suffix;
        ConverterType(int value, String suffix) {
            this.value = value;
            this.suffix = suffix;
        }


    }
    public static long timeConverter(long ticks, ConverterType type){
        return ticks/ type.getValue();
    }
    @Setter

 private  Map<UUID, Long> playersOnCooldown = new HashMap<>();
    public CoolDownManager(){
     task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Set<UUID> idToRemove = new HashSet<>();
            playersOnCooldown.entrySet().forEach(time ->{
                time.setValue(time.getValue()-20);
                if (time.getValue() <= 0){
                    idToRemove.add(time.getKey());
                }
            });
            idToRemove.forEach(playersOnCooldown::remove);

        }, 0, 20);
        SMV_VerseChests.tasks.add(task);

    }


    public void giveCooldown(long seconds, Player p){
        playersOnCooldown.put(p.getUniqueId(), 20*seconds);
    }
    public boolean checkCooldown(Player p){
        if(playersOnCooldown.containsKey(p.getUniqueId())){
            return playersOnCooldown.get(p.getUniqueId()) <= 0;
        }
        return true;
    }
    public String getRemainingString(Player p){
        if (checkCooldown(p)) return "0"+ConverterType.SECONDS.getSuffix();
        long time = playersOnCooldown.get(p.getUniqueId());
        long seconds =  timeConverter(time, ConverterType.SECONDS);
        if(seconds < 60) return seconds+ConverterType.SECONDS.suffix;
        if(seconds > 60 && seconds < (60*60)) return timeConverter(time,ConverterType.MINUTES)+ConverterType.MINUTES.suffix;
        long hours = timeConverter(time, ConverterType.HOURS);
        if(hours < 24) return hours+ConverterType.HOURS.suffix;
        return timeConverter(time, ConverterType.HOURS) + ConverterType.HOURS.getSuffix();
    }
    @SuppressWarnings("unchecked") //strictly managed type by this code
    public static CoolDownManager deserialize(@NotNull Map<String, Object> map) {
        CoolDownManager manager = new CoolDownManager();
        Map<UUID, Long> cooldownMap = new HashMap<>();

        ((Map<UUID, Object>) map.get("playersOnCooldown")).forEach((uuid, value) -> cooldownMap.put(uuid, ((Number) value).longValue()));

        manager.setPlayersOnCooldown(cooldownMap);
        return manager;
    }

    public Map<String, Object> serialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("playersOnCooldown", playersOnCooldown);
        return map;
    }

}
