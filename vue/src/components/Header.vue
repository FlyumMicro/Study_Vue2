<template>
  <!-- 个人信息 -->
  <div style="font-size: 12px; line-height: 60px; display: flex;">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 20px"  @click="collapse"></span>
     <!--页面标签-->
      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block; margin-left: 10px "  >
        <el-breadcrumb-item :to="'/'" >首页</el-breadcrumb-item>
        <el-breadcrumb-item  >{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>

      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>

        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>

<script>
import user from "@/views/User";

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    // collapse: Boolean,
    user: Object  //获取 头像 姓名
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  data() {
    return { //获取 头像 姓名
      // user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    collapse(){
      //个人信息
      this.$emit("asideCollapse")
    },
    logout() { //退出登录
      this.$router.push("/login")
      localStorage.removeItem("user") //清除 user
      this.$message.success("退出成功")
    }
  }
}
  // created() {
  //   console.log(this.$route)
  // }

</script>

<style scoped>

</style>