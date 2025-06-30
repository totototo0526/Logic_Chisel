# Logic_Chisel

## 概要 (Overview)

「Logic_Chisel」は、Minecraft Java版のMod制作を、小学生から熟練開発者まで、誰もが直感的に行えるようにすることを目指す、独自のDSL（ドメイン固有言語）兼フレームワークです。

複雑なJavaのコードを直接記述することなく、分かりやすいYAMLファイルを通じて、新しいアイテムやブロック、MOBなどを簡単に追加できます。

## 特徴 (Features)

* **YAMLベースの簡単な構文**: アイテムの性能や属性などを、可読性の高いYAML形式で記述します。
* **日英両対応のキー**: `表示名`のような日本語キーと、`displayName`のような英語キーの両方に対応。利用者が最も理解しやすい言語で定義できます。
* **高い拡張性**: 単純な定義だけでなく、アイテムの特殊な動作（イベント）を記述するための独自構文領域も用意されており、複雑な機能も実装可能です。

## 使い方：アイテムの追加方法

1.  **YAMLファイルの作成**
    プロジェクト内の`src/main/resources/definitions/items/`ディレクトリに、追加したいアイテムの`.yml`ファイルを作成します。

2.  **アイテムの定義**
    作成したYAMLファイルに、以下のようにアイテムの情報を記述します。日本語・英語のどちらのキーでも構いません。

    <details>
    <summary><strong>▶ 構文例：炎の剣</strong></summary>

    **日本語版 (`flame_sword_jp.yml`)**
    ```yaml
    アイテム:
      識別子: "flame_sword"
      表示名: "炎の剣"
      種類: "SWORD"
      耐久値: 350
      説明文:
        - "燃え盛る炎を宿した魔法の剣。"
        - "§c攻撃した敵を燃やす§r"
      性能:
        攻撃力: 6
        攻撃速度: -2.4
      動作: |
        onAttackEntity:
          when (chance(50%)) {
            target.setFire(duration: 4sec);
          }
    ```

    **英語版 (`flame_sword_en.yml`)**
    ```yaml
    item:
      id: "flame_sword"
      displayName: "Sword of Flame"
      type: "SWORD"
      durability: 350
      lore:
        - "A magic sword imbued with raging fire."
        - "§cIgnites entities on hit§r"
      performance:
        attackDamage: 6
        attackSpeed: -2.4
      events: |
        onAttackEntity:
          when (chance(50%)) {
            target.setFire(duration: 4sec);
          }
    ```
    </details>

## 主な定義キー (抜粋)

| 日本語キー | 英語キー | 説明 |
| :--- | :--- | :--- |
| `識別子` | `id` | Mod内部で使われる一意のID |
| `表示名` | `displayName`| ゲーム内での表示名 |
| `種類` | `type` | `SWORD`, `PICKAXE`, `FOOD` 等のアイテム種別 |
| `耐久値` | `durability` | ツールや武器の最大使用回数 |
| `説明文` | `lore` | アイテムに表示される説明文 |
| `性能` | `performance`| 攻撃力などをまとめるグループ |
| `動作` | `events` | 特殊なロジックを記述する領域 |
