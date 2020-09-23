<template>
  <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="white darken-3">
      <v-app-bar-nav-icon class="bar-icon" @click="changeDrawer"></v-app-bar-nav-icon>
      <v-toolbar-title style="width: 300px" class="ml-0 pl-4">
        <span class="hidden-sm-and-down toobar-title" >Microlib 書籍管理システム</span>
      </v-toolbar-title>
      <v-text-field
        flat
        solo-inverted
        hide-details
        prepend-inner-icon="mdi-magnify"
        label="Search"
        class="hidden-sm-and-down"
      ></v-text-field>
      <v-spacer></v-spacer>
    </v-app-bar>
</template>

<script>
  export default {
    // props: {
    //   drawer: false
    // },
    data: () => ({
      drawer: true,
    }),
    methods: {
      changeDrawer () {
        this.drawer = !this.drawer
        this.$emit('drawer', this.drawer)
      }
    }

  }
</script>

<style lang="scss" scoped>
.toobar-title {
  color: black;
}
</style>