<template>
  <v-card class="mx-auto" max-width="360" height="240" outlined>
    <v-card-title class="subtitle-1">{{book.title | formatTitle}}</v-card-title>
    <v-list-item>
      <v-list-item-avatar tile size="80" height="100" color="grey">
        <v-img v-if="book.haveCover" :src="getImgPath(book.id)" aspect-ratio="1.7"></v-img>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle>
          <div>著者: {{book.authorId}}</div>
          <div>種別: {{book.subject}}</div>
          <div>出版日: {{book.publicationDate}}</div>
          <div>価格: {{book.price | formatPrice}}</div>
          <div>ISBN: {{book.isbn}}</div>
          <div>ページ数: {{book.pageCount}} ページ</div>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions class="c-v-card-actions">
      <v-spacer></v-spacer>
      <v-btn text color="error" @click="deleteSelf()">
        <v-icon small>mdi-delete</v-icon>
        <div>
          <strong class="text--lighten-1">削除</strong>
        </div>
      </v-btn>
      <v-btn text @click="openBookDetails()">
        <v-icon small>mdi-pencil</v-icon>詳細
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: 'BookCard',
  props: {
    book: Object
  },
  methods: {
    // 詳細ボタンを押下する
    openBookDetails() {
      this.$emit('openBookDetails', this.book);
    },
    // 削除ボタンを押下する
    deleteSelf() {
      // this.$emit('dialog');
      this.$emit('deleteBook', this.book);
    },
    // 画像URLを取得
    getImgPath(id) {
      return `http://localhost:8081/microlib/book/cover/${id}`
    }
  }
};
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