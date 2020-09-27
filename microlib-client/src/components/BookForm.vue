<template>
  <v-card>
    <v-card-title :class="bookForm.id ? 'green' : 'blue'">書籍{{bookForm.id ? '更新' : '登録'}}</v-card-title>
    <v-container>
      <v-row class="mx-2">
        <v-col cols="12">
          <v-text-field
            v-model="bookForm.title"
            prepend-icon="mdi-format-title"
            label="タイトル"
            maxlength="100"
            counter="100"
          ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-file-input
            v-model="bookForm.imgFile"
            accept="image/png, image/jpeg, image/bmp"
            prepend-icon="mdi-camera"
            :placeholder="bookForm.id ? '表紙を更新する' : ''"
            label="表紙"
          ></v-file-input>
        </v-col>
        
        <v-col cols="6">
          <v-autocomplete
            v-model="bookForm.authorId"
            label="著者"
            persistent-hint
            :loading="authorsLoading"
            :search-input.sync="authorsList"
            :items="authors"
            item-text="authorName"
            item-value="id"
            prepend-icon="mdi-account-box"
          ></v-autocomplete>
        </v-col>

        <v-col cols="6">
          <v-text-field v-model="bookForm.isbn" prepend-icon="mdi-library" label="ISBN"></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-select
            v-model="bookForm.subject"
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
            ref="datePickerMenu"
            v-model="datePickerMenu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="bookForm.publicationDate"
                label="出版日"
                prepend-icon="mdi-calendar-month"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="bookForm.publicationDate"
              :allowed-dates="allowedDates"
              no-title
              scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="datePickerMenu = false">キャンセル</v-btn>
              <v-btn text color="primary" @click="$refs.datePickerMenu.save(bookForm.publicationDate)">選択</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="bookForm.price"
            prepend-icon="mdi-currency-jpy"
            label="価格"
            type="number"
          ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="bookForm.pageCount"
            prepend-icon="mdi-counter"
            label="ページ数"
            type="number"
            min="0"
          ></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-textarea
            v-model="bookForm.description"
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
    formObj: Object
  },
  data: () => ({
    subjectItems: constants.subjectItems,
    datePickerMenu: false,
    authors: [],
    authorsLoading: false,
    authorsList: null
  }),
  computed: {
    bookForm() {
      console.log(this.formObj)
      if (this.formObj) {
        return this.formObj;
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
    this.$request({
      url: '/microlib/author',
      method: 'get'
    })
    .then(response => {
      this.authors = response.data.content ? response.data.content : [];
      this.authorsLoading = false
    })
    .catch(e => {
      console.log(e);
      this.authorsLoading = false
      this.$emit('putMessage', '著者データ取得に失敗しました！', 'error');
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
      if (this.bookForm.imgFile)
        formData.append('imgFile', this.bookForm.imgFile);
      if (this.bookForm.title) 
        formData.append('title', this.bookForm.title);
      if (this.bookForm.authorId)
        formData.append('authorId', this.bookForm.authorId);
      if (this.bookForm.isbn) 
        formData.append('isbn', this.bookForm.isbn);
      if (this.bookForm.subject)
        formData.append('subject', this.bookForm.subject);
      if (this.bookForm.publicationDate)
        formData.append('publicationDate', this.bookForm.publicationDate);
      if (this.bookForm.price) 
        formData.append('price', this.bookForm.price);
      if (this.bookForm.pageCount)
        formData.append('pageCount', this.bookForm.pageCount);
      if (this.bookForm.description)
        formData.append('description', this.bookForm.description);

      if (this.bookForm.id) {
        // 書籍更新リクエスト
        formData.append('id', this.bookForm.id);
        this.$request({
          url: '/microlib/book',
          method: 'patch',
          data: formData,
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(() => {
          // 成功の場合ダイアログをクローズ
          this.$emit('closeDialog');
          this.$emit('putMessage', '書籍を更新しました！', 'success');
          this.$emit('refreshViewList');
        })
        .catch(e => {
          this.$emit('putMessage', e.response.data, 'error');
        });
      } else {
        // 書籍新規作成リクエスト
        this.$request({
          url: '/microlib/book',
          method: 'post',
          data: formData,
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(() => {
          this.$emit('closeDialog');
          this.$emit('putMessage', '書籍を登録しました！', 'success');
          this.$emit('refreshViewList');
        })
        .catch(e => {
          this.$emit('putMessage', e.response.data, 'error');
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
      fetch(this.$request.adornUrl(`/microlib/author?n=${val}`))
        .then(res => res.json())
        .then(res => {
          this.authors = res.content ? res.content : [];
        })
        .catch(e => {
          console.log(e);
          this.$emit('putMessage', '著者データ取得に失敗しました！', 'error');
        })
        .finally(() => (this.authorsLoading = false));
    }
  }
};
</script>