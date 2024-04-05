<template>
  <div v-show="!item.isHidden">
    <!-- 二级菜单 -->
    <template v-if="IsHaveMenu(item)">
      <el-submenu :index="resolvePath(item.path)" >
        <!-- 二级菜单标题 -->
        <template slot="title">
          <i style="margin-right: 10px"><svg-icon :icon-class="item.icon"/></i>
          <span>{{ item.name }}</span>
        </template>
        <!-- 二级菜单选项 -->
        <SidebarItem
            v-for="(route, index) in item.children"
            :key="index"
            :item="route"
            :base-path="resolvePath(item.path)"/>
      </el-submenu>
    </template>
    <!-- 一级菜单 -->
    <template v-else>
      <el-menu-item :index="resolvePath(item.path)">
        <i style="margin-right: 10px"><svg-icon :icon-class="item.icon"/></i>
        <span slot="title">{{ item.name }}</span>
      </el-menu-item>
    </template>
  </div>

</template>
<script>
import {isExternal} from "@/utils/validate";

export default {
  name: "SidebarItem",
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    basePath: {
      type: String,
      default: '/'
    }
  },
  created() {
    console.log(this.item)
  },
  methods:{
    IsHaveMenu(item){
      if(item.type==='M')
        return true
      return false;
    },
    resolvePath(routePath){
      // if(this.basePath==null)
      //   return '/'+routePath;
      // return this.basePath+'/'+routePath;
      //如果是外址
      if (isExternal(routePath)) {
        return routePath
      }
      //如果是外址
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      //如果routePath是以/开头就不要拼接
      let path = require("path")
      return path.resolve(this.basePath, routePath)
    },

  },
}
</script>

<style scoped>

</style>
