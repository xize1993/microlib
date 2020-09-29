<template>
  <v-card class="mx-auto" max-width="360" height="230" outlined>
    <v-card-title class="subtitle-1" >{{author.authorName}}</v-card-title>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-subtitle>
          <div>カナ: {{author.authorNameKana}}</div>
          <div>出生日: {{author.birthday}}</div>
          <div>書籍数: {{author.books | formatBooksLen}}</div>
          <div class="description">{{author.description}}</div>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text color="error" @click="deleteSelf()">
        <v-icon small >mdi-delete</v-icon>
        <div><strong class="text--lighten-1">削除</strong></div>
      </v-btn>
      <v-btn text @click="openAuthorDetails()">
        <v-icon small>mdi-pencil</v-icon>詳細
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  export default {
    name: 'AuthorCard',
    props: {
      author: Object
    },
    methods: {
       // 詳細ボタンを押下する
      openAuthorDetails() {
        this.$emit('openAuthorDetails', this.author);
      },
      // 削除ボタンを押下する
      deleteSelf() {
        this.$emit('deleteAuthor', this.author);
      },
    }
  }
</script>

<style lang="scss" scoped>
.subtitle-1 {
  height: 75px;
  padding: 14px;
  text-overflow: ellipsis;
  overflow: hidden;
}
.v-list-item {
  min-height: 100px;
}
</style>