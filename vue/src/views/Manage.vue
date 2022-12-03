<template>
  <!-- 菜单栏 -->
  <el-container style="min-height: 100vh">
    <!-- 左边导航栏 -->
    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(255, 255, 255); box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <!-- 这里的 Aside 是侧边栏的 固定 封装在component 文件夹里 -->
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
    </el-aside>

    <!-- 个人信息 -->
    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; background-color:rgb(238, 238, 238); box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1) " >
        <!--   这里的 Header 是顶栏 封装在component 文件夹里 -->
        <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" :user="user"/>
      </el-header>

      <!-- 内容模块 -->
      <el-main>
        <!--   表示当前页面子路由会在<router-view />里面展示 -->
        <router-view @refreshUser="getUser"/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>

// import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Home',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  components:{
     Aside,
     Header
  },
  created() {
    //一开始后台获取最新的User数据
     this.getUser()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      if (username) {
        // 从后台获取User数据
        this.request.get("/user/username/" + username).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>


