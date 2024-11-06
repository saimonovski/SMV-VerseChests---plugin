package io.github.saimonovski.smvchestpvpplugin.core.configs;

import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;

import java.io.IOException;

@Getter
public class MessageConfig  {
    final YamlDocument config;

    private String getChestCreatorMessage = "&2Nadano kreator";

    private String noPermissionMessage = "&cBrak uprawnień!";

    private String changesSavedMessage = "&aZmiany zostały zapisane.";

    private String coolDownMessage = "&ePoczekaj &f{PLAYER-REMAINING-COOLDOWN}, zanim otworzysz ta skrznie ponownie.";

    private String chestCreatedMessage = "&2Skrzynia została utworzona.";


    private String chestLootedMessage = "&6Skrzynia została zlootowana!";

    private String chestRemovedMessage = "&6Skrzynia została usunieta!";

    private void load(){
        getChestCreatorMessage = config.getString("chestCreatorMessage");
        noPermissionMessage = config.getString("noPermissionMessage");
        changesSavedMessage = config.getString("changesSavedMessage");
        coolDownMessage = config.getString("coolDownMessage");
        chestCreatedMessage = config.getString("chestCreatedMessage");
        chestLootedMessage = config.getString("chestLootedMessage");
        chestRemovedMessage = config.getString("chestRemovedMessage");
    }
    public MessageConfig(YamlDocument yamlDocument){
        config = yamlDocument;
        load();
    }
    public void save(){
        try {
            config.reload();
            load();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }




}
