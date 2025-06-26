package com.logicchisel // パッケージ名は、あなたの実際のファイルに合わせてください

import com.logicchisel.dsl.DslLoader
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tiers
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegisterEvent

@Mod(LogicChisel.MODID)
class LogicChisel(bus: IEventBus) {
    companion object {
        const val MODID = "logic_chisel"

        // 1. アイテムを登録するための「登録予約リスト」を準備します
        private val ITEMS = DeferredRegister.create(Registries.ITEM, MODID)
    }

    init {
        // 2. このMODがゲームのイベントを受け取れるように、イベントバスに接続します
        ITEMS.register(bus)
    }

    // 3. アイテム登録イベントを監視し、イベント発生時にこの関数を実行するよう設定します
    @SubscribeEvent
    fun onRegisterItems(event: RegisterEvent) {
        // この関数は、アイテム登録のタイミングでNeoForgeによって自動的に呼び出されます
        if (event.registryKey == Registries.ITEM) {
            println("[Logic Chisel] Starting item registration from DSL files...")

            // 4. 我々が作ったDslLoaderを呼び出し、全アイテムの設計図を取得します
            val itemDefinitions = DslLoader.loadItems()

            // 5. 取得した設計図を一つずつ処理します
            itemDefinitions.forEach { itemDef ->
                // typeが"Sword"の場合のみ処理する
                if (itemDef.type == "Sword") {
                    // 6. 設計図に基づき、新しい剣を「登録予約リスト」に追加します
                    ITEMS.register(itemDef.id) {
                        SwordItem(
                            Tiers.DIAMOND, // 剣の素材。一旦ダイヤモンドで固定します
                            itemDef.properties.damage.toInt(),
                            itemDef.properties.attackSpeed.toFloat(),
                            Item.Properties().durability(itemDef.properties.durability)
                        )
                    }
                    println("[Logic Chisel] Scheduled registration for sword: ${itemDef.id}")
                }
                // 今後、ここに "Block" や "Food" などの分岐を追加していくことになります
            }
        }
    }
}
