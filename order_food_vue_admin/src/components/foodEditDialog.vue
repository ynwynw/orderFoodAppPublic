<template>
  <div>
    <el-dialog :visible="this.foodEditDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="菜品编辑">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row type="flex" justify="start" align="top">
          <el-form-item label="菜品名" prop="name">
            <el-input v-model="formData.name" placeholder="请输入菜品名" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
          <el-form-item label="分类" prop="type" >
            <el-select v-model="formData.typeId" placeholder="请选择分类" clearable :style="{width: '100%'}">
              <el-option v-for="(item, index) in typeList" :key="index"
                         :label="item.name"
                         :value="item.id" >

              </el-option>
            </el-select>
          </el-form-item>

        </el-row>

        <el-row type="flex" justify="start" align="top">
          <el-form-item label="价格/元" prop="price">
            <el-input v-model="formData.price" placeholder="请输入价格" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
          <el-form-item label="包装费/元" prop="packagingFee">
            <el-input v-model="formData.packagingFee" placeholder="请输入包装费" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="start" align="top">
          <el-form-item label="剩余数量" prop="inventory">
            <el-input-number v-model="formData.inventory" placeholder="剩余数量"></el-input-number>
          </el-form-item>
          <el-form-item label="单位" prop="unit">
            <el-input v-model="formData.unit" placeholder="请输入单位" clearable :style="{width: '100%'}"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="start" align="top">
          <el-form-item label="最小购买数量" prop="minBuyNum">
            <el-input-number v-model="formData.minBuyNum" placeholder="最小购买数量"></el-input-number>
          </el-form-item>
          <el-form-item label="食物小料" prop="foodMinVOS">
            <!-- 标签选项 -->
            <el-popover
                placement="bottom-start"
                width="210"
                trigger="click"
            >
              <!-- 标签 -->
              <div class="popover-container" style="display: flex">
                <el-select v-model="foodMinIdList" collapse-tags multiple placeholder="请选择小料"  style="width: 200px">
                  <el-option
                      v-for="item in foodMinList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      @change="handleIsopenSelect">
                  </el-option>
                </el-select>
                <el-button size="small" @click="handleAllselect">
                  全选
                </el-button>
              </div>
              <el-button type="primary" plain slot="reference" size="small">
                添加小料
              </el-button>
            </el-popover>
          </el-form-item>

        </el-row>

        <el-row type="flex" justify="start" align="top">
          <el-form-item label="开启扩展标签" prop="unit">
            <el-switch
                v-model="formData.useProperty"
                active-color="#13ce66"
                inactive-color="#ff4949">
            </el-switch>
          </el-form-item>

        </el-row>

       <el-form-item label="封面上传" prop="pricutre" required>
          <el-upload
              class="avatar-uploader"
              :http-request="uploadPic"
              :show-file-list="false"
              :before-upload="beforeAvatarUpload">
            <img v-if="formData.pricutre" :src="formData.pricutre" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="封面地址" prop="pricutre" required>
          <el-input v-model="formData.pricutre" placeholder="封面地址"  :style="{width: '100%'}"/>
        </el-form-item>
        <el-form-item label="食物图片上传">
          <el-upload
              class="upload-demo"
              :http-request="uploadPic2"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="3"
              :on-exceed="handleExceed"
              :file-list="formData.foodPricutres">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="描述" prop="remarks">
          <el-input v-model="formData.remarks" type="textarea" placeholder="请输入描述"
                    :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {mapState} from "vuex";
import {number} from "echarts";
import {getFoodById,getTypeList,getTagList} from "@/api/food";

const {saveOrUpdateFood, uploadFoodAvatar, getFoodMinList,uploadFoodPricutre} = require("@/api/food");

export default {
  inheritAttrs: false,
  components: {},
  props: {
    id:{
      type: Number,
      default: null
    }
  },
  data() {
    return {
      formData:{
        type:{id:"",name:""},
        tags:[{id:"",name:""}]
      },
      rules: {
        name: [{
          required: true,
          message: '请输入菜品名',
          trigger: 'blur'
        }],
        price: [{
          required: true,
          message: '请输入价格',
          trigger: 'blur'
        }],
        inventory: [{
          required: true,
          message: '剩余数量',
          trigger: 'blur'
        }],
        type: [{
          required: true,
          message: '请选择分类',
          trigger: 'change'
        }],
        remarks: [{
          required: true,
          message: '请输入描述',
          trigger: 'blur'
        }],
      },
      pricutreAction: 'https://jsonplaceholder.typicode.com/posts/',
      pricutrefileList: [],
      typeOptions: [],
      tagList:[],
      tagIdList:[],
      foodMinIdList:[],
      typeList:[],
      fileList: [],
      foodMinList:[],

    }
  },
  computed: {
    ...mapState({
      foodEditDialog: state=>state.food.foodEditDialog,
    })
  },
  watch: {},
  created() {

  },
  mounted() {

  },
  methods: {
    async getFoodById() {
      const res = await getFoodById(this.id);
      console.log(res.data)
      this.foodMinIdList=[]
      res.data.foodMinVOS.forEach(item=>{
        this.foodMinIdList.push(item.id)
      })
      this.formData = res.data
    },
    async getTypeList() {
      const res = await getTypeList();
      this.typeList=res.data
    },
    async getFoodMinList() {
      const res = await getFoodMinList();
      this.foodMinList = res.data
    },
    onOpen() {
      if(this.id!=null){
        this.getFoodById();
      }
      this.getTypeList();
      this.getFoodMinList();
    },
    onClose() {
     this.$refs['elForm'].resetFields()
      //关闭弹窗
      this.$store.commit("food/CloseEditDialog")
      //刷新页面
      this.$emit("listArticles");
    },
    close() {
      this.$emit('update:visible', false)

      //关闭弹窗
      this.$store.commit("food/CloseEditDialog")
      //刷新页面
      this.$emit("listArticles");
    },
    handleAllselect(){
      if(this.foodMinIdList.length!=this.foodMinList.length){
        //全选
        this.foodMinIdList=[]
        this.foodMinList.forEach(item=>{
          this.foodMinIdList.push(item.id);
        })
      }else{
        //全不选
        this.foodMinIdList=[]
      }

    },
    handleConfirm() {
      this.$refs['elForm'].validate(async valid => {
        if (!valid) return
        this.formData.foodMinVOS=[];
        this.foodMinIdList.forEach(item=>{
          this.formData.foodMinVOS.push({
            id:item
          })
        })
        const res = await saveOrUpdateFood(this.formData);
        this.$message.success(res.msg)
        this.close()
      })
    },
    handleIsopenSelect(){
      this.$forceUpdate()
    },

    handleAvatarSuccess(res, file) {
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    uploadPic (param) {
      const fileObj = param.file
      const form = new FormData()
      // 文件对象
      form.append('file', fileObj)
      uploadFoodPricutre(form).then(res=>{
        this.$message.success('上传封面成功')
        //在这里搞 或者在上传成功后的钩子搞都行
        this.formData.pricutre = res.data.url
      })
    },
    uploadPic2(param){
      const fileObj = param.file
      const form = new FormData()
      // 文件对象
      form.append('file', fileObj)
      uploadFoodPricutre(form).then(res=>{
        this.$message.success('上传成功')
        this.formData.foodPricutres.push({
          foodId:this.id,
          name:fileObj.name,
          url:res.data.url
        })
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file) {
      for(let i=0;i<this.formData.foodPricutres.length;i++){
        if(this.formData.foodPricutres[i].name==file.name){
          this.formData.foodPricutres.splice(i,1);
        }
      }
    }

  }
}

</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}



</style>
