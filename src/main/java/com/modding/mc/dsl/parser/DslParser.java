package com.modding.mc.dsl.parser;

import com.modding.mc.dsl.definition.ItemDefinition;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;
import java.util.Map;

/**
 * DSLで定義されたYAMLファイルを解析し、対応するDefinitionオブジェクトに変換するパーサー。
 */
public class DslParser {

    private final Yaml itemDefinitionParser;
    private final Yaml genericParser;

    public DslParser() {
        // データ変換用の、特殊設定がされたYamlインスタンスを準備
        DumperOptions dumperOptions = new DumperOptions();
        LoaderOptions loaderOptions = new LoaderOptions();
        Representer representer = new Representer(dumperOptions);
        representer.setPropertyUtils(new AliasPropertyUtils());
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Constructor constructor = new Constructor(ItemDefinition.class, loaderOptions);
        this.itemDefinitionParser = new Yaml(constructor, representer);

        // ファイルを単純に読み込むための、汎用的なYamlインスタンスを準備
        this.genericParser = new Yaml();
    }

    /**
     * 指定された入力ストリーム（YAMLファイル）を読み込み、ItemDefinitionオブジェクトに変換する。
     * 
     * @param inputStream 読み込むYAMLファイルの入力ストリーム
     * @return パースされたItemDefinitionオブジェクト
     */
    public ItemDefinition parse(InputStream inputStream) {
        Map<String, Object> loadedData = genericParser.load(inputStream);

        Object itemDataNode = loadedData.get("アイテム");
        if (itemDataNode == null) {
            itemDataNode = loadedData.get("item");
        }

        if (itemDataNode == null) {
            throw new IllegalArgumentException("ルートキー 'アイテム' または 'item' がファイル内に見つかりません。");
        }

        String itemDataYaml = itemDefinitionParser.dump(itemDataNode);
        return itemDefinitionParser.loadAs(itemDataYaml, ItemDefinition.class);
    }
}
