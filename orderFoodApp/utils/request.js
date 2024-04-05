//fetch.js
import errorCode from "@/utils/errorCode";
const BaseUrl = 'http://localhost:8088'  //设置基本的url路径
export default ({ //对页面穿过来的参数进行解构
	url,
	method,
	data,
}) => {
	// 请求前
	const token = uni.getStorageSync('token');
	let header={};
	if(token!=null&&token!='')
	{  //设置请求的 header
		header={token:token}
	}
	// uni.showLoading({
	// 	title: "加载中"
	// });

	//发起请求
	return new Promise(function(resolve, reject) {
	
		uni.request({
			url: BaseUrl + url,  // 开发者服务器接口地址
			method: method,   
			timeout: 60000, //请求超时时间
			dataType: "json", 
			responseType: 'text',
			data: data,  //请求的参数
			withCredentials:true,
			header: header,
			// 成功时回调
			success(res) {//对请求请求到的信息进行处理
				console.log(res)
				// 未设置状态码则默认成功状态
				const code = res.data.code || 200;
				// 获取错误信息
				const msg = errorCode[code] || res.data.msg || errorCode['default'];
				//统一异常处理 能处理即处理，之后把异常抛出
				switch (code) {
				    //认证失败，可能token过期，直接踢出去登录
				    case 401:
					uni.showToast({
									title: "登录生效，请重新登录",
									icon:'exception',
									duration:850
								});
				        //把本地的token清了,然后跳登录页
				        // window.sessionStorage.clear()
						uni.clearStorageSync("token")
						uni.clearStorageSync("user")
				        Vue.prototype.$router.push("/login")
				        break;
				    //可能后端代码错
				    case 500:
				       uni.showToast({
				       				title: msg,
				       				icon:'exception',
				       				duration:850
				       			});
				        break;
				    case 200:
						resolve(res.data);
						break;
				        // return res.data;
				    //具体的业务错误，抛出去给业务调用者处理即可
				    default:
				       uni.showToast({
				       				title: msg,
				       				icon:'exception',
				       				duration:850
				       			});
				}
				//回调错误函数
				reject(res)
				//返回值：一个被拒绝的 Promise对象。 不去捕获他也行
				// return Promise.reject(new Error(msg))
				// if (res.data.code == 0) {
				// 	resolve(res.data.data)
				// }
			},
			fail(err) {
				reject(err)
			}
		})
	})
}