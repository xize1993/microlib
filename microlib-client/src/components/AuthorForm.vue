<template>
  <!-- <v-card>
    <v-card-title class="red">著者登録</v-card-title> -->
    <v-container>
      <v-row class="mx-2">
        <v-col cols="6">
          <v-text-field prepend-icon="mdi-account-circle" placeholder="名前"></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field prepend-icon="mdi-account-circle-outline" placeholder="名前（フリカナ）"></v-text-field>
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
                v-model="date"
                label="出生日を選択する"
                prepend-icon="mdi-calendar-month"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="date" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">キャンセル</v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(date)">選択</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
        <v-col cols="12">
          <v-textarea label="著者紹介" rows="1" prepend-icon="mdi-comment"></v-textarea>
        </v-col>
      </v-row>
    </v-container>
    <!-- <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text @click="dialog = false">Cancel</v-btn>
      <v-btn text color="primary" @click="dialog = false">Save</v-btn>
    </v-card-actions>
  </v-card> -->
</template>

<script>
export default {
  name: "AuthorForm",

};
</script>