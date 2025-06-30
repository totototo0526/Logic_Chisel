package com.modding.mc.dsl.definition;

import java.util.List;
import lombok.Data;

/**
 * 食料としての効果を格納するクラス。
 * (食事効果 / food)
 */
@Data
public class Food {
    /**
     * 満腹度の回復量。
     * (回復量 / hunger)
     */
    private Integer hunger;

    /**
     * 隠し満腹度（腹持ちの良さ）。
     * (満腹度 / saturation)
     */
    private Float saturation;

    /**
     * 食事時に付与されるポーション効果のリスト。
     * (特殊効果 / potionEffects)
     */
    private List<PotionEffectDefinition> potionEffects;
}
