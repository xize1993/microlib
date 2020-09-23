<template>
  <v-item-group multiple>
    <v-container>
      <v-subheader>Microlib 書籍管理システム > 書籍管理 </v-subheader>
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
    page: 1
  }),
  components: {
    BookCard
  }
};
</script>