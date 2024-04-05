import router, {createRouter, resetRouter} from "@/router";
import {generaMenu} from "/src/api/menu"
const loadView = view => {return ()=>view=='Layout'||view==null||view==''?import('@/components/ParentView'):import('@/views' + view)};
function dfs(userMenuList){
    if(userMenuList==null)
        return [];
    const router=[];
    for(let i=0;i<userMenuList.length;i++)
    {
        router.push({
                path: userMenuList[i].path,
                component: loadView(userMenuList[i].component),
                name:userMenuList[i].routerName,
                meta:{
                    menuName:userMenuList[i].name,
                },
                children:dfs(userMenuList[i].children)
            })
    }
    return router;
}
export default {
    namespaced:true,
    //用户接口
    actions:{
          generaMenu ({ commit }) {
              return new Promise(resolve => {
                  generaMenu().then(res=>{
                      let userMenuList = res.data;
                      //侧边栏路由
                      let asyncRoutes=
                         [ {
                              path: '/',
                              component: ()=>import("@/layout"),
                              children:dfs(userMenuList)
                          },
                             {
                                 path: '*',
                                 component: ()=>import("@/views/error/404"),
                             }
                          ];
                      resetRouter();
                      router.addRoutes(asyncRoutes);

                      console.log(asyncRoutes)
                      commit('handleAsyncRoutesChange',asyncRoutes);
                      //侧边栏菜单显示
                      commit('handleUserMenuListChange',userMenuList);
                      resolve()
                  })
              })



         }
    },
    //操作数据
    mutations:{
        handleAsyncRoutesChange(state,val){
            state.asyncRoutes=val
        },
        handleUserMenuListChange(state,val){
            // localStorage.setItem("userMenuList",val)
            state.userMenuList=val
        },

        saveTab(state, tab) {
            if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
                state.tabList.push({ name: tab.meta.menuName, path: tab.path });
            }
        },
        removeTab(state, tab) {
            let index = state.tabList.findIndex(item => item.name === tab.name);
            state.tabList.splice(index, 1);
        },
        resetTab(state) {
            state.tabList = [];
        },
        trigger(state) {
            state.collapse = !state.collapse;
        },
    },
    //数据
    state:{
        userMenuList:[], // 用户菜单,
        asyncRoutes: [],

        collapse: false,
        tabList: [],

    },
    getters:{

    },
}
