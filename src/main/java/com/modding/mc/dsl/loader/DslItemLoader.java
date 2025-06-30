package com.modding.mc.dsl.loader;

import com.modding.mc.LogicChisel;
import com.modding.mc.dsl.definition.ItemDefinition;
import com.modding.mc.dsl.parser.DslParser;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent; // ★このimport文を追加
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * DSLで定義されたファイルを読み込み、ゲームに登録する責務を持つクラス。
 */
public class DslItemLoader {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(LogicChisel.MODID + " DSL Loader");
    private static final String DEFINITIONS_ROOT_PATH = "definitions/items";

    /**
     * FMLCommonSetupEventのタイミングで呼び出され、アイテムの読み込みと登録を開始する。
     * @param event FMLCommonSetupEventオブジェクト（イベントリスナーとして必須）
     */
    // --- ★修正箇所：引数に (final FMLCommonSetupEvent event) を追加 ---
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("DSLアイテムの読み込みを開始します...");
        DslParser parser = new DslParser();

        try {
            // クラスローダーからリソースのURIを取得
            URI uri = DslItemLoader.class.getClassLoader().getResource(DEFINITIONS_ROOT_PATH).toURI();
            Path rootPath;

            // 実行環境（JAR内か、IDEか）に応じてパスの解決方法を切り替える
            if ("jar".equals(uri.getScheme())) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                rootPath = fileSystem.getPath(DEFINITIONS_ROOT_PATH);
            } else {
                rootPath = Paths.get(uri);
            }

            // 指定されたパス配下の全ファイルを探索
            try (Stream<Path> paths = Files.walk(rootPath)) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".yml") || path.toString().endsWith(".yaml"))
                    .forEach(filePath -> {
                        LOGGER.info("ファイルを発見: {}", filePath);
                        try (InputStream in = Files.newInputStream(filePath)) {
                            ItemDefinition itemDef = parser.parse(in);
                            LOGGER.info("パース成功: {}", itemDef);
                            // 将来的にここでアイテム登録処理を呼び出す
                        } catch (Exception e) {
                            LOGGER.error("ファイルのパースに失敗しました: " + filePath, e);
                        }
                    });
            }
        } catch (Exception e) {
            LOGGER.error("定義フォルダの読み込みに失敗しました: " + DEFINITIONS_ROOT_PATH, e);
        }
        LOGGER.info("DSLアイテムの読み込みが完了しました。");
    }
}
