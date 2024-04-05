<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import store from "@/store";
export default {
  name: 'App',
  components: {
  },
  created() {
    // store.dispatch('menu/generaMenu');
  }
}
</script>

<style>

</style>
