<template>
  <v-app>
    <!-- メニュー引出し -->
    <v-navigation-drawer v-model="drawerControl" :clipped="$vuetify.breakpoint.lgAndUp" app>
      <v-list dense>
        <template v-for="item in menus">
          <v-list-item :key="item.text" link>
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

    <!-- APPバー -->
    <AppBar @switchDrawer="switchDrawer"></AppBar>

    <!-- メインレイアウト -->
    <v-main>
      <keep-alive>
        <router-view 
          ref="currentView"
          @openDialog="openDialog"
          @putMessage="putMessage"
        ></router-view>
      </keep-alive>
    </v-main>

    <!-- フローティングボタン -->
    <v-btn bottom color="primary" fab fixed right @click="openDialog()">
      <v-icon>mdi-plus</v-icon>
    </v-btn>

    <!-- ダイアログ -->
    <v-dialog v-model="dialogControl" width="800px" v-if="dialogControl">
        <keep-alive>
          <component
            :is="currentForm"
            :formObj="formObj"
            @closeDialog="closeDialog"
            @putMessage="putMessage"
            @refreshViewList="refreshCurrentViewList"
          ></component>
        </keep-alive>
    </v-dialog>

    <!-- 画面マクス TODO -->
    <v-overlay v-model="apiLoading">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>

    <!-- メッセージ -->
    <v-snackbar
      v-model="messageBarControl"
      :timeout="3000"
      :top="true"
      :color="messageColor"
    >{{message}}</v-snackbar>
  </v-app>
</template>

<script>
import AppBar from '../components/AppBar';
import BookForm from '../components/BookForm';
import AuthorForm from '../components/AuthorForm';

export default {
  name: 'App',

  components: {
    AppBar
  },
  data: () => ({
    drawerControl: true,
    currentForm: BookForm,
    menus: [
      { icon: 'mdi-book-open-page-variant', text: '書籍', routerName: 'book' },
      { icon: 'mdi-contacts', text: '著者', routerName: 'author' }
    ],
    messageBarControl: false,
    apiLoading: false,
    message: '',
    messageColor: 'info',
    dialogControl: false,
    formObj: {},
  }),
  computed: {
    dialogCompentKey: function () {
      return new Date().getTime()
    }
  },
  methods: {
    // 引出し表示変換
    switchDrawer() {
      this.drawerControl = !this.drawerControl;
    },
    // フォームダイアログを開く
    openDialog(formObj) {
      // ルーターよりフォームページを変更
      if (this.$route.name === 'book') {
        this.currentForm = BookForm;
      } else if (this.$route.name === 'author') {
        this.currentForm = AuthorForm;
      }
      this.formObj = formObj || {}
      this.dialogControl = true;
    },
    // フォームダイアログを閉じる
    closeDialog() {
      this.formObj = {}
      this.dialogControl = false;
    },
    // メッセージを表示する
    putMessage(msg, color) {
      this.message = msg;
      if (color) this.messageColor = color;
      this.messageBarControl = true;
    },
    // 現在のビューの一覧を更新する
    refreshCurrentViewList() {
      this.$refs.currentView.getList();
    },
  }
};
</script>

<style lang="scss">
.lib-breadcrumb {
  padding: 0;
}
</style>