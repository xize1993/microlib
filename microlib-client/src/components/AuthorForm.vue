<template>
  <v-card>
    <v-card-title class="grey darken-2">著者</v-card-title>
    <v-container>
      <v-row class="mx-2">
        <v-col class="align-center justify-space-between" cols="12">
          <v-row align="center" class="mr-0">
            <v-avatar size="40px" class="mx-3">
              <img src="//ssl.gstatic.com/s2/oz/images/sge/grey_silhouette.png" alt />
            </v-avatar>
            <v-text-field placeholder="Name"></v-text-field>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-card-actions>
      <v-btn text color="primary">More</v-btn>
      <v-spacer></v-spacer>
      <v-btn text color="primary" @click="dialog = false">Cancel</v-btn>
      <v-btn text @click="dialog = false">Save</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {

  },
  data: () => ({

  }),
  methods: {
    
  }
};
</script>