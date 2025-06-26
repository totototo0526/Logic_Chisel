package com.logicchisel.dsl

import com.charleskorn.kaml.Yaml
import java.io.File
import net.neoforged.fml.loading.FMLPaths

// DSLファイルをロードするための専門オブジェクト
object DslLoader {

    // アイテム定義ファイルをロードするメインの関数
    fun loadItems(): List<ItemDefinition> {
        // 1. DSLファイルの置き場所を取得します (例: /<マインクラフトのフォルダ>/config/logic_chisel/items)
        val dslDirectory = FMLPaths.CONFIGDIR.get().resolve("logic_chisel/items").toFile()

        // 2. もしディレクトリが存在しなければ、自動で作成します
        if (!dslDirectory.exists()) {
            println("[Logic Chisel] DSL directory not found. Creating at: ${dslDirectory.absolutePath}")
            dslDirectory.mkdirs()
            return emptyList() // 初回起動時はファイルがないため、空のリストを返して終了
        }

        // 3. ディレクトリ内の全ファイルを走査し、".yml"または".yaml"で終わるファイルのみを対象にします
        val itemDefinitions = dslDirectory.walk()
            .filter { it.isFile && (it.extension == "yml" || it.extension == "yaml") }
            .mapNotNull { file ->
                println("[Logic Chisel] Loading file: ${file.name}")
                try {
                    // 4. ファイルをテキストとして読み込み、ItemDefinitionオブジェクトに変換（デシリアライズ）します
                    val text = file.readText()
                    Yaml.default.decodeFromString(ItemDefinition.serializer(), text)
                } catch (e: Exception) {
                    // 5. もしファイルの書式が間違っていた場合、エラーを記録して次のファイルに進みます
                    println("[Logic Chisel] Error parsing ${file.name}: ${e.message}")
                    null // このファイルは不正なので結果から除外
                }
            }.toList()

        println("[Logic Chisel] Successfully loaded ${itemDefinitions.size} item definition(s).")
        return itemDefinitions
    }
}
