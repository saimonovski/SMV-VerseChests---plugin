package io.github.saimonovski.smvchestpvpplugin.core.configs.rarity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rare  implements RarityConfigurer{

    private int chance;

    private int maxItems;

    private int minItems;



}
