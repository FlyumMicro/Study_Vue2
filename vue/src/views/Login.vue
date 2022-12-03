<template>
  <div class="wrapper">
    <div class="loginBox" >
      <div style="margin: 25px 0; text-align: center; font-size: 20px; color: black">
        <b>Yue Blogs Login System</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right" >
          <el-button type="primary" size="large"  autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="large"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      //user 绑定 数据校验
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data)) //存储用户信息
              this.$router.push("/")
              this.$message.success("登录成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
    * {
      margin: 0;
      padding: 0;
      font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
      box-sizing: border-box;
    }
    .wrapper {
      height: 100vh;
      background-image: url("../assets/S.png");
      background-position: center;
      background-attachment: fixed;
      background-size: cover;
      overflow: hidden;

    }
    .loginBox {
      margin: 200px auto;
      background-color: #fff;
      box-shadow: 0 25px 50px rgba(0,0,0,0.6);
      width: 350px;
      height: 320px;
      padding: 20px;
      border-radius: 13px
    }
</style>