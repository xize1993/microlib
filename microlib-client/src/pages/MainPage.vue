<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" :clipped="$vuetify.breakpoint.lgAndUp" app>
      <v-list dense>
        <template v-for="item in items">
          <v-row v-if="item.heading" :key="item.heading" align="center">
            <v-col cols="6">
              <v-subheader v-if="item.heading">{{ item.heading }}</v-subheader>
            </v-col>
            <v-col cols="6" class="text-center">
              <a href="#!" class="body-2 black--text">EDIT</a>
            </v-col>
          </v-row>

          <v-list-group
            v-else-if="item.children"
            :key="item.text"
            v-model="item.model"
            :prepend-icon="item.model ? item.icon : item['icon-alt']"
            append-icon
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>
                  <router-link :to="{ name: item.routerName}">{{ item.text }}</router-link>
                </v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item v-for="(child, i) in item.children" :key="i" link>
              <v-list-item-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ child.text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>

          <v-list-item v-else :key="item.text" link>
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>
                 <router-link :to="{ name: item.routerName}">{{ item.text }}</router-link>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>

    <AppBar v-on:drawer="changeDrawer"></AppBar>

    <v-main>
      <router-view></router-view>
    </v-main>

    <v-btn bottom color="pink" dark fab fixed right @click="openDialog()">
      <v-icon>mdi-plus</v-icon>
    </v-btn>

    <v-dialog v-model="dialog" width="800px">
      <component :is="currentForm"></component>
    </v-dialog>

  </v-app>
</template>

<script>
import AppBar from "../components/AppBar";
import BookForm from "../components/BookForm";
import AuthorForm from "../components/AuthorForm";

export default {
  name: "App",

  components: {
    AppBar
  },
  props: {
    source: String
  },
  data: () => ({
    dialog: false,
    drawer: true,
    currentForm: BookForm,
    items: [
      { icon: "mdi-book-open-page-variant", text: "書籍", routerName: "book" },
      { icon: "mdi-contacts", text: "著者", routerName: "author" },
    ]
  }),
  methods: {
      changeDrawer: function (drawer) {
        this.drawer = drawer
      },
      openDialog: function() {
        // ルーターよりフォームページを変更
        console.log(this.$route.name)
        if (this.$route.name === "book") {
          this.currentForm = BookForm
        } else if (this.$route.name === "author") {
          this.currentForm = AuthorForm
        }
        this.dialog = !this.dialog
      }
    }
};
</script>

<style lang="scss">
.lib-breadcrumb {
  padding: 0;
}
</style>