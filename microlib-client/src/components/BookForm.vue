<template>
  <v-card>
    <v-card-title :class="getDialogColor()">書籍{{getDialogTitle()}}</v-card-title>
    <v-container>
      <ValidationObserver ref="observer">
        <v-form :readonly="getDialogTitle()=='詳細'">
          <v-row class="mx-2">
            <v-col cols="12">
              <ValidationProvider v-slot="{ errors }" name="タイトル" rules="max:100">
                <v-text-field
                  name="title"
                  v-model="bookForm.title"
                  prepend-icon="mdi-format-title"
                  label="タイトル"
                  counter="100"
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>

            <v-col cols="6">
              <v-file-input
                name="imgFile"
                v-model="bookForm.imgFile"
                accept="image/png, image/jpeg, image/bmp"
                prepend-icon="mdi-camera"
                :placeholder="bookForm.id ? '表紙を更新する' : ''"
                label="表紙"
              ></v-file-input>
            </v-col>
            
            <v-col cols="6">
              <ValidationProvider v-slot="{ errors }" name="著者" rules="required">
                <v-autocomplete
                  name="authorId"
                  v-model="bookForm.author.id"
                  label="著者"
                  persistent-hint
                  :loading="authorsLoading"
                  :search-input.sync="authorsList"
                  :items="authors"
                  item-text="authorName"
                  item-value="id"
                  prepend-icon="mdi-account-box"
                  required
                  :error-messages="errors"
                ></v-autocomplete>
              </ValidationProvider>
            </v-col>

            <v-col cols="6">
              <ValidationProvider v-slot="{ errors }" name="ISBN号" rules="min:10|max:13">
                <v-text-field 
                  name="isbn" 
                  v-model="bookForm.isbn" 
                  prepend-icon="mdi-library" 
                  label="ISBN"
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>

            <v-col cols="6">
              <v-select
                name="subject"
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
                  name="publicationDate"
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
              <ValidationProvider v-slot="{ errors }" name="価格" rules="min_value:0">
                <v-text-field
                  name="price"
                  v-model="bookForm.price"
                  prepend-icon="mdi-currency-jpy"
                  label="価格"
                  type="number"
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>

            <v-col cols="6">
              <ValidationProvider v-slot="{ errors }" name="ページ数" rules="min_value:0">
                <v-text-field
                  name="pageCount"
                  v-model="bookForm.pageCount"
                  prepend-icon="mdi-counter"
                  label="ページ数"
                  type="number"
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>

            <v-col cols="12">
              <ValidationProvider v-slot="{ errors }" name="内容紹介" rules="max:2000">
                <v-textarea
                  name="description"
                  v-model="bookForm.description"
                  label="内容紹介"
                  counter="2000"
                  rows="1"
                  prepend-icon="mdi-comment"
                  :error-messages="errors"
                ></v-textarea>
              </ValidationProvider>
            </v-col>
          </v-row>
        </v-form>
      </ValidationObserver>
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
    bookForm: function () {
      return {
          id: this.formObj.id || '',
          imgFile: undefined,
          title: this.formObj.title || '',
          author: {
            id: this.formObj && this.formObj.author ? this.formObj.author.id : ''
          },
          isbn: this.formObj.isbn || '',
          subject: this.formObj.subject || '',
          publicationDate: this.formObj.publicationDate || '',
          price: this.formObj.price || '',
          pageCount: this.formObj.pageCount || '',
          description: this.formObj.description || ''
        }
    }
  },
  activated() {
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
    // ダイアログタイトル
    getDialogTitle() {
      if (!this.bookForm.id) {
        return '登録'
      } else {
        if (this.bookForm.publicationDate && new Date(`${this.bookForm.publicationDate} 00:00:00`) <= new Date(new Date().toLocaleDateString())) {
          return '詳細'
        } else {
          return '更新'
        }
      }
    },
    // ダイアログカーラ
    getDialogColor() {
      if (!this.bookForm.id) {
        return 'blue'
      } else {
        if (this.bookForm.publicationDate && new Date(`${this.bookForm.publicationDate} 00:00:00`) <= new Date(new Date().toLocaleDateString())) {
          return 'yellow'
        } else {
          return 'green'
        }
      }
    },
    // 出版日は過去日付の選択を禁止する
    allowedDates: val => new Date(val) >= new Date(new Date().toLocaleDateString()),
    // ダイアログをクローズ
    closeSelf() {
      this.$emit('closeDialog');
    },
    // 登録ボタン
    saveBook() {
      // 入力チェック
      this.$refs.observer.validate().then(result => {
        if (result) {
          // リクエストパラーメータを作成
          let formData = new FormData();
          if (this.bookForm.imgFile)
            formData.append('imgFile', this.bookForm.imgFile);
          if (this.bookForm.title) 
            formData.append('title', this.bookForm.title);
          if (this.bookForm.author.id)
            formData.append('authorId', this.bookForm.author.id);
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
              this.$emit('putMessage', this.$request.adornErrorMsg(e), 'error');
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
              this.$emit('putMessage', this.$request.adornErrorMsg(e), 'error');
            });
          }
        }
      })
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