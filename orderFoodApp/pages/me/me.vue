<template>
	<view class="container">		
		<view style="height: 50vh;">
			<list-cell>
				<view class="d-flex align-items-center">
					<view class="avatar" style="margin-top: 20rpx;">
						<image :src="isLogin ? user.avatar : '/static/images/mine/default.png'"></image>
					</view>
					<view class="d-flex flex-column flex-fill overflow-hidden" style="margin-top: 20rpx;">
						<view v-if="isLogin" class="font-size-lg font-weight-bold d-flex justify-content-start align-items-center "
							@tap="userinfo">
							<view class="text-truncate">{{ user.nickname }}</view>
							<view class="iconfont iconarrow-right"></view>
						</view>
						<view v-else class="font-size-lg font-weight-bold" @tap="login">请点击授权登录</view>			
					</view>				
				</view>				
			</list-cell>
			<view class="d-flex flex-column font-weight-bold" style="margin: 20px;">
				<view>
					<list-cell>
						<view class="pay-cell" @click="gotoOrders()">
							<view>
								<view>订单列表</view>
							</view>
							<view class="iconfont iconarrow-right font-weight-bold"></view>
						</view>
					</list-cell>
				</view>
				<view style="margin-top: 20rpx;margin-bottom: 20rpx;">
					<list-cell>
						<view class="pay-cell">
							<view>投诉与建议</view>
							<view class="iconfont iconarrow-right font-weight-bold"></view>
						</view>
					</list-cell>
				</view>
			</view>
			
		</view>
		<view class="d-flex flex-column " style="margin: 20px; height: 50vh;">
			<list-cell>
				<button type="primary" size="default" class="login-btn"  lang="zh_CN" @click="scanCode">
					<image src="/static/sm.png"></image>
					扫码点餐
				</button>
			</list-cell>
		</view>
		
		
		
		
	</view>
</template>

<script>
	import {mapState, mapGetters,mapMutations} from 'vuex'
	const { wxLogin } = require("@/api/api/me.js")
	import listCell from '@/components/list-cell/list-cell'
	import UniIcons from "@/components/uni-icons/uni-icons"
	export default {
		component:{
			listCell
		},
		data() {
			
			return {
				newIcon: 'https://img-shop.qmimg.cn/s16/images/2020/05/12/ffb0613dded704b6.png'
			}
		},
		computed: {
			...mapState('user',['user']),
			...mapGetters('user',['isLogin']),
			growthValue() {
				if(!this.isLogin) return 0
				const {currentValue, needValue} = this.user
				return currentValue / (currentValue + needValue) * 100
			}
		},
		onLoad() {
			
			console.log(this.isLogin)

		},
		methods: {
			...mapMutations(['SET_SEAT']),
			gotoOrders(){
				uni.navigateTo({
					url:"/pages/orders/orders"
				})
			},
			scanCode(){
				// 只允许通过相机扫码
				this.SET_SEAT({
					seatId:1,
					seatNum:1
				})
				
				if(!this.isLogin){
					this.login();
					return;
				}
					
				uni.redirectTo({
					url: '/pages/menu/menu'
				})
				// uni.scanCode({
				// 	onlyFromCamera: true,
				// 	scanType:["qrCode"],
				// 	success: function (res) {
				// 		console.log('条码类型：' + res.scanType);
				// 		console.log('条码内容：' + res.result);
				// 	}
				// });
			},
			login() {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			},
			packages() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/packages/index'
				})
			},
			balance() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/balance/balance'
				})
			},
			addresses() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/address/address'
				})
			},
			integrals() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/integrals/integrals'
				})
			},
			attendance() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/attendance/attendance'
				})
			},
			orders() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/orders/orders'
				})
			},
			memberCode() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/me/member-code'
				})
			},
			coupons() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/coupons/coupons'
				})
			},
			userinfo() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/me/userinfo'
				})
			},
			services() {
				uni.navigateTo({
					url: '/pages/services/services'
				})
			},
			giftCards() {
				if(!this.isLogin) {
					this.login()
					return
				}
				uni.navigateTo({
					url: '/pages/giftcard/giftcard'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
page {
	height: auto;
	min-height: 100%;
}	
.login-btn {
	margin-top: 100rpx;
	margin-bottom: 100rpx;
	margin-left: 20rpx;
	margin-right: 20rpx;
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 10rpx 0;
	background-color: #c8c8c8;
	image {
		width: 50rpx;
		height: 50rpx;
		margin-right: 15rpx;
	}
}
	
	
.pay-cell {
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: space-between;
	font-size: $font-size-base;
	color: $text-color-base;
	margin-bottom: 40rpx;

	&:nth-last-child(1) {
		margin-bottom: 0;
	}
}

.bg {
	width: 100%;
	height: calc(410 / 594 * 750rpx);
}

.hym-btn {
	position: absolute;
	top: 40rpx;
	right: 40rpx;
	color: $color-primary;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 50rem;
	font-size: $font-size-sm;
	box-shadow: 0 0 20rpx rgba(66,66,66,0.1);
	&::after {
		border: 0;
	}
	
	image {
		width: 30rpx;
		height: 30rpx;
		margin-right: 10rpx;
	}
}

.user-box {
	position: relative;
	border-radius: 8rpx; 
	margin-bottom: 30rpx;
	margin-top: -115rpx;
	box-shadow: $box-shadow;
}

.avatar {
	position: relative;
	margin-top: -35rpx;
	margin-left: 35rpx;
	margin-right: 35rpx;
	width: 160rpx;
	height: 160rpx;
	border-radius: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #FFFFFF;
	box-shadow: 0 0 20rpx rgba($color: #000000, $alpha: 0.2);
	
	image {
		width: 140rpx;
		height: 140rpx;
		border-radius: 100%;
	}
	
	.badge {
		position: absolute;
		right: -10rpx;
		bottom: -10rpx;
		background-color: #FFFFFF;
		border-radius: 50rem;
		display: flex;
		align-items: center;
		justify-content: center;
		color: $color-warning;
		font-size: 24rpx;
		padding: 8rpx 16rpx;
		box-shadow: 0 0 20rpx rgba($color: #000000, $alpha: 0.2);
		
		image {
			width: 30rpx;
			height: 30rpx;
		}
	}
}

.level-benefit {
	margin-left: 35rpx;
	padding: 10rpx 0 10rpx 30rpx;
	border-radius: 50rem 0 0 50rem;
}

.user-grid {
	width: 25%;
	padding: 30rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	
	.value {
		margin-bottom: 20rpx;
	}
}

.level-benefit-box {
	border-radius: 8rpx; 
	margin-bottom: 30rpx;
	box-shadow: 0 10rpx 8rpx rgba($color: #878889, $alpha: 0.1);
	width: 100%;
	display: flex;
	padding: 30rpx;
	flex-direction: column;
	background-color: #FFFFFF;
	
	.row {
		display: flex;
		padding: 30rpx 0 20rpx;
		justify-content: space-around;
		align-items: center;
		
		.grid {
			width: 20%;
			display: flex;
			flex-direction: column;
			font-size: $font-size-sm;
			color: $text-color-assist;
			align-items: center;

			image {
				width: 80rpx;
				height: 80rpx;
				margin-bottom: 10rpx;
			}
		}
	}
}

.banner {
	width: 100%;
	border-radius: 8rpx;
	margin-bottom: 30rpx;
}

.service-box {
	width: 100%;
	background-color: #FFFFFF;
	padding: 32rpx 30rpx 10rpx;
	box-shadow: $box-shadow;
	
	.row {
		display: flex;
		flex-wrap: wrap;
		color: $text-color-assist;
		font-size: $font-size-sm;
		padding-bottom: -40rpx;
		
		.grid {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			margin-bottom: 40rpx;
			width: 25%;
			position: relative;
			
			.image {
				image {
					width: 80rpx;
					height: 80rpx;
					margin-bottom: 20rpx;
				}
			}
			
			.new-badage {
				width: 40rpx;
				height: 40rpx;
				position: absolute;
				top: 0;
				right: 30rpx;
			}
		}
	}
}
</style>
