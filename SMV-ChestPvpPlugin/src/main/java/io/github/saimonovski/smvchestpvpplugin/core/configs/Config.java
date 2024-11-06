package io.github.saimonovski.smvchestpvpplugin.core.configs;


import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.saimonovski.smvchestpvpplugin.core.configs.rarity.Epic;
import io.github.saimonovski.smvchestpvpplugin.core.configs.rarity.Legendary;
import io.github.saimonovski.smvchestpvpplugin.core.configs.rarity.Mythic;
import io.github.saimonovski.smvchestpvpplugin.core.configs.rarity.Rare;
import io.github.saimonovski.smvchestpvpplugin.core.utils.ChatUtils;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.List;


@Data
public class Config {

    private YamlDocument config;

    private ItemStack item = null;


    private Material chest = Material.CHEST;


    private String chestTitle = "&6&lChestPVP";


    private int minSlotsInChest = 3;

    private int maxSlotsInChest = -1;

    private int chestCooldown = 3600;

    Rare rare;
    Epic epic;
    Mythic mythic;
    Legendary legendary;


    public Config(YamlDocument config){
        this.config = config;
        loadRarity();
        loadChestConfig();
        loadChestCreator();
    }

    public void save() {
        try {
            config.reload();
            loadRarity();
            loadChestConfig();
            loadChestCreator();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
private void loadRarity(){
    rare = new Rare();
    rare.setChance(config.getInt("rare.chanceToDrop"));
    rare.setMinItems(config.getInt("rare.minItems"));
    rare.setMaxItems(config.getInt("rare.maxItems"));

    mythic = new Mythic();
    mythic.setChance(config.getInt("mythic.chanceToDrop"));
    mythic.setMinItems(config.getInt("mythic.minItems"));
    mythic.setMaxItems(config.getInt("mythic.maxItems"));

    legendary = new Legendary();
    legendary.setChance(config.getInt("legendary.chanceToDrop"));
    legendary.setMinItems(config.getInt("legendary.minItems"));
    legendary.setMaxItems(config.getInt("legendary.maxItems"));

    epic = new Epic();
    epic.setChance(config.getInt("epic.chanceToDrop"));
    epic.setMinItems(config.getInt("epic.minItems"));
    epic.setMaxItems(config.getInt("epic.maxItems"));
}
private void loadChestConfig(){
    chestTitle = config.getString("chestConfiguration.title");
    chest = config.getEnum("chestConfiguration.material", Material.class);

    chestCooldown = config.getInt("chestCooldown");
    minSlotsInChest = config.getInt("minSlotsInChests");
    maxSlotsInChest = config.getInt("maxSlotsInChests");
}
private void loadChestCreator(){
    String itemName = config.getString("blockCreator.name");
    List<String> lore = config.getStringList("blockCreator.lore");
    Material material = config.getEnum("blockCreator.material", Material.class);
    item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatUtils.fixColor(itemName));
    lore.replaceAll(ChatUtils::fixColor);
    meta.setLore(lore);
}






}
