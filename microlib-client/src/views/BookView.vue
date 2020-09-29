<template>
  <v-item-group multiple>
    <v-container>
      <v-row>
        <v-col cols="12" md="4" class="lib-breadcrumb" align-self="center">
          <v-breadcrumbs :items="breadcrumbItems" divider=">"></v-breadcrumbs>
        </v-col>
        <v-col cols="12" md="6" align-self="center" class="microlib-search-area">
          <v-row align="start">
            <v-col cols="6">
              <v-text-field v-model="searchParams.title" label="タイトルで検索"></v-text-field>
            </v-col>
            <v-col cols="6">
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
              ></v-autocomplete>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="12" md="2" align-self="center">
          <v-btn class="ma-2" outlined color="teal" @click="getList()">検索</v-btn>
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

      <v-alert v-show="!listBook" text color="blue-grey">
        <div>書籍が検索できません。</div>
      </v-alert>
    </v-container>

    <div class="text-center">
      <v-pagination
        v-show="listBook"
        v-model="searchParams.pageIndex"
        :length="totalPages"
        :total-visible="10"
        @input="getList()"
      ></v-pagination>
    </div>

    <!-- 削除ダイアログ -->
    <v-dialog v-model="deleteDialogControl" max-width="350">
      <v-card>
        <v-card-title class="headline">この書籍を削除しますか？</v-card-title>
        <v-card-text>
          タイトル：{{deleteBookObj.title | formatTitle}}
          <br />
          著者：{{deleteBookObj.author.authorName}}
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="deleteDialogControl = false">キャンセル</v-btn>
          <v-btn color="red" text @click="deleteBook()">削除</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-item-group>
</template>

<script>
import BookCard from "../components/BookCard";

export default {
  name: "BookView",
  data: () => ({
    breadcrumbItems: [
      {
        text: "Microlib 書籍管理システム",
        disabled: true
      },
      {
        text: "書籍管理",
        disabled: true
      }
    ],
    searchParams: {
      pageIndex: 1,
      pageSize: 9,
      title: "",
      authorId: undefined
    },
    authors: [],
    authorsLoading: false,
    authorsList: null,
    listBook: [],
    totalPages: 0,
    deleteDialogControl: false,
    deleteBookObj: {
      author: {}
    }
  }),
  components: {
    BookCard
  },
  activated() {
    // 画面データを初期化する
    this.getList();
    // 著者リスト
    this.authorsLoading = true;
    this.$request({
      url: "/microlib/author",
      method: "get"
    })
      .then(response => {
        this.authors = response.data.content ? response.data.content : [];
        this.authorsLoading = false;
      })
      .catch(e => {
        console.log(e);
        this.authorsLoading = false;
        this.$emit("putMessage", "著者データ取得に失敗しました！", "error");
      });
  },
  methods: {
    // 書籍リストを取得
    getList() {
      this.$request({
        url: "/microlib/book",
        method: "get",
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
          this.$emit("putMessage", e, "error");
        });
    },
    // 書籍を詳細ダイアログを開く
    openBookDetails(book) {
      if (book && book.id) {
        this.$emit("openDialog", book);
      } else {
        this.$emit("putMessage", "パラーメータ不正", "error");
      }
    },
    // 削除ダイアログを開く
    openDeleteDialog(book) {
      if (book && book.id) {
        this.deleteBookObj = book;
        this.deleteDialogControl = true;
      } else {
        this.$emit("putMessage", "パラーメータ不正", "error");
      }
    },
    // 書籍を削除
    deleteBook() {
      this.$request({
        url: `/microlib/book/${this.deleteBookObj.id}`,
        method: "delete"
      })
        .then(() => {
          this.deleteDialogControl = false;
          this.$emit("putMessage", "書籍を削除しました。", "success");
          this.getList();
        })
        .catch(e => {
          this.$emit("putMessage", e.response.data, "error");
        });
    }
  }
};
</script>
<style lang="scss" scoped>
.microlib-search-area {
  padding-top: 0px;
  padding-bottom: 0px;
}
</style>