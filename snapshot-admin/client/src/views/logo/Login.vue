<template>
	<div class="form_container">
		<div class="manage_tip">
			<span class="title">在线后台管理系统</span>
			<el-form :model="loginUser" status-icon :rules="rules" ref="loginForm" class="loginForm" label-width="80px">
				<el-form-item label="手机号" prop="username">
					<el-input v-model="loginUser.username" placeholder="请输入手机号"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input type="password" v-model="loginUser.password" placeholder="请输入密码"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" class="submit_btn" @click="submitForm('loginForm')">登录</el-button>
				</el-form-item>
				<!-- <div class="info">
					<p>还没有账号,请先<router-link to="/register">注册?</router-link></p>
				</div>	 -->
			</el-form>
		</div>
	</div>
</template>

<script>
import jwt_decode from 'jwt-decode'
// @ is an alias to /src
export default {
  name: 'Register',
  data () {
	return {
	  loginUser: {
			username: '',
			password: '',
	},
		rules: {
			username: [
				{ required: true, message: '手机号不能为空', trigger: 'blur' },
				{ min: 11, max: 11, message: '手机号长度为11个字符', trigger: 'blur' }
			],
			password: [
				{ required: true, message: '密码不能为空', trigger: 'blur' },
				{ min: 6, max: 30, message: '长度在2到30个字符之间', trigger: 'blur' }
			]
	  }
	}
  },
  methods: {
		submitForm (formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.$axios.post('/api/user/login',this.loginUser).then(res => {
						const token   = res.data.data; 
						localStorage.setItem('eleToken',token); //存储token
						// 解析token
						const decode = jwt_decode(token)
						
						//存储token到vuex
						this.$store.dispatch('setAuthenticated' , !this.isEmpty(token))
						this.$store.dispatch('setUSER',token)

						this.$router.push('/index')
		 			})
				}
			})
		},
		isEmpty(value) {
			return (
				value === undefined || value === null || (typeof value === 'object' && Object.keys(value).length === 0)
				|| (typeof value === 'string' && value.trim().length === 0)
			)
		}
  },created() {
            console.log(!this.$store.getters.user)
			if(this.$store.getters.user!=null) {
               this.$router.push('/index')
			}
			
  }
}
</script>

<style scoped>
.form_container {
  position: relative;
  width: 100%;
  height: 100%;
  background: url(../../assets/bg.jpg) no-repeat center center;
  background-size: 100% 100%;
}
.manage_tip {
  width: 370px;
  height: 210px;
  position: absolute;
  top: 10%;
  left: 50%;
  padding: 25px;
  border-radius: 5px;
  text-align: center;
  margin-left: -185px;
}
.form_container .manage_tip .title {
  font-family: "Microsoft YaHei";
  font-weight: bold;
  font-size: 26px;
  color: #fff;
}

.loginForm {
  margin-top: 20px;
  background-color: white;
  padding: 20px 40px 20px 20px;
  border-radius: 5px;
  box-shadow: 0px 5px 10px #cccccc;
}
.submit_btn {
  width: 100%;
}
.info{
	float: right;
	font-size: 14px;
	color: #606266;
}
a{
	text-decoration: none;
}
</style>