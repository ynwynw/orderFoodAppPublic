<template>
<div>
  首页
  <button @click="aaaa()">首页</button>
</div>
</template>

<script>
export default {
  name: "Home",
  methods:{
    aaaa(){
      this.$router.push("/home")
    }
  }
}
</script>

<style scoped>

</style>
