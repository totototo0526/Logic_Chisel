# Logic Chisel

Minecraft NeoForge 1.21.6用MOD開発プロジェクト（Kotlin/Gradle対応）

## セットアップ手順

1. このリポジトリをクローンまたはダウンロード
2. 必要に応じてJDK 21以上をインストール
3. ターミナルで以下を実行し、依存関係を取得＆ビルド

   ```bash
   ./gradlew build
   ```

4. MOD開発・編集は`src/main/java`や`src/main/kotlin`配下で行ってください
5. 不要ファイルは`.gitignore`で除外済み

## 開発の流れ
- VSCodeやIntelliJ IDEA等のIDE推奨
- `./gradlew runClient`でテスト起動可能
- 主要な設定ファイル：
    - `build.gradle.kts`（ビルド設定）
    - `src/main/resources/META-INF/neoforge.mods.toml`（MOD情報）
    - `src/main/java`/`kotlin`（ソースコード）

## トラブルシューティング
- 依存関係の不整合やキャッシュ問題は `./gradlew --refresh-dependencies` で解決
- JavaバージョンやNeoForgeバージョンに注意

## 参考リンク
- [NeoForge公式ドキュメント](https://docs.neoforged.net/)
- [NeoForge Discord](https://discord.neoforged.net/)
- [Kotlin公式](https://kotlinlang.org/)

---

このプロジェクトは [totototo0526/Logic_Chisel](https://github.com/totototo0526/Logic_Chisel) で管理されています。
