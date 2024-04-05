<template>
  <div>
    <el-menu
      class="side-nav-bar"
      router
      :collapse="this.$store.state.menu.collapse"
      :default-active="this.$route.path"
      background-color="#304156"
      text-color="#BFCBD9"
      active-text-color="#409EFF"
    >
      <SidebarItem v-for="(route,index) of this.$store.state.menu.userMenuList"
                   :key="index"
                   :item="route"
      />
    </el-menu>
  </div>
</template>
<script>
import SidebarItem from "@/layout/components/SidebarItem";
export default{
  components: {SidebarItem}
}
</script>
<style scoped>
.side-nav-bar:not(.el-menu--collapse) {
  width: 210px;
}
.side-nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  overflow-x: hidden;
  overflow-y: auto;
}
.side-nav-bar i {
  margin-right: 1rem;
}
*::-webkit-scrollbar {
  width: 0.5rem;
  height: 1px;
}
*::-webkit-scrollbar-thumb {
  border-radius: 0.5rem;
  background-color: rgba(144, 147, 153, 0.3);
}
</style>
