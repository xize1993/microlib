<template>
  <v-item-group multiple>
    <v-container>
      <v-subheader>Microlib 書籍管理システム > 著者管理 </v-subheader>
      <v-row>
        <v-col v-for="n in 18" :key="n" cols="12" md="2">
          <v-item>
            <AuthorCard></AuthorCard>
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
import AuthorCard from "../components/AuthorCard";

export default {
  data: () => ({
    page: 1
  }),
  components: {
    AuthorCard
  }
};
</script>

<style lang="scss" scoped>
</style>