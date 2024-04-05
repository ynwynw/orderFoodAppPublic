
export default {
    namespaced: true,
    //用户接口
    actions: {
        async login({commit}, userInfo) {

        },
        logout({commit}) {
          
        },
    },
    //操作数据
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token
			console.log("设置token成功")
			uni.setStorageSync('token', token)
            // sessionStorage.setItem("token", token)
        },
        SET_USER(state, user) {
			console.log(user)
            state.user = user
			console.log("设置user成功")
			uni.setStorageSync('user', user)
        },


    },
    //数据
    state: {
        token: uni.getStorageSync('token')!=null?uni.getStorageSync('token'):null,  // 用户token
        user:uni.getStorageSync('user')!=null?uni.getStorageSync('user'):null,  // 用户
		isLogin:0,
    },
   getters: {
		isLogin(state){
			console.log("-----"+uni.getStorageSync('token'))
			console.log("----"+state.token)
			return state.token!=null&&state.token!="";
		} 
   },

}
