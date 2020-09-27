<template>
  <v-item-group multiple>
    <v-container>
      <v-row>
        <v-col class="lib-breadcrumb" md="8">
          <v-breadcrumbs :items="breadcrumbItems" divider=">"></v-breadcrumbs>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="10">
          <v-row align="center">
            <v-col cols="4">
              <v-text-field v-model="searchParams.title" label="タイトルで検索"></v-text-field>
            </v-col>
            <v-col cols="4">
              <!-- <v-text-field v-model="searchParams.title" label="著者で検索"></v-text-field> -->
              <v-autocomplete
                v-model="searchParams.authorId"
                label="著者"
                persistent-hint
                clearable
                :loading="authorsLoading"
                :search-input.sync="authorsList"
                :items="authors"
                item-text="authorName"
                item-value="id"
                prepend-icon="mdi-account-box"
              >
              </v-autocomplete>
            </v-col>
            <v-col cols="2" fill-height>
              <v-btn class="ma-2" outlined color="teal" @click="getBookList()">検索</v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row>
        <v-col v-for="book in listBook" :key="book.id" cols="12" md="4">
          <v-item>
            <BookCard 
              :book="book"
              @openBookDetails="openBookDetails"
              @deleteBook="openDeleteDialog"
            ></BookCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>

    <div class="text-center">
      <v-pagination
        v-model="searchParams.pageIndex"
        :length="totalPages"
        :total-visible="10"
        @input="getBookList()"
      ></v-pagination>
    </div>

    <!-- 削除ダイアログ -->
    <v-dialog v-model="deleteDialog" max-width="290">
      <v-card>
        <v-card-title class="headline">書籍を削除しますか？</v-card-title>
        <v-card-text>
          タイトル：未定
          <br />著者：著者太郎
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="deleteDialog = false">キャンセル</v-btn>
          <v-btn color="red" text @click="deleteBook()">削除</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-item-group>
</template>

<script>
import BookCard from '../components/BookCard';

export default {
  name: 'BookView',
  data: () => ({
    breadcrumbItems: [
      {
        text: 'Microlib 書籍管理システム',
        disabled: true
      },
      {
        text: '書籍管理',
        disabled: true
      }
    ],
    searchParams: {
      pageIndex: 1,
      pageSize: 9,
      title: '',
      authorId: undefined
    },
    authors: [],
    authorsLoading: false,
    authorsList: null,
    listBook: [],
    totalPages: 0,
    deleteDialog: false,
    deleteBookObj: {}
  }),
  components: {
    BookCard
  },
  activated() {
    // 画面データを初期化する
    this.getBookList()
    // 著者リスト
    this.authorsLoading = true
    this.axios.get(`http://localhost:8081/microlib/author`)
      .then(response => {
        this.authors = response.data.content ? response.data.content : [];
        this.authorsLoading = false
      })
      .catch(e => {
        console.log(e);
        this.authorsLoading = false
        this.$emit('showMessage', '著者データ取得に失敗しました！', 'error');
      })
  },
  methods: {
    // 書籍リストを取得
    getBookList() {
      this.axios
        .get(`http://localhost:8081/microlib/book`, {
          params: {
            t: this.searchParams.title,
            a: this.searchParams.authorId,
            i: this.searchParams.pageIndex - 1,
            s: this.searchParams.pageSize
          }
        })
        .then(response => {
          this.listBook = response.data.content;
          this.totalPages = response.data.totalPages;
        })
        .catch(e => {
          this.$emit('showMessage', e, 'error');
        });
    },
    // 書籍を詳細ダイアログを開く
    openBookDetails(book) {
      if (book && book.id) {
        this.$emit('openDialog', book);
      } else {
        this.$emit('showMessage', 'パラーメータ不正', 'error');
      }
    },
    // 削除ダイアログを開く
    openDeleteDialog(book) {
      if (book && book.id) {
        this.deleteBookObj = book
        this.deleteDialog = true
      } else {
        this.$emit('showMessage', 'パラーメータ不正', 'error');
      }
    },
    // 書籍を削除
    deleteBook() {
      this.axios
        .delete(`http://localhost:8081/microlib/book/${this.deleteBookObj.id}`)
        .then(() => {
          this.deleteDialog = false
          this.$emit('showMessage', '書籍を削除しました。', 'success');
          this.getBookList();
        })
        .catch(e => {
          this.$emit('showMessage', e.response.data, 'error');
        })
    }
  }
};
</script>