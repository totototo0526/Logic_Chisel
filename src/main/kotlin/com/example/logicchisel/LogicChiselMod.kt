package com.example.logicchisel

import net.neoforged.fml.common.Mod

@Mod(LogicChiselMod.MODID)
object LogicChiselMod {
    const val MODID = "logic_chisel"

    init {
        // 初期化処理（イベント登録など）
        println("Logic Chisel Mod loaded!")
    }
}
