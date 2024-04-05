import {getInfo, login} from "@/api/login";
import {logout} from "@/api/menu";
import {resetRouter} from "@/router";
import {getToken, setToken} from "@/utils/auth";

export default {
    namespaced: true,
    //用户接口
    actions: {
        // 获取用户信息
        GetInfo({ commit, state }) {
            return new Promise((resolve, reject) => {
                getInfo().then(res => {

                    const user = res.data

                     const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/av.jpg") :  user.avatar;

                     user.avatar=avatar

                    commit("SET_USER",user)
                    resolve(res)
                }).catch(error => {

                    reject(error)
                })
            })
        },

        async login({commit}, userInfo) {
            const res = await login(userInfo);
            commit("SET_TOKEN", res.data.token);
            // commit("SET_USER", res.data.user);
        },
        logout({commit}) {
            logout().then(() => {
                //登出
                window.sessionStorage.clear()
                commit("SET_TOKEN", '');
                commit("SET_USER", {});
                resetRouter();

            })
        },
    },
    //操作数据
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token
            setToken(token)
        },
        SET_USER(state, user) {
            state.user = user

        },


    },
    //数据
    state: {
        token: getToken(), // 用户token
        user: null, // 用户token
    },
    getters: {



    },

}
