package io.github.saimonovski.smvchestpvpplugin.core.utils;

import io.github.saimonovski.smvchestpvpplugin.core.CoolDownManager;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {
    public enum MessageType {}
  @Setter
  private  Player p;
    @Setter
    private String message;
        @Setter
        CoolDownManager coolDown;

    public static String fixColor(String str){
        return     ChatColor.translateAlternateColorCodes('&',str);

    }
    public  String fixValues(){
       message = message.replace("{PLAYER_NAME}",p.getName());
       if(coolDown != null) {
           message = message.replace("{PLAYER-REMAINING-COOLDOWN}", coolDown.getRemainingString(p));
       }
       return message;
    }

    public ChatUtils (String message, Player p){
        this.p = p;
        this.message = message;
    }
}
