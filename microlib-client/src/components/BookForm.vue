<template>
  <v-card>
    <v-card-title :class="formObj.id ? 'green' : 'blue'">書籍{{formObj.id ? '更新' : '登録'}}</v-card-title>
    <v-container>
      <v-row class="mx-2">
        <v-col cols="12">
          <v-text-field
            v-model="formObj.title"
            prepend-icon="mdi-format-title"
            label="タイトル"
            maxlength="100"
            counter="“100”"
          ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-file-input
            v-model="formObj.imgFile"
            accept="image/png, image/jpeg, image/bmp"
            prepend-icon="mdi-camera"
            :placeholder="formObj.id ? '表紙を更新する' : ''"
            label="表紙"
          ></v-file-input>
        </v-col>

        <v-col cols="6">
          <v-autocomplete
            v-model="formObj.authorId"
            label="著者"
            persistent-hint
            :loading="authorsLoading"
            :search-input.sync="authorsList"
            :items="authors"
            item-text="authorName"
            item-value="id"
            prepend-icon="mdi-account-box"
          >
          </v-autocomplete>
        </v-col>

        <v-col cols="6">
          <v-text-field v-model="formObj.isbn" prepend-icon="mdi-library" label="ISBN"></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-select
            v-model="formObj.subject"
            :items="subjectItems"
            menu-props="auto"
            label="種別"
            hide-details
            prepend-icon="mdi-star-circle"
            single-line
          ></v-select>
        </v-col>

        <v-col cols="12">
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="formObj.publicationDate"
                label="出版日"
                prepend-icon="mdi-calendar-month"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="formObj.publicationDate"
              :allowed-dates="allowedDates"
              no-title
              scrollable
            >
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">キャンセル</v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(formObj.publicationDate)">選択</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="formObj.price"
            prepend-icon="mdi-currency-jpy"
            label="価格"
            type="number"
          ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="formObj.pageCount"
            prepend-icon="mdi-counter"
            label="ページ数"
            type="number"
            min="0"
          ></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-textarea
            v-model="formObj.description"
            label="内容紹介"
            rows="1"
            prepend-icon="mdi-comment"
          ></v-textarea>
        </v-col>
      </v-row>
    </v-container>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text @click="closeSelf()">キャンセル</v-btn>
      <v-btn text color="primary" @click="saveBook()">登録</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import constants from '../common/constants.js';

export default {
  name: 'BookForm',
  props: {
    dialogFormObj: Object
  },
  data: () => ({
    subjectItems: constants.subjectItems,
    menu: false,
    authors: [],
    authorsLoading: false,
    authorsList: null
  }),
  computed: {
    formObj() {
      if (this.dialogFormObj) {
        return this.dialogFormObj;
      } else {
        return {
          id: '',
          imgFile: undefined,
          title: '',
          authorId: '',
          isbn: '',
          subject: '',
          publicationDate: '',
          price: '',
          pageCount: '',
          description: ''
        };
      }
    }
  },
  activated() {
    console.log('activated')
    // 画面データを初期化する
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
    // 出版日は過去日付の選択を禁止する
    allowedDates: val => new Date(val) > new Date(),
    // ダイアログをクローズ
    closeSelf() {
      this.$emit('closeDialog');
    },
    // 登録ボタン
    saveBook() {
      // リクエストパラーメータを作成
      let formData = new FormData();
      if (this.formObj.imgFile)
        formData.append('imgFile', this.formObj.imgFile);
      if (this.formObj.title) formData.append('title', this.formObj.title);
      if (this.formObj.authorId)
        formData.append('authorId', this.formObj.authorId);
      if (this.formObj.isbn) formData.append('isbn', this.formObj.isbn);
      if (this.formObj.subject)
        formData.append('subject', this.formObj.subject);
      if (this.formObj.publicationDate)
        formData.append('publicationDate', this.formObj.publicationDate);
      if (this.formObj.price) formData.append('price', this.formObj.price);
      if (this.formObj.pageCount)
        formData.append('pageCount', this.formObj.pageCount);
      if (this.formObj.description)
        formData.append('description', this.formObj.description);

      if (this.formObj.id) {
        formData.append('id', this.formObj.id);
        this.axios
          .patch('http://localhost:8081/microlib/book', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(() => {
            // 成功の場合ダイアログをクローズ
            this.$emit('closeDialog');
            this.$emit('showMessage', '書籍を更新しました！', 'success');
            this.$emit('refreshViewList');
          })
          .catch(e => {
            this.$emit('showMessage', e.response.data, 'error');
          });
      } else {
        this.axios
          .post('http://localhost:8081/microlib/book', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(() => {
            this.$emit('closeDialog');
            this.$emit('showMessage', '書籍を登録しました！', 'success');
            this.$emit('refreshViewList');
          })
          .catch(e => {
            this.$emit('showMessage', e.response.data, 'error');
          });
      }
    }
  },
  watch: {
    authorsList(val) {
      // データすでに取得済みあるいはローディング中
      if (this.authors.length > 0 || this.authorsLoading) return;
      this.authorsLoading = true
      // 著者一覧APIを呼び出す
      fetch(`http://localhost:8081/microlib/author?n=${val}`)
        .then(res => res.json())
        .then(res => {
          this.authors = res.content ? res.content : [];
        })
        .catch(e => {
          console.log(e);
          this.$emit('showMessage', '著者データ取得に失敗しました！', 'error');
        })
        .finally(() => (this.authorsLoading = false));
    }
  }
};
</script>