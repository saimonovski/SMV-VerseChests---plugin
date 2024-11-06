package io.github.saimonovski.smvchestpvpplugin.core.cmd;

import io.github.saimonovski.smvchestpvpplugin.SMV_VerseChests;
import io.github.saimonovski.smvchestpvpplugin.core.data.storage.Data;
import io.github.saimonovski.smvchestpvpplugin.core.data.storage.ItemsInChests;
import io.github.saimonovski.smvchestpvpplugin.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static io.github.saimonovski.smvchestpvpplugin.core.DataManage.Instance;

public class ItemCreateCMD implements Listener {
    private static final Inventory selectRarity = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE+"Select Rarity");
    private static final Inventory rare = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +"Rare Items");
    private static final Inventory epic = Bukkit.createInventory(null, 54, ChatColor.LIGHT_PURPLE +"Epic Items");
    private static final Inventory mythic = Bukkit.createInventory(null, 54, ChatColor.AQUA +"Mythic Items");
    private static final Inventory legendary = Bukkit.createInventory(null, 54, ChatColor.GOLD +"Legendary Items");

    private enum Rarity{RARE,EPIC,MYTHIC,LEGENDARY}


    static {
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtils.fixColor("&2&lRare Items"));
        item.setItemMeta(meta);

        selectRarity.setItem(1, item);

        item.setType(Material.REDSTONE);
        meta.setDisplayName(ChatUtils.fixColor("&d&lEpic Items"));
        item.setItemMeta(meta);
        selectRarity.setItem(3, item);

        item.setType(Material.EMERALD);
        meta.setDisplayName(ChatUtils.fixColor("&4&lMythic Items"));
        item.setItemMeta(meta);
        selectRarity.setItem(5, item);

        item.setType(Material.EMERALD);
        meta.setDisplayName(ChatUtils.fixColor("&6&lLegendary Items"));
        item.setItemMeta(meta);
        selectRarity.setItem(7, item);

    }
    public ItemCreateCMD(){
        //  1  3  5  7

    }
    private  void openGui(Player p, Rarity rarity){
           Data data = Instance.getData();
        ItemsInChests itemsInChests = data.getItems();
    switch (rarity){
        case RARE:
    p.openInventory(setup(rare,itemsInChests.getRare()));
    break;
        case EPIC:
            p.openInventory(setup(epic,itemsInChests.getEpic()));
            break;
        case MYTHIC:
            p.openInventory(setup(mythic,itemsInChests.getMythic()));
            break;
        case LEGENDARY:
            p.openInventory(setup(legendary,itemsInChests.getLegendary()));
            break;
    }

    }
    private Inventory setup(Inventory inv, List<ItemStack> list){
        if(list.size() > 54){
            for(int x = 0; x < 54; x++){
                inv.addItem(list.get(x));
            }
        }else{
            inv.setContents(list.toArray(new ItemStack[0]));
        }
        return inv;
    }
    private void setup(ItemStack[] contents, Rarity rarity, ItemsInChests configurer){
        List<ItemStack> itemStacks = new ArrayList<>();
        for (ItemStack content : contents) {
            if(content != null){
                itemStacks.add(content);
            }



        }
        switch(rarity){
            case RARE:
                configurer.setRare(itemStacks);
                break;
            case EPIC:
                configurer.setEpic(itemStacks);
                break;
            case MYTHIC:
                configurer.setMythic(itemStacks);
                break;
            case LEGENDARY:
                configurer.setLegendary(itemStacks);
                break;
        }
//        Instance.getData().save();
    }
    @EventHandler
    public void onSelectRaritySetup(InventoryClickEvent e){
        if(!e.getView().getTitle().equalsIgnoreCase(selectRarity.getTitle())) return;
        if(!(e.getWhoClicked() instanceof Player) ) return;
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        switch(slot){
            case 1:
                openGui(p, Rarity.RARE);
                break;
            case 3:
                openGui(p, Rarity.EPIC);
                break;
            case 5:
                openGui(p, Rarity.MYTHIC);
                break;
            case 7:
                openGui(p, Rarity.LEGENDARY);
                break;
        }
    }
    @EventHandler
    public void onSetUpItemsDrop(InventoryCloseEvent e){
        if(e.getInventory().getTitle().equals(rare.getTitle())){

           Bukkit.getScheduler().runTaskAsynchronously(SMV_VerseChests.getInstance(), () -> setup(e.getInventory().getContents(), Rarity.RARE, Instance.getData().getItems()));

        } else if (e.getInventory().getTitle().equals(epic.getTitle())) {
            Bukkit.getScheduler().runTaskAsynchronously(SMV_VerseChests.getInstance(), () -> setup(e.getInventory().getContents(), Rarity.EPIC, Instance.getData().getItems()));


        } else if (e.getInventory().getTitle().equals(mythic.getTitle())) {
            Bukkit.getScheduler().runTaskAsynchronously(SMV_VerseChests.getInstance(), () -> setup(e.getInventory().getContents(), Rarity.MYTHIC, Instance.getData().getItems()));



        } else if (e.getInventory().getTitle().equals(legendary.getTitle())) {
            Bukkit.getScheduler().runTaskAsynchronously(SMV_VerseChests.getInstance(), () -> setup(e.getInventory().getContents(), Rarity.LEGENDARY, Instance.getData().getItems()));
        }
    }

    public void startEditor(Player p){
        p.openInventory(selectRarity);
    }



}
