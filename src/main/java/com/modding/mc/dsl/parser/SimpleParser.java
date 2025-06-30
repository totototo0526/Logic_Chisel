package com.modding.mc.dsl.parser;

import com.modding.mc.dsl.definition.ItemDefinition;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

public class SimpleParser {

    public static void main(String[] args) {
        // DumperOptionsとLoaderOptionsを明示的に生成します。
        DumperOptions dumperOptions = new DumperOptions();
        LoaderOptions loaderOptions = new LoaderOptions();

        // SnakeYAMLが、YAMLのネストされたマップを自動的に内部クラス（例: Performance）にマッピングできるように設定します。
        Representer representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        // 1. Yamlインスタンスを生成。ItemDefinitionクラスのコンストラクタと上記設定を適用。
        Constructor constructor = new Constructor(ItemDefinition.class, loaderOptions);
        Yaml yaml = new Yaml(constructor, representer);

        // 2. resourcesフォルダにあるYAMLファイルを読み込む
        String filePath = "definitions/items/simple_sword.yml";
        try (InputStream in = SimpleParser.class.getClassLoader().getResourceAsStream(filePath)) {
            if (in == null) {
                System.err.println("ファイルが見つかりません: " + filePath);
                return;
            }

            // 3. YAMLを読み込み、ItemDefinitionオブジェクトに変換
            ItemDefinition itemDef = yaml.load(in);

            // 4. 結果を出力
            // Lombokの@Dataアノテーションが自動生成したtoString()メソッドにより、中身が分かりやすく表示される。
            System.out.println("--- パース成功 ---");
            System.out.println(itemDef);
            System.out.println("--------------------");

        } catch (Exception e) {
            System.err.println("--- パース中にエラーが発生しました ---");
            e.printStackTrace();
            System.err.println("---------------------------------");
        }
    }
}
