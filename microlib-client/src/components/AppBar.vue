<template>
  <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="white">
    <v-app-bar-nav-icon class="bar-icon" @click="switchDrawer"></v-app-bar-nav-icon>
    <v-toolbar-title style="width: 300px" class="ml-0 pl-4">
      <span class="hidden-sm-and-down" color="black">Microlib 書籍管理システム</span>
    </v-toolbar-title>
    <v-spacer></v-spacer>
  </v-app-bar>
</template>

<script>
export default {
  methods: {
    switchDrawer() {
      this.$emit("switchDrawer");
    }
  }
};
</script>