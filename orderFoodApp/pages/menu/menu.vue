<template>
	<view class="container" v-if="!loading">
		<view class="main" v-if="goods.length">
			<view class="nav">
				<view class="header">
					<view class="left">
						<view class="store-name">
							<text>桌号：{{seat.seatNum }}</text>
							<view class="iconfont iconarrow-right"></view>
						</view>
					</view>
					<view class="right">
						<view class="dinein" :class="{active: type == 1}" @click="goMenu()">
							<text>点餐</text>
						</view>
						<view class="takeout" :class="{active: type == 2}" @click="goOrder()">
							<text>订单</text>
						</view>
					</view>
				</view>
				<view class="coupon">
					<text class="title">"这是一条广告"</text>
					<view class="iconfont iconarrow-right"></view>
				</view>
			</view>
			<view class="content">
				<scroll-view class="menus" :scroll-into-view="menuScrollIntoView" scroll-with-animation scroll-y>
					<view class="wrapper">
						<view class="menu" :id="`menu-${item.id}`" :class="{'current': item.id === currentCateId}"
							v-for="(item, index) in goods" :key="index" @tap="handleMenuTap(item.id)">
							<text>{{ item.name }}</text>
							<!-- 分类选择的食品数 -->
							<view class="dot" v-show="menuCartNum(item.id)">{{ menuCartNum(item.id) }}</view>
						</view>
					</view>
				</scroll-view>
				<!-- goods list begin 解决-->
				<scroll-view class="goods" scroll-with-animation scroll-y :scroll-top="cateScrollTop"
					@scroll="handleGoodsScroll">
					<view class="wrapper">
						<!-- 轮播图 -->
						<swiper class="ads" id="ads" autoplay :interval="3000" indicator-dots>
							<swiper-item v-for="(item, index) in ads" :key='index'>
								<image :src="item.image"></image>
							</swiper-item>
						</swiper>
						<view class="list">
							<!-- category begin -->
							<view class="category" v-for="(item, index) in goods" :key="index" :id="`cate-${item.id}`">
								<!-- 分类标题 -->
								<view class="title">
									<text>{{ item.name }}</text>
									<image :src="item.icon" class="icon"></image>
								</view>
								<!-- 遍历分类下的食物 -->
								<view class="items">
									<!-- 商品 begin -->
									<view class="good" v-for="(good, key) in item.goods_list" :key="key">
										<image :src="good.pricutre" class="image" @tap="showGoodDetailModal(item, good)">
										</image>
										<view class="right">
											<!-- 名称 -->
											<text class="name">{{ good.name }}</text>
											<!-- 简介 -->
											<text class="tips">{{ good.content }}</text>
											<view class="price_and_action">
												<!-- 价格 -->
												<text class="price">￥{{ good.price }}</text>
												<view class="btn-group" v-if="good.useProperty">
													<button type="primary" class="btn property_btn" hover-class="none"
														size="mini" @tap="showGoodDetailModal(item, good)">
														选规格
													</button>
													<!-- 购物车当前此食物数量 -->
													<view class="dot" v-show="goodCartNum(good.id)">
														{{ goodCartNum(good.id) }}</view>
												</view>
												<!-- 加入购物车（- +） -->
												<view class="btn-group" v-else>
													<button type="default" v-show="goodCartNum(good.id)" plain
														class="btn reduce_btn" size="mini" hover-class="none"
														@tap="handleReduceFromCart(item, good)">
														<view class="iconfont iconsami-select"></view>
													</button>
													<view class="number" v-show="goodCartNum(good.id)">
														{{ goodCartNum(good.id) }}</view>
													<button type="primary" class="btn add_btn" size="min"
														hover-class="none" @tap="handleAddToCart(item, good, 1)">
														<view class="iconfont iconadd-select"></view>
													</button>
												</view>
											</view>
										</view>
									</view>
									<!-- 商品 end -->
								</view>
							</view>
							<!-- category end -->
						</view>
					</view>
				</scroll-view>
				<!-- goods list end -->
			</view>
			<!-- content end -->
			<!-- 购物车栏 begin -->
			<view class="cart-box" v-if="cart.length > 0">
				<view class="mark">
					<image src="/static/images/menu/cart.png" class="cart-img" @tap="openCartPopup"></image>
					<view class="tag">{{ getCartGoodsNumber }}</view>
				</view>
				<view class="price">￥{{ getCartGoodsPrice }}</view>
				<button type="primary" class="pay-btn" @tap="toPay" :disabled="disabledPay">
					{{ disabledPay ? `差${spread}元起送` : '去结算' }}
				</button>
			</view>
			<!-- 购物车栏 end -->
		</view>
		<!-- 商品详情模态框 begin -->
		<modal :show="goodDetailModalVisible" class="good-detail-modal" color="#5A5B5C" width="90%" custom
			padding="0rpx" radius="12rpx">
			<!-- 不重要1 begin -->
			<view class="cover">
				<image v-if="good.pricutre" :src="good.pricutre" class="image"></image>
				<view class="btn-group">
					<image src="/static/images/menu/share-good.png"></image>
					<image src="/static/images/menu/close.png" @tap="closeGoodDetailModal"></image>
				</view>
			</view>
			<!-- 不重要1end -->
			<scroll-view class="detail" scroll-y>
				<!-- 详情食物图片与简介 start-->
				<view class="wrapper">
					<view class="basic">
						<view class="name">{{ good.name }}</view>
						<view class="tips">{{ good.content }}</view>
					</view>
					<!--详情食物图片与简介 end -->
					<view class="properties" v-if="good.useProperty">
						<view class="property" v-for="(item, index) in good.property" :key="index">
							<view class="title">
								<text class="name">{{ item.name }}</text>
								<!-- !! begin 父标签描述 解决-->
								<view class="desc" v-if="item.desc">({{ item.desc }})</view>
								<!-- !! end -->
								<!-- 遍历标签孩子结点start -->
							</view>
							<view class="values">
								<view class="value" v-for="(value, key) in item.children" :key="key"
									:class="{'default': value.isDefault}" @tap="changePropertyDefault(index, key)">
									{{ value.name }}
								</view>
							</view>
							<!--  遍历标签孩子结点end-->
						</view>
					</view>
				</view>
			</scroll-view>
			<view class="action">
				<view class="left">
					<view class="price">￥{{ good.price }}</view>
					<!-- 食物描述!!start  解决-->
					<view class="props" v-if="getGoodSelectedProps(good)">
						{{ getGoodSelectedProps(good) }}
					</view> 
					<!--食物描述 !!end -->
				</view>
				<!-- 加减食物数量 start -->
				<view class="btn-group">
					<button type="default" plain class="btn" size="mini" hover-class="none" @tap="handlePropertyReduce">
						<view class="iconfont iconsami-select"></view>
					</button>
					<view class="number">{{ good.number }}</view>
					<button type="primary" class="btn" size="min" hover-class="none" @tap="handlePropertyAdd">
						<view class="iconfont iconadd-select"></view>
					</button>
				</view>
				<!-- 加减食物数量 end -->
			</view>
			<view class="add-to-cart-btn" @tap="handleAddToCartInModal">
				<view>加入购物车</view>
			</view>
		</modal>
		<!-- 商品详情模态框 end -->
		<!-- 购物车详情popup -->
		<popup-layer direction="top" :show-pop="cartPopupVisible" class="cart-popup">
			<template slot="content">
				<view class="top">
					<text @tap="handleCartClear">清空</text>
				</view>
				<scroll-view class="cart-list" scroll-y>
					<view class="wrapper">
						<view class="item" v-for="(item, index) in cart" :key="index">
							<view class="left">
								<view class="name">{{ item.name }}</view>
								<view class="props">{{ item.props_text }}</view>
							</view>
							<view class="center">
								<text>￥{{ item.price }}</text>
							</view>
							<!-- 食物+1和-1 start -->
							<view class="right">
								<button type="default" plain size="mini" class="btn" hover-class="none"
									@tap="handleCartItemReduce(index)">
									<view class="iconfont iconsami-select"></view>
								</button>
								<view class="number">{{ item.number }}</view>
								<button type="primary" class="btn" size="min" hover-class="none"
									@tap="handleCartItemAdd(index)">
									<view class="iconfont iconadd-select"></view>
								</button>
							</view>
							<!-- 食物+1和-1 end -->
						</view>
						<!-- 包装费未实现 start -->
						<view class="item" v-if="orderType == 'takeout' && store.packing_fee">
							<view class="left">
								<view class="name">包装费</view>
							</view>
							<view class="center">
								<text>￥{{ parseFloat(store.packing_fee) }}</text>
							</view>
							<view class="right invisible">
								<button type="default" plain size="mini" class="btn" hover-class="none">
									<view class="iconfont iconsami-select"></view>
								</button>
								<view class="number">1</view>
								<button type="primary" class="btn" size="min" hover-class="none">
									<view class="iconfont iconadd-select"></view>
								</button>
							</view>
						</view>
						<!-- 包装费未实现 end -->
					</view>
				</scroll-view>
			</template>
		</popup-layer>
		<!-- 购物车详情popup -->
	</view>
	<view class="loading" v-else>
		<image src="/static/images/loading.gif"></image>
	</view>
</template>

<script>
	import modal from '@/components/modal/modal'
	import popupLayer from '@/components/popup-layer/popup-layer'
	const { getFoodsList } = require("@/api/api/menu")
	import {
		mapState,
		mapMutations,
		mapActions,
		mapGetters
	} from 'vuex'

	export default {
		components: {
			modal,
			popupLayer
		},
		data() {
			return {
				orderType:'takein',
				goods: [], //所有商品
				ads: [{ //轮播图
					image: 'https://img-shop.qmimg.cn/s23107/2020/04/27/4ebdb582a5185358c4.jpg?imageView2/2/w/600/h/600'
					},
					{
						image: 'https://images.qmai.cn/s23107/2020/05/08/c25de6ef72d2890630.png?imageView2/2/w/600/h/600'
					},
					{
						image: 'https://img-shop.qmimg.cn/s23107/2020/04/10/add546c1b1561f880d.jpg?imageView2/2/w/600/h/600'
					},
					{
						image: 'https://images.qmai.cn/s23107/2020/04/30/b3af19e0de8ed42f61.jpg?imageView2/2/w/600/h/600'
					},
					{
						image: 'https://img-shop.qmimg.cn/s23107/2020/04/17/8aeb78516d63864420.jpg?imageView2/2/w/600/h/600'
					}
				],
				loading: true,
				currentCateId: 6905, //默认分类
				cateScrollTop: 0,
				menuScrollIntoView: '',
				cart: [], //购物车
				goodDetailModalVisible: false, //是否饮品详情模态框
				good: {}, //当前饮品
				category: {}, //当前饮品所在分类
				cartPopupVisible: false,
				sizeCalcState: false
			}
		},
		async onLoad() {
			
			if(this.seat==null||this.seat=={}){
				uni.navigateTo({
					url:'/pages/me/me'
				})
				return;
			}
			
			await this.init()
		},
		computed: {
			...mapState([ 'seat','type']),
			...mapGetters('user',['isLogin']),
			goodCartNum() { //计算单个饮品添加到购物车的数量
				return (id) => this.cart.reduce((acc, cur) => {
					if (cur.id === id) {
						return acc += cur.number
					}
					return acc
				}, 0)
			},
			menuCartNum() {
				return (id) => this.cart.reduce((acc, cur) => {
					if (cur.cate_id === id) {
						return acc += cur.number
					}
					return acc
				}, 0)
			},
			getCartGoodsNumber() { //计算购物车总数
				return this.cart.reduce((acc, cur) => acc + cur.number, 0)
			},
			getCartGoodsPrice() { //计算购物车总价
				return this.cart.reduce((acc, cur) => acc + cur.number * cur.price, 0)
			},
			disabledPay() { //是否达到起送价
				return this.orderType == 'takeout' && (this.getCartGoodsPrice < this.store.min_price) ? true : false
			},
			spread() { //差多少元起送
				if (this.orderType != 'takeout') return
				return parseFloat((this.store.min_price - this.getCartGoodsPrice).toFixed(2))
			}
		},
		methods: {
			...mapMutations(['SET_ORDER_TYPE','SET_TYPE']),
			
			async init() { //页面初始化
				this.loading = true
				
				const res =  await getFoodsList()
				this.goods = res.data
				console.log(this.goods)
				this.loading = false
				this.cart = uni.getStorageSync('cart') || []
			},
			goMenu(){
				
				if(this.type==1) return
				
				if(this.seat==null||this.seat=={}){
					uni.navigateTo({
						url:'/pages/me/me'
					})
					return;
				}
				
				this.SET_TYPE(1)
				uni.redirectTo({
					url: '/pages/menu/menu'
				})
			},
			goOrder(){
				if (this.type == 2) return
				this.SET_TYPE(2)
				if (!this.isLogin) {
					uni.navigateTo({
						url: '/pages/login/login'
					})
					return
				}
				console.log("dddds")
				uni.redirectTo({
					url: '/pages/orders/orders'
				})
			},
			
			handleMenuTap(id) { //点击菜单项事件
				if (!this.sizeCalcState) {
					this.calcSize()
				}

				this.currentCateId = id
				this.$nextTick(() => this.cateScrollTop = this.goods.find(item => item.id == id).top)
			},
			handleGoodsScroll({
				detail
			}) { //商品列表滚动事件
				if (!this.sizeCalcState) {
					this.calcSize()
				}
				const {
					scrollTop
				} = detail
				let tabs = this.goods.filter(item => item.top <= scrollTop).reverse()
				if (tabs.length > 0) {
					this.currentCateId = tabs[0].id
				}
			},
			calcSize() {
				let h = 10

				let view = uni.createSelectorQuery().select('#ads')
				view.fields({
					size: true
				}, data => {
					h += Math.floor(data.height)
				}).exec()

				this.goods.forEach(item => {
					let view = uni.createSelectorQuery().select(`#cate-${item.id}`)
					view.fields({
						size: true
					}, data => {
						item.top = h
						h += data.height
						item.bottom = h
					}).exec()
				})
				this.sizeCalcState = true
			},
			handleAddToCart(cate, good, num) { //添加到购物车
				const index = this.cart.findIndex(item => {
					if (good.useProperty) {
						return (item.id === good.id) && (item.props_text === good.props_text)
					} else {
						return item.id === good.id
					}
				})
				if (index > -1) {
					this.cart[index].number += num
				} else {
					this.cart.push({
						id: good.id,
						//分类id
						cate_id: cate.id,
						name: good.name,
						price: good.price,
						number: num,//数量
						pricutre: good.pricutre,
						useProperty: good.useProperty,
						//一段文字，描述扩展
						props_text: good.props_text,
						//选择的扩展id列表
						props: good.props
					})
				}
			},
			handleReduceFromCart(item, good) {
				const index = this.cart.findIndex(item => item.id === good.id)
				this.cart[index].number -= 1
				if (this.cart[index].number <= 0) {
					this.cart.splice(index, 1)
				}
			},
			showGoodDetailModal(item, good) {
				console.log("我来了")
				this.good = JSON.parse(JSON.stringify({
					...good,
					number: 1
				}))
				console.log(this.good)
				this.category = JSON.parse(JSON.stringify(item))
				console.log(this.category)
				this.goodDetailModalVisible = true
			},
			closeGoodDetailModal() { //关闭饮品详情模态框
				this.goodDetailModalVisible = false
				this.category = {}
				this.good = {}
			},
			changePropertyDefault(index, key) { //改变默认属性值
				this.good.property[index].children.forEach(value => {
					if(value.isDefault==1){
						//选中了
						this.good.price-=value.price
					}
						
					this.$set(value, 'isDefault', 0)
					
				})
				this.good.property[index].children[key].isDefault = 1
				this.good.number = 1
				this.good.price+=this.good.property[index].children[key].price
		
				
			},
			getGoodSelectedProps(good, type = 'text') { //计算当前饮品所选属性
				if (good.useProperty) {
					let props = []
					good.property.forEach(({
						children
					}) => {
						children.forEach(value => {
							if (value.isDefault) {
								props.push(type === 'text' ? value.name : value.id)
							}
						})
					})
					return type === 'text' ? props.join('，') : props
				}
				return ''
			},
			handlePropertyAdd() {
				this.good.number += 1
			},
			handlePropertyReduce() {
				if (this.good.number === 1) return
				this.good.number -= 1
			},
			handleAddToCartInModal() {
				//将this.good和后面2个东东合并
				const product = Object.assign({}, this.good, {
					props_text: this.getGoodSelectedProps(this.good),
					props: this.getGoodSelectedProps(this.good, 'id')
				})
				//这个食物的分类模块，{props_text,prop}(食物描述,选择的扩展id集合),购买数量
				this.handleAddToCart(this.category, product, this.good.number)
				this.closeGoodDetailModal()
			},
			openCartPopup() { //打开/关闭购物车列表popup
				this.cartPopupVisible = !this.cartPopupVisible
			},
			handleCartClear() { //清空购物车
				uni.showModal({
					title: '提示',
					content: '确定清空购物车么',
					success: ({
						confirm
					}) => {
						if (confirm) {
							this.cartPopupVisible = false
							this.cart = []
						}
					}
				})
			},
			handleCartItemAdd(index) {
				this.cart[index].number += 1
			},
			handleCartItemReduce(index) {
				if (this.cart[index].number === 1) {
					this.cart.splice(index, 1)
				} else {
					this.cart[index].number -= 1
				}
				if (!this.cart.length) {
					this.cartPopupVisible = false
				}
			},
			toPay() {
				if (!this.isLogin) {
					uni.navigateTo({
						url: '/pages/login/login'
					})
					return
				}

				uni.showLoading({
					title: '加载中'
				})
				uni.setStorageSync('cart', JSON.parse(JSON.stringify(this.cart)))

				uni.navigateTo({
					url: '/pages/pay/pay'
				})
				uni.hideLoading()
			}
		}
	};
</script>

<style lang="scss" scoped>
	@import '~@/pages/menu/menu.scss';
</style>
