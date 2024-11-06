package io.github.saimonovski.smvchestpvpplugin.core.listeners;


import io.github.saimonovski.smvchestpvpplugin.core.configs.Config;
import io.github.saimonovski.smvchestpvpplugin.core.configs.rarity.RarityConfigurer;
import io.github.saimonovski.smvchestpvpplugin.core.data.storage.Data;
import io.github.saimonovski.smvchestpvpplugin.core.data.storage.DropChest;
import io.github.saimonovski.smvchestpvpplugin.core.data.storage.ItemsInChests;
import io.github.saimonovski.smvchestpvpplugin.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static io.github.saimonovski.smvchestpvpplugin.core.DataManage.Instance;

public class ListenerInitalizer implements Listener {

    @EventHandler
    public void onChestCreator(BlockPlaceEvent e) {
        if (!e.canBuild()) return;
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (!b.getType().equals(Instance.getCreatorBlock().getType())) return;

        Data data = Instance.getData();
        List<DropChest> chestList = data.getChestList();
        DropChest chest = new DropChest(b.getLocation());
        chestList.add(chest);
        data.setChestList(chestList);
        b.setType(Instance.getChestMaterial());
        
        p.sendMessage(ChatUtils.fixColor(Instance.getMessages().getChestCreatedMessage()));
    }

    @EventHandler
    public void onChestLooted(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        Location location = block.getLocation();

        DropChest chest = findDropChestByLocation(location);
        if (chest == null) return;
        e.setCancelled(true);

        Config config = Instance.getConfig();
        Player p = e.getPlayer();
        if (!chest.getManager().checkCooldown(p)) {
            ChatUtils utils = new ChatUtils(Instance.getMessages().getCoolDownMessage(), p);
            utils.setCoolDown(chest.getManager());

            p.sendMessage(ChatUtils.fixColor(utils.fixValues()));
            return;
        }

        chest.getManager().giveCooldown(config.getChestCooldown(), p);
//        Instance.getData().save();*

        List<ItemStack> itemList = new ArrayList<>();
        ItemsInChests itemsInChests = Instance.getData().getItems();
        itemList.addAll(addItems(Instance.getLegendary(), itemsInChests.getLegendary()));
        itemList.addAll(addItems(Instance.getMythic(), itemsInChests.getMythic()));
        itemList.addAll(addItems(Instance.getEpic(), itemsInChests.getEpic()));
        itemList.addAll(addItems(Instance.getRare(), itemsInChests.getRare()));

        Inventory inv = Bukkit.createInventory(null, 27, ChatUtils.fixColor(config.getChestTitle()));
        itemList.removeIf(item -> item == null || item.getType().equals(Material.AIR));

        if (itemList.size() > 27) {
            for (int x = 0; x < 27; x++) {
                inv.addItem(itemList.get(new Random().nextInt(itemList.size()) - 1));
            }
        } else {
            Set<Integer> usedSlots = new HashSet<>();
            Random random = new Random();

            for (ItemStack item : itemList) {
                int randomSlot;
                do {
                    randomSlot = random.nextInt(27);
                } while (usedSlots.contains(randomSlot));

                usedSlots.add(randomSlot);
                inv.setItem(randomSlot, item);
            }
        }

        p.openInventory(inv);

        p.sendMessage(ChatUtils.fixColor(Instance.getMessages().getChestLootedMessage()));
    }

    private <T extends RarityConfigurer> List<ItemStack> addItems(T rarity, List<ItemStack> itemsToSelect) {
        Random random = new Random();

        int itemsNumber;
        if (rarity.getMaxItems() < rarity.getMinItems()) {
            itemsNumber = random.nextInt(itemsToSelect.size());
        } else {
            itemsNumber = random.nextInt(rarity.getMaxItems());
            if (itemsNumber < rarity.getMinItems()) {
                itemsNumber = rarity.getMinItems();
            }
        }

        List<ItemStack> itemsToDrop = new ArrayList<>();
        if(!itemsToSelect.isEmpty()) {
            if (rarity.getChance() <= random.nextInt(100)) {
                for (int x = 0; x < itemsNumber; x++) {
                    int itemIndex = random.nextInt(itemsToSelect.size());
                    ItemStack item = itemsToSelect.get(itemIndex);
                    itemsToDrop.add(item);
                }
            }

        }
        return itemsToDrop;
    }

    @EventHandler
    public void onChestRemove(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        Location loc = block.getLocation();

        DropChest chest = findDropChestByLocation(loc);
        if (chest == null) return;

        if (e.getPlayer().isSneaking() && e.getPlayer().isOp()) {
            Data data = Instance.getData();
            List<DropChest> chestList = data.getChestList();
            chestList.remove(chest);
            data.setChestList(chestList);
            p.sendMessage(ChatUtils.fixColor(Instance.getMessages().getChestRemovedMessage()));
        } else {
            e.setCancelled(true);
        }
    }

    private DropChest findDropChestByLocation(Location location) {
        List<DropChest> chestList = Instance.getData().getChestList();
        for (DropChest chest : chestList) {
            if (chest.getChestLocation().equals(location)) {
                return chest;
            }
        }
        return null;
    }
}