<template>
  <!-- 左边导航栏 -->
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(16, 172, 168)"
           text-color="rgb(255, 255, 255)"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/login_logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color: rgb(255, 255, 255)" v-show="logoTextShow">后台管理系统</b>
    </div>

    <!-- 标题1 -->
    <el-submenu index="/home" >
      <template slot="title">
        <i class="el-icon-s-home" style="color:rgb(255, 255, 255)"></i>
        <span slot="title">首页</span>
      </template>

      <el-menu-item-group>
        <el-menu-item index="/home">
          <i class="el-icon-s-shop" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">商户状况</span>
        </el-menu-item>

        <el-menu-item index="/person">
          <i class="el-icon-s-finance" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">用户信息</span>
        </el-menu-item>

      </el-menu-item-group>

    </el-submenu>
    <!-- 标题2 -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu" style="color:rgb(255, 255, 255)"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item-group>

        <el-menu-item index="/user">
          <i class="el-icon-s-custom" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>

        <el-menu-item index="/file">
          <i class="el-icon-document" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">文件管理</span>
        </el-menu-item>

        <el-menu-item index="/role">
          <i class="el-icon-s-tools" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">角色管理</span>
        </el-menu-item>

        <el-menu-item index="/menu">
          <i class="el-icon-s-operation" style="color:rgb(255, 255, 255)"></i>
          <span slot="title">菜单管理</span>
        </el-menu-item>

      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
// 需要设置类型
export default {
  name: "Aside",
  data(){
    return{
      sideWidth: 200,
      isCollapse: false,
    }
  },
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods:{
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
    }
  }
}
</script>

<style>

/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}
</style>