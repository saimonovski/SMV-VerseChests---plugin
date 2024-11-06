package io.github.saimonovski.smvchestpvpplugin.core.utils;

import io.github.saimonovski.smvchestpvpplugin.SMV_VerseChests;
import io.github.saimonovski.smvchestpvpplugin.core.cmd.ItemCreateCMD;
import io.github.saimonovski.smvchestpvpplugin.core.listeners.ListenerInitalizer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.stream.Stream;

public class ListenerRegisterer {
  @Getter
  private static final Stream<Listener> listenerStream = Stream.of(
            new ItemCreateCMD(),
            new ListenerInitalizer()
    );

    public static void initalize(){
        listenerStream.forEach(value -> Bukkit.getPluginManager().registerEvents(value, SMV_VerseChests.getInstance()));
    }
}
