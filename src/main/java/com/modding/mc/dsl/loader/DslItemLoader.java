package com.modding.mc.dsl.loader;

import com.modding.mc.LogicChisel;
import com.modding.mc.dsl.definition.ItemDefinition;
import com.modding.mc.dsl.parser.DslParser;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * DSLで定義されたファイルを読み込み、アイテムとしてゲームに登録する責務を持つクラス。
 */
public class DslItemLoader {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(LogicChisel.MODID + " DSL Loader");
    private static final String DEFINITIONS_ROOT_PATH = "definitions/items";

    /**
     * Modのコンストラクタから呼び出され、アイテムの読み込みと登録準備を行う。
     * @param eventBus Modのイベントバス
     */
    public static void register(IEventBus eventBus) {
        LOGGER.info("DSLアイテムの登録処理を開始します...");
        DslParser parser = new DslParser();

        try {
            URI uri = DslItemLoader.class.getClassLoader().getResource(DEFINITIONS_ROOT_PATH).toURI();
            Path rootPath;
            if ("jar".equals(uri.getScheme())) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                rootPath = fileSystem.getPath(DEFINITIONS_ROOT_PATH);
            } else {
                rootPath = Paths.get(uri);
            }

            try (Stream<Path> paths = Files.walk(rootPath)) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".yml") || path.toString().endsWith(".yaml"))
                    .forEach(filePath -> {
                        LOGGER.info("定義ファイルを発見: {}", filePath);
                        try (InputStream in = Files.newInputStream(filePath)) {
                            // 1. ファイルをパースしてItemDefinitionオブジェクトを取得
                            ItemDefinition itemDef = parser.parse(in);
                            LOGGER.info("パース成功: {}", itemDef.getId());

                            // 2. Item.Propertiesを生成し、ItemDefinitionの値を設定
                            Item.Properties properties = new Item.Properties();
                            if (itemDef.getStackSize() != null) {
                                properties.stacksTo(itemDef.getStackSize());
                            }
                            if (itemDef.getDurability() != null) {
                                properties.durability(itemDef.getDurability());
                            }
                            // TODO: 将来的にはここでFOODやSWORDなどの種類に応じた処理を追加

                            // 3. DeferredRegisterにアイテムを登録
                            LogicChisel.ITEMS.register(itemDef.getId(), () -> new Item(properties));
                            LOGGER.info("アイテム '{}' の登録を予約しました。", itemDef.getId());

                        } catch (Exception e) {
                            LOGGER.error("ファイルの処理に失敗しました: " + filePath, e);
                        }
                    });
            }
        } catch (Exception e) {
            LOGGER.error("定義フォルダの読み込みに失敗しました: " + DEFINITIONS_ROOT_PATH, e);
        }
        
        // 4. 最終的に、全てのアイテム登録予約をイベントバスに結びつける
        LogicChisel.ITEMS.register(eventBus);
        LOGGER.info("DSLアイテムの登録処理が完了しました。");
    }
}
