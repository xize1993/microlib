# 紹介
kotlin、micronaut、vueに基づいているの練習プロジェクト。  
簡単なCRUD、画像アップロード、バリュエーション機能などを実装する。

## バックエンド
- kotlin
- micronaut

## フロンドエンド
- vue
- vuetify

# 実行
- ダウンロード（git pullあるいはZIPファイルをダウンロード）
```bash
git pull git@github.com:xize1993/microlib.git

```

- プロジェクトフォルダーに移動
```
cd microlib/
```

- Docker Composeを実行
```bash
```
- ブラウザで確認  
全部のサービスが立ち上がったら、以下URLで確認する。
```
http://localhost:8080/#/microlib/book
```

- 問題点
    - `docker-compose up`で初回起動後DBに接続できないこともあります、一度`docker-compose `シャットダウンして、再起動すればい問題ないですか、原因はまだ特定中...


