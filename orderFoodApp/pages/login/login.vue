<template>
	<view class="container">
		<view class="intro">
			<image src="/static/images/logo.png"></image>
		</view>
		<view class="bottom">
				<!-- <button type="primary" size="default" class="login-btn" open-type="getUserInfo" lang="zh_CN" @getuserinfo="getUserInfo">
					<image src="/static/images/mine/wechat.png"></image>
					微信一键登录
				</button> -->
				<button type="primary" size="default" class="login-btn"  lang="zh_CN" @click="getUserInfo">
					<image src="/static/images/mine/wechat.png"></image>
					一键登录
				</button>
			</view>
	</view>
</template>

<script>
	import Member from '@/api/member'
	import {mapMutations} from 'vuex'
	const { wxLogin } = require("@/api/api/login.js")
	export default {
		data() {
			return {
				
			}
		},
		methods: {
		
			...mapMutations('user',['SET_TOKEN','SET_USER']),
			login(){
				wx.login({
				 
				}).then(res=>{
					if (res.code) {
					  //发起网络请求
					console.log(res)
					const param = {
						code:res.code
					}
					wxLogin(param).then(res=>{
						console.log(res)
						// this.$store.commit("user/SET_TOKEN",res.token);
						this.SET_TOKEN(res.data.token);
						this.SET_USER(res.data.user);
						uni.navigateBack()
						
					})			    
					} else {
					  console.log('登录失败！' + res.errMsg)
					}
				})
				
			},
			async getUserInfo() {
				this.login();
				// const {errMsg, userInfo} = e.detail
				// if(errMsg !== "getUserInfo:ok") {
				// 	uni.showModal({
				// 		title: '提示',
				// 		content: '您取消了授权登录，请重新授权',
				// 		showCancel: false
				// 	})
					
				// 	//没有授权登录就用默认的用户信息
				// 	this.SET_MEMBER(Member)
				// 	uni.navigateBack()
					
				// 	return
				// } else {
				// 	console.log(userInfo)
				// 	const {avatarUrl: avatar, city, country, gender, nickName: nickname, province} = userInfo
					// const member = Object.assign(Member, {avatar, city, country, gender, nickname, province})
				// 	this.SET_MEMBER(member)
					
				// 	uni.navigateBack()
				// }
				
			
				
				
			},
			// login() {
			// 	this.SET_MEMBER(Member)
			// 	uni.navigateBack()
			// }
		}
	}
</script>

<style lang="scss" scoped>
	.intro {
		width: 100%;
		height: 60vh;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-evenly;
		font-size: $font-size-base;
		color: $text-color-assist;
		
		image {
			width: 165rpx;
			height: 165rpx;
		}
		
		.tips {
			line-height: 72rpx;
			text-align: center;
		}
	}
	
	.bottom {
		height: 40vh;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		padding: 0 40rpx;
		
		.login-btn {
			width: 100%;
			border-radius: 50rem !important;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 10rpx 0;
			
			image {
				width: 36rpx;
				height: 30rpx;
				margin-right: 10rpx;
			}
		}
		
		.row {
			.grid {
				width: 20%;
				image {
					width: 60rpx;
					height: 60rpx;
					margin-bottom: 10rpx;
				}
			}
		}
	}
</style>
