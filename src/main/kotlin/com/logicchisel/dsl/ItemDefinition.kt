package com.logicchisel.dsl

import kotlinx.serialization.Serializable

// YAMLファイル全体に対応するデータクラス
@Serializable
data class ItemDefinition(
    val type: String,
    val id: String,
    val displayName: String,
    val texture: String,
    val properties: ItemProperties
)

// propertiesセクションに対応するデータクラス
@Serializable
data class ItemProperties(
    val damage: Double,
    val attackSpeed: Double,
    val durability: Int,
    val rarity: String
)
