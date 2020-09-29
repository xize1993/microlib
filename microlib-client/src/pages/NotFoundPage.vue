<template>
  <v-container class="text-center fill-height" style="height: calc(100vh - 58px);">
    <v-row align="center">
      <v-col>
        <h1 class="display-2 primary--text">残念...</h1>
        <p>ここには何もない。</p>
        <br />
        <v-btn color="primary" outlined>
          <!-- メイン画面に戻る -->
          <router-link tag="p" :to="{ name: 'book'}">メイン画面に戻る</router-link>
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>