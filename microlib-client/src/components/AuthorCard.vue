<template>
  <v-card class="mx-auto" max-width="360" height="230" outlined>
    <v-card-title class="subtitle-1">著者 太郎</v-card-title>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-subtitle>
          <div>カナ: チョシャ タロウ</div>
          <div>出生日: 1964/-/-</div>
          <div>書籍数: 5</div>
          <p>仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。仮の著者です。</p>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions>
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
</style>