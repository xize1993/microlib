<template>
  <v-card>
    <v-card-title class="red darken-2">書籍登録</v-card-title>
    <v-container>
      <v-row class="mx-2">
        <v-col cols="12">
          <v-text-field prepend-icon="mdi-format-title" label="タイトル"></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-file-input
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            prepend-icon="mdi-camera"
            label="表紙"
          ></v-file-input>
        </v-col>

        <v-col cols="6">
          <v-autocomplete
            v-model="model"
            :items="autrhos"
            :loading="isLoading"
            :search-input.sync="search"
            color="white"
            hide-no-data
            hide-selected
            item-text="Description"
            item-value="API"
            label="著者"
            prepend-icon="mdi-account-box"
            return-object
          ></v-autocomplete>
        </v-col>

        <v-col cols="6">
          <v-text-field prepend-icon="mdi-library" label="ISBN" ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-select
            v-model="e1"
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
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="dataForm.publicationDate"
                label="出版日"
                prepend-icon="mdi-calendar-month"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="dataForm.publicationDate" :allowed-dates="allowedDates" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">キャンセル</v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(dataForm.publicationDate)">選択</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="6">
          <v-text-field prepend-icon="mdi-currency-jpy" label="価格" type="number"></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-text-field prepend-icon="mdi-counter" label="ページ数" type="number" min="0"></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-textarea label="内容紹介" rows="1" prepend-icon="mdi-comment"></v-textarea>
        </v-col>
      </v-row>
    </v-container>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text @click="dialog = false">キャンセル</v-btn>
      <v-btn text color="primary" @click="save()">登録</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import constants from "../common/constants.js";

export default {
  props: {},
  data: () => ({
    dataForm: {
      id: 0,
      beanName: "",
      params: "",
      cronExpression: "",
      remark: "",
      status: 0,
      publicationDate: ""
    },
    subjectItems: constants.subjectItems,
    autrhos: [],
    isLoading: false,
    model: null,
    search: null
  }),
  methods: {
    // 出版日：過去日付を選択できない
    allowedDates: val => new Date(val) > new Date(),
    // 登録ボタン
    save() {
      console.log(this.dataForm)
    }
  },
  watch: {
  }
};
</script>