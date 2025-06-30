package com.modding.mc.dsl.definition;

import lombok.Data;

/**
 * ポーション効果一つ分の定義を格納するクラス。
 */
@Data
public class PotionEffectDefinition {
    /**
     * 効果の種類。例: "regeneration", "speed"
     * (効果)
     */
    private String effect;

    /**
     * 効果の持続時間（tick単位）。20 ticks = 1秒。
     * (時間)
     */
    private Integer duration;

    /**
     * 効果のレベル。例: 0=I, 1=II
     * (レベル / 強さ)
     */
    private Integer amplifier = 0;
}
