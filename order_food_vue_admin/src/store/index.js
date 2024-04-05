import Vuex from 'vuex'
import Vue from 'vue'
import menu from "@/store/menu";
import user from "@/store/user";
import food from "@/store/food"
import getters from "@/store/getters";
Vue.use(Vuex)
const store = new Vuex.Store({
    modules:{
        menu,
        user,
        food
    },
    getters

})

export default store
