<template>
	<view class="container d-flex flex-column">
		<view class="flex-fill form">
			<list-cell :hover="false">
				<view class="form-input w-100 d-flex align-items-center">
					<view class="label">头像</view>
					<view class="input flex-fill">
						<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
						  <image class="avatar" :src="user.avatar"></image>
						</button>
					</view>
				</view>			
			</list-cell>

			<list-cell :hover="false">					
				<view class="form-input w-100 d-flex align-items-center">
					<view class="label">昵称</view>
					<view class="input flex-fill">
						<input type="nickname" placeholder="请输入昵称"  
						v-model="user.nickName">
					</view>
				</view>
			</list-cell>
			<list-cell :hover="false">
				<view class="form-input w-100 d-flex align-items-center">
					<view class="label">手机号码</view>
					<view class="input flex-fill">
						<input type="text" v-model="user.phoneNumber">
					</view>
				</view>
			</list-cell>
			<list-cell :hover="false">
				<view class="form-input w-100 d-flex align-items-center">
					<view class="label">性别</view>
					<view class="input flex-fill">
						<view class="radio-group">
							<view class="radio" :class="{'checked': user.sex === 1}" style="margin-right: 10rpx;" @tap="user.sex=1">先生</view>
							<view class="radio" :class="{'checked': user.sex == 2}" @tap="user.sex=2">女士</view>
						</view>
					</view>
				</view>
			</list-cell>
		</view>
		<view class="btn-box d-flex align-items-center just-content-center">
			<button type="primary" class="save-btn" @tap="save">保存</button>
		</view>
	</view>
</template>

<script>
	import listCell from '@/components/list-cell/list-cell'
	import {mapState,mapMutations} from 'vuex'
	const {updateUserById,uploadAvatar} = require("@/api/api/me.js")
	export default {
		components: {
			listCell
		},
		data() {
			return {
				user:{}
			}
		},
		computed: {
		
		 
		},
		onLoad() {		
			this.user = JSON.parse(JSON.stringify(this.$store.state.user.user))
			
			console.log(this.user)
			
		},
		methods: {
			...mapMutations('user',['SET_USER']),
			 onChooseAvatar(e) {
				 console.log(e)
				const { avatarUrl } = e.detail 
				
				this.user.avatar=avatarUrl
				// 执行图片转base64方法
				    this.base64(avatarUrl, "png").then(res => {
				      console.log(res, 'base64路径') //res是base64路径
				      this.setData({
				        'form.base64': res
				      })
				    })
				    console.log(avatarUrl, '1');
			  },
		
			save() {
				console.log(this.user)
				updateUserById(this.user).then(res=>{
					uni.showToast({
									title: res.msg,
									icon:'success',
									duration:850
								});
					this.SET_USER(this.user);
					uni.navigateBack()
				})
				
			},
			// 图片转64
			  base64(url, type) {
			    return new Promise((resolve, reject) => {
			      wx.getFileSystemManager().readFile({
			        filePath: url, //选择图片返回的相对路径
			        encoding: 'base64', //编码格式
			        success: res => {
			          // resolve('data:image/' + type.toLocaleLowerCase() + ';base64,' + res.data)
			          resolve(res.data)
			        },
			        fail: res => reject(res.errMsg)
			      })
			    })
			  }
		}
	}
</script>

<style lang="scss" scoped>
page {
	height: 100%;
}

.container {
	padding: 20rpx 30rpx;
}

.form {
	border-radius: 8rpx;
}

.form-input {
	.label {
		width: 160rpx;
		font-size: $font-size-base;
		color: $text-color-base;
	}
	
	.input {
	}
	
	.radio-group {
		display: flex;
		justify-content: flex-start;
		
		.radio {
			padding: 10rpx 30rpx;
			border-radius: 6rpx;
			border: 2rpx solid $text-color-assist;
			color: $text-color-assist;
			font-size: $font-size-base;
			
			&.checked {
				background-color: $color-primary;
				color: $text-color-white;
				border: 2rpx solid $color-primary;
			}
		}
	}
}

.btn-box {
	height: calc((100vh - 40rpx) / 2);
}

.save-btn {
	width: 90%;
	border-radius: 50rem !important;
	font-size: $font-size-lg;
}


.avatar-wrapper{
	padding: 0;
	margin: 0;
	width: 160rpx;
	height: 160rpx;
	display: flex;
	justify-content: center;
	align-items: center;
	outline: none;
	border:none;
	.avatar{
		
		max-width: 100%;
		max-height: 100%;
		object-fit: cover;
	}
}



</style>
