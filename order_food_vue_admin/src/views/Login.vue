<template>
    <div class="login_container">
      <!-- banner -->
      <div class="archive-banner banner">
        <h1 class="banner-title">登录</h1>
      </div>
      <div class="login_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
      </div>
        <!-- 登录表单区域 -->
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
          <!-- 用户名 -->
          <el-form-item prop="userName">
            <el-input v-model="loginForm.userName" placeholder="请输入用户名" prefix-icon="el-icon-user-solid"></el-input>
          </el-form-item>
          <!-- 密码 -->
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" placeholder="请输入密码" prefix-icon="el-icon-moon-night" type="password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-input v-model="loginForm.code" placeholder="请输入验证码" prefix-icon="el-icon-picture-outline-round" style="width: 50%"></el-input>
            <img v-bind:src="verifyCode" @click="getVerifyCode()" width="130px" height="35px" style="float: right;cursor:pointer;" />
          </el-form-item>
          <!-- 按钮区域 -->
          <el-form-item class="btns">
            <el-button type="success" @click="handleCreate">注册</el-button>
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="info" @click="resetPassword">忘记密码</el-button>
          </el-form-item>
          <div></div>
<!--          <div class="social-login-title">社交账号登录</div>-->
<!--          <div class="social-login-wrapper" style="cursor: pointer">-->
<!--            &lt;!&ndash; 微博登录 &ndash;&gt;-->
<!--            <a-->
<!--              v-if="showLogin('weibo')"-->
<!--              class="mr-3 iconfont iconweibo"-->
<!--              style="color:#e05244"-->
<!--              @click="weiboLogin"-->
<!--            />-->
<!--            &lt;!&ndash; qq登录 &ndash;&gt;-->
<!--            <a-->
<!--              v-if="showLogin('qq')"-->
<!--              class="iconfont iconqq"-->
<!--              style="color:#00AAEE; margin-left: 8px"-->
<!--              @click="qqLogin"-->
<!--            />-->
<!--            <i class="smile outline icon" @click="faceLogin" style="color:#ae81ff; margin-left: 8px"></i>-->
<!--          </div>-->
        </el-form>
      </div>
      <!-- 注册弹层-->
      <div class="add-form">
        <el-dialog title="注册用户" :visible.sync="dialogFormVisible">
          <el-form ref="registForm" :model="formData" :rules="loginFormRules" label-position="right"
                   label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="用户名" prop="userName">
                  <el-input label="请输入用户名" v-model="formData.userName"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="密码" prop="password">
                  <el-input v-model="formData.password"/>
                </el-form-item>
              </el-col>
            </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="昵称" prop="nickName">
                <el-input v-model="formData.nickName"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="formData.email"/>
              </el-form-item>
            </el-col>
          </el-row>
            <el-col :span="24">
              <el-form-item label="头像地址">
                <el-input v-model="formData.avatar"/>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="or选择本地图片当头像">
              <el-upload
                class="avatar-uploader"
                action="serverApi/file/userAvatar/"
                accept="image/png,.jpg"
                multiple
                :limit="2"
                :on-exceed="masterFileMax"
                :show-file-list="false"
                :http-request="uploadPic"
                :on-change="onChange"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              </el-form-item>
            </el-col>
          </el-form>
          <div slot="footer" class="dialog-footer">
<!--            <el-button type="success" @click="faceRegister">人脸注册</el-button>-->
            <el-button @click="dialogFormVisible=false">取消</el-button>
            <el-button type="primary" @click="register()">确定</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
</template>

<script>
// import { generaMenu } from '../assets/js/menu'
// import {decrypt, encrypt} from "@/utils/jsencrypt";
import {getVerifyCode, login, register} from "@/api/login";

const {uploaduserAvatar} = require("@/api/login");
export default {
  data () {
    return {
      imageUrl: '',
      verifyCode: '',
      formData: {
        userName: '',
        password: '',
        nickName: '',
        email: '',
        avatar: '头像参考地址：https://picsum.photos/images，将右边链接image后的id换成自己的即可（https://unsplash.it/100/100?image=1）'
      },
      dialogFormVisible: false, // 增加表单是否可见
      // 这是登录表单的数据绑定对象
      loginForm: {
        userName: '',
        password: '',
        verKey: '',
        code: ''
      },
      // 这是表单的验证规则对象
      loginFormRules: {
        // 验证用户名是否合法
        userName: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        // 验证码
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        email: [{ required: true, message: '请填写电子邮箱', trigger: 'change' }]
      }
    }
  },
  created () {
    this.getVerifyCode()
  },
  methods: {
    onChange(file, fileList) {
      console.log(file,fileList)
      if (fileList.length > 1) {
        fileList.splice(0, 1);
      }
    },
    login () {//或者.then也行 await+async解决回调地狱
       this.$refs.loginFormRef.validate(  valid => {
         //表单验证失败
        if (!valid) return
         //加密
        const param = this.loginForm
         //或者.then也行 await+async解决回调地狱
         this.$store.dispatch("user/login",param).then(()=>{
             this.$message.success('登录成功')
             this.$router.push("/")

         }).catch(()=>{
            this.getVerifyCode()
         })
      })
    },
    socialLoginList () {
      return this.$store.state.blogInfo.websiteConfig.socialLoginList;
    },
    showLogin () {
      return function (type) {
        return this.socialLoginList.indexOf(type) !== -1;
      };
    },
    resetPassword () {
      this.$store.state.forgetFlag = true;
    },
    // eslint-disable-next-line no-unused-vars
    masterFileMax (files, fileList) {
      this.$message.warning('请最多上传一张图片')
    },
    uploadPic (param) {
      const fileObj = param.file
      const form = new FormData()
      // 文件对象
      form.append('file', fileObj)
      uploaduserAvatar(form).then(res=>{
        this.$message.success('上传头像成功')
        this.formData.avatar = res.data.url
      })
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt5M = file.size / 1024 / 1024 < 5

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过5MB!')
      }
      return isJPG && isLt5M
    },
    getVerifyCode() {
      getVerifyCode().then((res)=>{
        this.verifyCode = res.data.verCode
        //redis验证码的密钥
        this.loginForm.verKey = res.data.verKey
      })
    },
    // 点击重置按钮，重置登录表单
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    // 弹出添加窗口
    handleCreate () {
      this.dialogFormVisible = true
    },
    register () {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      // eslint-disable-next-line eqeqeq
      if (this.formData.email != '' && !regEmail.test(this.formData.email)) {
        this.$message({
          message: '邮箱格式不正确',
          type: 'error'
        })
        this.formData.email = ''
        return false
      }
      // 进行表单校验
      this.$refs.registForm.validate((valid) => {
        if (valid) {
          // 表单校验通过，发ajax请求，把数据录入至后台处理
          // const param = this.$encrypTion(JSON.stringify(this.formData))
          register(this.formData).then(()=>{
            this.$message({
              message: '注册成功，快来登录吧！',
              type: 'success'
            })
            this.dialogFormVisible = false
          })

        } else { // 校验不通过
          this.$message.error('校验失败，请检查输入格式')
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.banner-title {
  animation: title-scale 1s;
  position: absolute;
  top: 25rem;
  padding: 0 0.5rem;
  width: 100%;
  font-size: 2.5rem;
  text-align: center;
  color: #eee;
}
.archive-banner {
  height: 110vh;
  background: #49b1f5 url(https://r.photo.store.qq.com/psc?/V53KcXfb1umonn4HbITu3rINxs43TczD/45NBuzDIW489QBoVep5mccYPEGHYJF8vf05Y7Jp3Sq4PYCDwfPyvkq4c5VlhffPJbHw4QoE1dsiS8OtN2H5XvhPtg1C1JZwAOMeqYFSoGDg!/r) no-repeat center;
}
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 410px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}
.avatar-uploader .el-upload {
  border: 5px dashed #d9d9d9;
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
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 60px;
  height: 60px;
  display: block;
}
</style>
