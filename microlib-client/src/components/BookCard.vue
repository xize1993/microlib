<template>
  <v-card class="mx-auto" max-width="360" height="240" outlined>
    <v-tooltip bottom :disabled="!book.title || (book.title && book.title.length < 40)">
      <template v-slot:activator="{ on, attrs }">
        <v-card-title 
          class="subtitle-1" 
          v-bind="attrs"
          v-on="on"
        >{{book.title | formatTitle}}</v-card-title>
      </template>
      <span>{{book.title | formatTitle}}</span>
    </v-tooltip>
    <v-list-item>
      <v-list-item-avatar tile size="80" height="100" color="grey">
        <v-img v-if="book.haveCover" :src="getImgPath(book.id)" aspect-ratio="1.7"></v-img>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle>
          <div>著者: {{book.author.authorName}}</div>
          <div>種別: {{book.subject}}</div>
          <div>出版日: {{book.publicationDate}}</div>
          <div>価格: {{book.price | formatPrice}}</div>
          <div>ISBN: {{book.isbn}}</div>
          <div>ページ数: {{book.pageCount | formatPageCount}}</div>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions class="c-v-card-actions">
      <div v-if="isPublication()">
          <strong class="isPublication">(出版済)</strong>
      </div>
      <v-spacer></v-spacer>
      <v-btn text color="error" @click="deleteSelf()" :disabled="isPublication()">
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
    // 出版済み判断
    isPublication() {
      return this.book.publicationDate && new Date(`${this.book.publicationDate} 00:00:00`) <= new Date(new Date().toLocaleDateString())
    },
    // 詳細ボタンを押下する
    openBookDetails() {
      this.$emit('openBookDetails', this.book);
    },
    // 削除ボタンを押下する
    deleteSelf() {
      this.$emit('deleteBook', this.book);
    },
    // 画像URLを取得
    getImgPath(id) {
      return this.$request.adornUrl(`/microlib/book/cover/${id}?t=${new Date().getTime()}`)
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
.isPublication {
  color: #EF5350;
}
</style>