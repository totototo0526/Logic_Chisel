package com.modding.mc.dsl.parser;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * YAMLのキー（日本語エイリアス等）をJavaクラスのプロパティ名に変換する。
 */
public class AliasPropertyUtils extends PropertyUtils {

    private final Map<String, String> aliasMap = new HashMap<>();

    public AliasPropertyUtils() {
        // ここに、日本語キーと英語のフィールド名の対応を定義する
        // 基本情報
        aliasMap.put("識別子", "id");
        aliasMap.put("表示名", "displayName");
        // 基本属性
        aliasMap.put("種類", "type");
        aliasMap.put("スタック数", "stackSize");
        aliasMap.put("耐久値", "durability");
        aliasMap.put("説明文", "lore");
        // 性能
        aliasMap.put("性能", "performance");
        aliasMap.put("攻撃力", "attackDamage");
        aliasMap.put("攻撃速度", "attackSpeed");
        aliasMap.put("採掘レベル", "miningLevel");
        // 食事効果
        aliasMap.put("食事効果", "food");
        aliasMap.put("回復量", "hunger");
        aliasMap.put("満腹度", "saturation");
        aliasMap.put("特殊効果", "potionEffects");
        // 独自ロジック
        aliasMap.put("動作", "events");
    }

    @Override
    public Property getProperty(Class<?> type, String name) {
        // YAMLのキー(name)がエイリアスマップに存在すれば、対応するJavaのフィールド名に置換して処理を続行する
        String canonicalName = aliasMap.getOrDefault(name, name);
        return super.getProperty(type, canonicalName);
    }
}
