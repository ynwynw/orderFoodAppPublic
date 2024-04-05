
export default {
    namespaced: true,
    //用户接口
    actions: {

    },
    //操作数据
    mutations: {
        OpenEditDialog(state){
            console.log("wll")
            state.foodEditDialog= true;
        },
        CloseEditDialog(state){
            state.foodEditDialog=false;
        },
        setFormData(state,data){
            state.formData=data;
        }
    },
    //数据
    state: {
        foodEditDialog: false,
        formData:{},
         },
    getters: {},

}
