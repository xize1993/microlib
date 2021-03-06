<template>
  <v-card>
    <v-card-title :class="authorForm.id ? 'green' : 'blue'">著者{{authorForm.id ? '更新' : '登録'}}</v-card-title>
    <v-container>
      <ValidationObserver ref="observer">
        <v-form>
          <v-row class="mx-2">
            <v-col cols="6">
              <ValidationProvider v-slot="{ errors }" name="著者名" rules="required|max:50">
                <v-text-field
                  v-model="authorForm.authorName"
                  prepend-icon="mdi-account-circle"
                  label="著者名"
                  counter="50"
                  required
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>
            <v-col cols="6">
              <ValidationProvider v-slot="{ errors }" name="著者名（フリカナ）" rules="max:50">
                <v-text-field
                  v-model="authorForm.authorNameKana"
                  prepend-icon="mdi-account-circle-outline"
                  label="著者名（フリカナ）"
                  counter="50"
                  :error-messages="errors"
                ></v-text-field>
              </ValidationProvider>
            </v-col>
            <v-col cols="12">
              <v-menu
                ref="datePickerMenu"
                v-model="datePickerMenu"
                :close-on-content-click="false"
                :return-value.sync="authorForm.birthday"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="authorForm.birthday"
                    label="出生日を選択する"
                    prepend-icon="mdi-calendar-month"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="authorForm.birthday"
                  :allowed-dates="allowedDates"
                  no-title
                  scrollable
                >
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="datePickerMenu = false">キャンセル</v-btn>
                  <v-btn
                    text
                    color="primary"
                    @click="$refs.datePickerMenu.save(authorForm.birthday)"
                  >選択</v-btn>
                </v-date-picker>
              </v-menu>
            </v-col>
            <v-col cols="12">
              <ValidationProvider v-slot="{ errors }" name="著者紹介" rules="max:1000">
                <v-textarea 
                  v-model="authorForm.description" 
                  label="著者紹介" 
                  rows="1"
                  counter="1000"
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
      <v-btn text color="primary" @click="saveAuthor()">登録</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "AuthorForm",
  props: {
    formObj: Object
  },
  data: () => ({
    datePickerMenu: false
  }),
  computed: {
    authorForm: function() {
      return {
        id: this.formObj.id || "",
        authorName: this.formObj.authorName || "",
        authorNameKana: this.formObj.authorNameKana || "",
        birthday: this.formObj.birthday || "",
        description: this.formObj.description || ""
      };
    }
  },
  methods: {
    // 誕生日は将来日付の選択を禁止する
    allowedDates: val => new Date(val) < new Date(),
    // ダイアログをクローズ
    closeSelf() {
      this.$emit("closeDialog");
    },
    // 登録ボタン
    saveAuthor() {
      // 入力チェック
      this.$refs.observer.validate().then(result => {
        if (result) {
          if (this.authorForm.id) {
            // 著者更新リクエスト
            this.$request({
              url: "/microlib/author",
              method: "patch",
              data: this.authorForm
            })
              .then(() => {
                // 成功の場合ダイアログをクローズ
                this.$emit("closeDialog");
                this.$emit("putMessage", "著者を更新しました！", "success");
                this.$emit("refreshViewList");
              })
              .catch(e => {
                this.$emit("putMessage", this.$request.adornErrorMsg(e), "error");
              });
          } else {
            // 著者新規作成リクエスト
            this.$request({
              url: "/microlib/author",
              method: "post",
              data: this.authorForm
            })
              .then(() => {
                this.$emit("closeDialog");
                this.$emit("putMessage", "著者を登録しました！", "success");
                this.$emit("refreshViewList");
              })
              .catch(e => {
                this.$emit("putMessage", this.$request.adornErrorMsg(e), "error");
              });
          }
        }
      })

    }
  }
};
</script>