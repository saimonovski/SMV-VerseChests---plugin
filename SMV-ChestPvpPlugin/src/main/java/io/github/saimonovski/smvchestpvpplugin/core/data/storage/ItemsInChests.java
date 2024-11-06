package io.github.saimonovski.smvchestpvpplugin.core.data.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.saimonovski.smvchestpvpplugin.core.utils.SerialzizeUtils;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class ItemsInChests  {
    YamlDocument document;

    List<ItemStack> rare = new ArrayList<>();

    List<ItemStack> epic = new ArrayList<>();

    List<ItemStack> mythic = new ArrayList<>();

    List<ItemStack> legendary = new ArrayList<>();

    @SuppressWarnings("unchecked") //because in the conmfig I store itemstack map presented as String, object
    public ItemsInChests(YamlDocument data){


        this.document = data;

            //rare
        data.getMapList("itemsInChests.rare")
        .forEach(m -> rare.add(ItemStack.deserialize((Map<String, Object>) m)));
        //epic
        data.getMapList("itemsInChests.epic")
                .forEach(m -> epic.add(ItemStack.deserialize((Map<String, Object>) m)));
        //mythic
        data.getMapList("itemsInChests.mythic")
                .forEach(m -> mythic.add(ItemStack.deserialize((Map<String, Object>) m)));
        //legendary
        data.getMapList("itemsInChests.legendary")
                .forEach(m -> legendary.add(ItemStack.deserialize((Map<String, Object>) m)));
    }
    public void save(){
        document.set("itemsInChests.rare", SerialzizeUtils.listSerialize(rare));
        document.set("itemsInChests.epic", SerialzizeUtils.listSerialize(epic));
        document.set("itemsInChests.mythic", SerialzizeUtils.listSerialize(mythic));
        document.set("itemsInChests.legendary", SerialzizeUtils.listSerialize(legendary));
    }



}
