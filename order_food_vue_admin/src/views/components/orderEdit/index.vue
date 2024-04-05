<template>
  <el-dialog
      :visible="isShow"
      :before-close="closeDialog"
  >
    <el-card class="main-card">
      <div class="title">订单处理</div>
      <!-- 表格操作 -->
      <div class="operation-container">
        <!-- 条件筛选 -->
        <div style="margin-left:auto;margin-top: 3px;display: flex " >
          <div>
            <!-- 订单状态 -->
            <el-select
                clearable
                size="small"
                v-model="orderStatus"
                filterable
                placeholder="请选择订单状态"
                style="margin-right:1rem"
            >
              <el-option
                  v-for="(item,key) in orderStatusList"
                  :key=key
                  :label="item.name"
                  :value="item.id"
              />
            </el-select>
          </div>
          <!-- 支付状态 -->
          <div>
            <el-select
                clearable
                size="small"
                v-model="payStatus"
                filterable
                placeholder="请选择订单状态"
                style="margin-right:1rem"
            >
              <el-option
                  v-for="(item,key) in payStatusList"
                  :key=key
                  :label="item.name"
                  :value="item.id"
              />
            </el-select>
          </div>
          <div>
            <template>
              <el-button type="text" @click="open">查看备注</el-button>
            </template>
          </div>
          <div>
            <el-button
                type="primary"
                size="small"
                style="margin-left:1rem"
                @click="change()"
            >
              确定修改
            </el-button>
          </div>
        </div>
      </div>
      <!-- 表格展示 -->
      <el-table
          v-loading="loading"
          :data="formData"
          row-key="id"
          :default-expand-all="isExpandAll"
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <!-- 食物标题 -->
        <el-table-column prop="name" label="食物名称" align="center"/>
        <!-- 食物数量 -->
        <el-table-column
            prop="num"
            label="数量"

            align="center"
        >
        </el-table-column>
        <!-- 扩展描述 -->
        <el-table-column
            prop="propertyText"
            label="描述"

            align="center">
        </el-table-column>
        <!-- 食物价格 -->
        <el-table-column
            prop="price"
            label="单价"

            align="center"
        >
        </el-table-column>
      </el-table>
    </el-card>

    <span slot="footer" class="dialog-footer">
    <el-button @click="closeDialog">取 消</el-button>
    <el-button type="primary" @click="closeDialog">确 定</el-button>
  </span>
  </el-dialog>
</template>

<script>



const {getOrderFoodByOrderId, getOrderStatusByOrderId, updateOrderStatusById} = require("@/api/orderEdit");

export default {

  name:"orderEdit",
  created() {
    // this.listArticles();
  },
  data: function() {
    return {
      // 是否展开，默认全部折叠
      orderId:null,
      isExpandAll: false,
      formData:[],
      loading: true,
      activeStatus: "all",
      isDelete: 0,
      status: null,
      orderStatusList:[
        {
          id:"1",
          name:"待处理"
        },
        {
          id:"2",
          name:"处理中"
        },
        {
          id:"3",
          name:"已发货"
        },
        {
          id:"4",
          name:"已取消"
        },
      ],
      orderStatus:"1",
      payStatus:"2",
      payStatusList:[
        {
          id:"1",
          name:"待支付"
        },
        {
          id:"2",
          name:"已支付"
        },
        {
          id:"3",
          name:"已退款"
        },
      ],
      remarks:"",
    };
  },
  props:{
    isShow:{
      type:Boolean,
      default:false
    }

  },
  mounted() {
    this.monitoring();
  },
  methods: {
    monitoring() { // 监听事件
      this.$on('openEdit', (id) => {
        console.log('方法1:触发监听事件监听成功')
        this.listArticles(id)
      })
    },
    closeDialog(){
      this.$emit("close");
    },

    open() {
      this.$alert(this.remarks, '客户备注', {
        confirmButtonText: '确定',
      });
    },
    async change() {
      const params = {
        id: this.orderId,
        payStatus: this.payStatus,
        orderStatus: this.orderStatus
      }
       await updateOrderStatusById(params);
      this.$message.success("更新成功");
    },
    showFood(id){

    },
    async listArticles(id) {
      this.orderId=id;
      // const id=this.$route.params.id
      if(id==null){
        this.loading = false;
        return;
      }
      //获取订单状态
      const res2 = await getOrderStatusByOrderId(id);
      this.orderStatus=res2.data.orderStatus;
      this.payStatus=res2.data.payStatus;
      this.remarks=res2.data.remarks;
      //获取食物列表
      const res = await getOrderFoodByOrderId(id);
      console.log(res);
      this.formData=res.data;
      console.log(this.formData)
      this.loading = false;

    },
  },
  watch: {


  },
  computed: {
    articleType() {
      return function(type) {
        var tagType = "";
        var name = "";
        switch (type) {
          case 1:
            tagType = "danger";
            name = "原创";
            break;
          case 2:
            tagType = "success";
            name = "转载";
            break;
          case 3:
            tagType = "primary";
            name = "翻译";
            break;
        }
        return {
          tagType: tagType,
          name: name
        };
      };
    },
    isActive() {
      return function(status) {
        return this.activeStatus == status ? "active-status" : "status";
      };
    }
  }
};
</script>

<style scoped>
.operation-container {
  margin-top: 1.5rem;
}
.article-status-menu {
  font-size: 14px;
  margin-top: 40px;
  color: #999;
}
.article-status-menu span {
  margin-right: 24px;
}
.status {
  cursor: pointer;
}
.active-status {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
.article-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.article-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.article-status-icon {
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
  bottom: 1.4rem;
}
</style>
