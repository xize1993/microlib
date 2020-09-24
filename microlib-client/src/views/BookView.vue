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
              <v-text-field
                label="タイトルで検索"
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field
                label="著者で検索"
              ></v-text-field>
            </v-col>
            <v-col cols="2" fill-height>
              <v-btn class="ma-2" outlined color="teal">検索</v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="n in 9" :key="n" cols="12" md="4">
          <v-item>
            <BookCard></BookCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>
    <div class="text-center">
      <v-pagination v-model="page" :length="6"></v-pagination>
    </div>
  </v-item-group>
</template>

<script>
import BookCard from "../components/BookCard";

export default {
  data: () => ({
    breadcrumbItems: [
      {
        text: "Microlib 書籍管理システム",
        disabled: true,
      },
      {
        text: "書籍管理",
        disabled: true,
      }
    ],
    page: 1
  }),
  components: {
    BookCard
  }
};
</script>