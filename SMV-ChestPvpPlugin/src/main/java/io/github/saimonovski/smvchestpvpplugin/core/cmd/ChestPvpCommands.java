package io.github.saimonovski.smvchestpvpplugin.core.cmd;

import io.github.saimonovski.smvchestpvpplugin.core.DataManage;
import io.github.saimonovski.smvchestpvpplugin.core.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ChestPvpCommands implements CommandExecutor, TabCompleter {
    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        //chestpvp editItems | getCreatorBlock | removeChest
        if(args.length == 0) return false;
        if(args[0].equalsIgnoreCase("editItems")){
            ItemCreateCMD createCMD = new ItemCreateCMD();
            createCMD.startEditor(p);
            p.sendMessage(ChatUtils.fixColor(DataManage.Instance.getMessages().getChangesSavedMessage()));
            return true;
        }
        if(args[0].equalsIgnoreCase("getCreatorBlock")){
            ItemStack item = DataManage.Instance.getCreatorBlock();
            p.getInventory().addItem(item);
            p.sendMessage(ChatUtils.fixColor(DataManage.Instance.getMessages().getGetChestCreatorMessage()));
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")){
            DataManage.Instance.saveAll();
            DataManage.Instance.reloadAll();
            p.sendMessage(ChatColor.GREEN+"Przeladowano config");
            return true;
        }
        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length ==1) {
            return Arrays.asList("editItems", "getCreatorBlock", "reload");
        }
        return null;
    }
}
