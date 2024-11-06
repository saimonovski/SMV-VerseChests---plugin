package io.github.saimonovski.smvchestpvpplugin;

import io.github.saimonovski.smvchestpvpplugin.core.DataManage;
import io.github.saimonovski.smvchestpvpplugin.core.cmd.ChestPvpCommands;
import io.github.saimonovski.smvchestpvpplugin.core.utils.ListenerRegisterer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public final class SMV_VerseChests extends JavaPlugin {
   @Getter
   private static SMV_VerseChests instance;
  public static final Set<BukkitTask> tasks = new HashSet<>();
    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Loading configs...");
        DataManage.Instance.reloadAll();
        getLogger().info("Loading events...");
        ListenerRegisterer.initalize();
        getLogger().info("Loading commands...");
        this.getCommand("chestpvp").setTabCompleter(new ChestPvpCommands());
        this.getCommand("chestpvp").setExecutor(new ChestPvpCommands());
    getLogger().info("plugin enabled :>");
    }


    @Override
    public void onDisable() {
        ListenerRegisterer.getListenerStream().close();
        getLogger().info("disabling cooldown save tasks");
        tasks.forEach(BukkitTask::cancel);

        getLogger().info("saving configs...");
        DataManage.Instance.saveAll();
        getLogger().info("plugin disabled :c");

    }
}
