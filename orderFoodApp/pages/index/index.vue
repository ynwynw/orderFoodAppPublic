<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{title}}</text>
		</view>
		<button style="width: 300rpx;height: 300rpx;" class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
		  <image  class="avatar" :src="avatarUrl"></image>
		</button> 
		<input type="nickname" class="weui-input" placeholder="请输入昵称"/>
		
	</view>
</template>

<script>
	const { wxLogin } = require("@/api/api/me.js")
	const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

	export default {
		data() {
			return {
				title: 'Hello',
				 avatarUrl: defaultAvatarUrl,
			}
		},
		onLoad() {

		},
		methods: {
			onChooseAvatar(e) {
				console.log(e)
			    const { avatarUrl } = e.detail 
				this.avatarUrl=e.detail
			  },
			login(){
				wx.login({
				  success (res) {
				    if (res.code) {
					
				      //发起网络请求
					console.log(res)
					const param = {
						code:res.code
					}
					wxLogin(param).then(res=>{
						console.log(res)
					})
					
					  
				    
				    } else {
				      console.log('登录失败！' + res.errMsg)
				    }
				  }
				})
			}

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
