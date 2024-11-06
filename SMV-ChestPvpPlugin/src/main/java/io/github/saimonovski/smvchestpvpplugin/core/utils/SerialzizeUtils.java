package io.github.saimonovski.smvchestpvpplugin.core.utils;

import io.github.saimonovski.smvchestpvpplugin.core.data.storage.DropChest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerialzizeUtils {
    public static List<ItemStack> listDeSerialize(List<Map<String, Object>> map){
        List<ItemStack> stacks = new ArrayList<>();
        map.forEach(m -> stacks.add(ItemStack.deserialize(m)));
        return stacks;
    }
    public static List<Map<String, Object>> listSerialize(List<ItemStack> list){
        List<Map<String, Object>> maps = new ArrayList<>();
        list.forEach(i -> maps.add(i.serialize()));
        return maps;
    }
    public static List<Map<String, Object>> chestSerializer(List<DropChest> chestList){
        List<Map<String, Object>> maps = new ArrayList<>();

        chestList.forEach(dropChest -> maps.add(dropChest.serialize()));
        return maps;
    }
}
