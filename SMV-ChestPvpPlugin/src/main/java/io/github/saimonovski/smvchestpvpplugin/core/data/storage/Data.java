package io.github.saimonovski.smvchestpvpplugin.core.data.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.saimonovski.smvchestpvpplugin.core.DataManage;
import io.github.saimonovski.smvchestpvpplugin.core.utils.SerialzizeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@lombok.Data
public class Data {
YamlDocument document = DataManage.create("data.yml");
ItemsInChests items = new ItemsInChests(document);

    List<DropChest> chestList = new ArrayList<>();


    @SuppressWarnings("unchecked")
    private void loadChests(){
        document.getMapList("chestList").forEach(chest -> {
            DropChest chest1 = new DropChest((Map<String, Object>) chest);
            chestList.add(chest1);
        });
    }

public Data(){
        loadChests();
}
public void save()  {
        items.save();
        document.set("chestList", SerialzizeUtils.chestSerializer(chestList));
        try {
            document.save();
        }catch (IOException exception){
            exception.printStackTrace();
        }

}


}
