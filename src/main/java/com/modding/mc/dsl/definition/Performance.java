package com.modding.mc.dsl.definition;

import lombok.Data;

/**
 * 武器やツールとしての性能を格納するクラス。
 * (性能 / performance)
 */
@Data
public class Performance {
    /**
     * 攻撃力。
     * (攻撃力 / attackDamage)
     */
    private Double attackDamage;

    /**
     * 攻撃速度。
     * (攻撃速度 / attackSpeed)
     */
    private Double attackSpeed;

    /**
     * 採掘可能なブロックの硬さ。例: "IRON", "DIAMOND"
     * (採掘レベル / miningLevel)
     */
    private String miningLevel;
}
