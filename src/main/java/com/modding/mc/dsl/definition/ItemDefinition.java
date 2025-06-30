package com.modding.mc.dsl.definition;

import java.util.List;
import lombok.Data;

/**
 * YAMLから読み込んだアイテム定義の全情報を格納するクラス。
 * (item / アイテム)
 */
@Data
public class ItemDefinition {

    /**
     * 内部的な一意のID。
     * (識別子 / id)
     */
    private String id;

    /**
     * ゲーム内で表示される名前。
     * (表示名 / displayName)
     */
    private String displayName;

    /**
     * アイテムの種類。例: "SWORD", "PICKAXE", "FOOD"
     * (種類 / type)
     */
    private String type = "ITEM";

    /**
     * 1スロットに格納できる最大数。
     * (スタック数 / stackSize)
     */
    private Integer stackSize = 64;

    /**
     * ツールや武器の最大使用回数。
     * (耐久値 / durability)
     */
    private Integer durability;

    /**
     * アイテムの説明文。
     * (説明文 / lore)
     */
    private List<String> lore;

    /**
     * 武器やツールとしての性能。
     * (性能 / performance)
     */
    private Performance performance;

    /**
     * 食料としての効果。
     * (食事効果 / food)
     */
    private Food food;

    /**
     * 独自構文を記述するイベントロジック。
     * (動作 / events)
     */
    private String events;
}
