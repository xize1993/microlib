<template>
  <v-item-group multiple>
    <v-container>
      <v-row>
        <v-col cols="12" md="4" class="lib-breadcrumb" align-self="center" >
          <v-breadcrumbs :items="breadcrumbItems" divider=">"></v-breadcrumbs>
        </v-col>
        <v-col cols="12" md="6"  align-self="center" class="microlib-search-area">
          <v-row align="center" justify="end">
            <v-col cols="12" md="6">
              <v-text-field
                v-model="searchParams.authorName"
                label="著者名で検索"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="12" md="2"  align-self="center" >
          <v-btn class="ma-2" outlined color="teal" @click="getList()">検索</v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col v-for="author in listAuthor" :key="author.id" cols="12" md="2">
          <v-item>
            <AuthorCard
              :author="author"
              @openAuthorDetails="openAuthorDetails"
              @deleteAuthor="openDeleteDialog"
            ></AuthorCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>

    <div class="text-center">
      <v-pagination
        v-model="searchParams.pageIndex"
        :length="totalPages"
        :total-visible="10"
        @input="getList()"
      ></v-pagination>
    </div>

    <!-- 削除ダイアログ -->
    <v-dialog v-model="deleteDialogControl" max-width="350">
      <v-card>
        <v-card-title class="headline">この著者を削除しますか？</v-card-title>
        <v-card-text>
          著者名：{{deleteAuthorObj.authorName}}
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="deleteDialogControl = false">キャンセル</v-btn>
          <v-btn color="red" text @click="deleteAuthor()">削除</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-item-group>
</template>

<script>
import AuthorCard from '../components/AuthorCard';

export default {
  name: 'AuthorView',
  data: () => ({
    breadcrumbItems: [
      {
        text: 'Microlib 書籍管理システム',
        disabled: true,
      },
      {
        text: '著者管理',
        disabled: true,
      }
    ],
    searchParams: {
      pageIndex: 1,
      pageSize: 9,
      authorName: ''
    },
    listAuthor: [],
    totalPages: 0,
    deleteDialogControl: false,
    deleteAuthorObj: {}
  }),
  components: {
    AuthorCard
  },
  activated() {
    // 画面データを初期化する
    this.getList()
  },
  methods: {
    // 著者リストを取得
    getList() {
      this.$request({
        url: '/microlib/author',
        method: 'get',
        params: {
            n: this.searchParams.authorName,
            i: this.searchParams.pageIndex - 1,
            s: this.searchParams.pageSize
          }
      })
      .then(response => {
        this.listAuthor = response.data.content;
        this.totalPages = response.data.totalPages;
      })
      .catch(e => {
        this.$emit('putMessage', e, 'error');
      });
    },
    // 著者を詳細ダイアログを開く
    openAuthorDetails(author) {
      if (author && author.id) {
        this.$emit('openDialog', author);
      } else {
        this.$emit('putMessage', 'パラーメータ不正', 'error');
      }
    },
    // 削除ダイアログを開く
    openDeleteDialog(author) {
      if (author && author.id) {
        this.deleteAuthorObj = author
        this.deleteDialogControl = true
      } else {
        this.$emit('putMessage', 'パラーメータ不正', 'error');
      }
    },
    // 著者を削除
    deleteAuthor() {
      this.$request({
        url: `/microlib/author/${this.deleteAuthorObj.id}`,
        method: 'delete'
      })
      .then(() => {
        this.deleteDialogControl = false
        this.$emit('putMessage', '著者を削除しました。', 'success');
        this.getList();
      })
      .catch(e => {
        this.$emit('putMessage', e.response.data, 'error');
      })
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