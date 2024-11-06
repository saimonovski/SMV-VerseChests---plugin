package io.github.saimonovski.smvchestpvpplugin.core.configs.rarity;

import lombok.Setter;

@Setter
public class Mythic  implements RarityConfigurer{

    private int chance;

    private int maxItems;

    private int minItems;

    @Override
    public int getChance() {
        return chance;
    }

    @Override
    public int getMaxItems() {
        return maxItems;
    }

    @Override
    public int getMinItems() {
        return minItems;
    }
}
