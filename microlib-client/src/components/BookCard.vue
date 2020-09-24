<template>
  <v-card class="mx-auto" max-width="360" height="240" outlined>
    <v-card-title class="subtitle-1">今日何食べよう・・・みんなは今日何食べた？！</v-card-title>
    <v-list-item>
      <v-list-item-avatar tile size="80" height="100" color="grey"></v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle>
          <div>著者: 著者 太郎</div>
          <div>種別: 生活情報</div>
          <div>出版日: 2020/3/17</div>
          <div>価格: ￥765</div>
          <div>ISBN: 1234567890123</div>
          <div>ページ数: 287ページ</div>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions class="c-v-card-actions">
      <v-spacer></v-spacer>
      <v-btn text color="error">
        <v-icon small >mdi-delete</v-icon>
        <div><strong class="text--lighten-1">削除</strong></div>
      </v-btn>
      <v-btn text>
        <v-icon small>mdi-pencil</v-icon>詳細
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  export default {
  }
</script>

<style lang="scss" scoped>
.subtitle-1 {
  height: 75px;
  padding: 14px;
  text-overflow: ellipsis;
  overflow: hidden;
}
.c-v-card-actions {
  padding-top: 0px;
}
</style>