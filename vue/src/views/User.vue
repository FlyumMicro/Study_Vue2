<template>
    <!-- 内容模块 -->
   <div>
     <!-- 页面标签 -->
     <!-- 搜索框模块 -->
     <div style="padding: 10px 0;">
       <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="username"></el-input>
       <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
       <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
       <el-input style="width: 200px" placeholder="请输入手机号" suffix-icon="el-icon-mobile-phone" class="ml-5" v-model="phone"></el-input>
       <el-button type="primary" icon="el-icon-search" @click="load" class="ml-5">搜索</el-button>
       <el-button type="warning" icon="el-icon-set-up" @click="reset" >重置</el-button>
     </div>

     <!-- 功能模块 -->
     <div style="margin: 10px 0">
       <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
       <!--删除反复确认-->
       <el-popconfirm
           class="ml-5"
           confirm-button-text='确定'
           cancel-button-text='我在想想'
           icon="el-icon-info"
           icon-color="red"
           title="确定批量删除吗？"
           @confirm="delBatch"
       >
         <el-button type="danger" slot="reference"  >批量删除 <i class="el-icon-remove-outline"></i></el-button>
       </el-popconfirm>

       <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
         <el-button type="primary" class="ml-5"> 导入 <i class="el-icon-bottom"></i></el-button>
       </el-upload>
       <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
     </div>

     <!-- 表格模块 -->
     <el-table :data="tableData" border stripe style="width: 100%" :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
       <!--多项Checkbox-->
       <el-table-column type="selection" width="55">
       </el-table-column>
       <el-table-column prop="id" label="id" width="55">
       </el-table-column>
       <el-table-column prop="username" label="用户名" width="130">
       </el-table-column>
       <el-table-column prop="nickname" label="昵称" width="150">
       </el-table-column>
       <el-table-column prop="email" label="邮箱" width="180">
       </el-table-column>
       <el-table-column prop="phone" label="手机" width="130">
       </el-table-column>
       <el-table-column prop="address" label="地址">
       </el-table-column>

       <!-- 修改新增 -->
       <el-table-column prop="funt" label="操作" width="200" align="center">
         <template slot-scope="scope">
           <el-button type="success" icon="el-icon-edit" @click="handleEdit(scope.row)" circle></el-button> <!--编辑-->

           <!--删除反复确认-->
           <el-popconfirm
               class="ml-5"
               confirm-button-text='确定'
               cancel-button-text='我在想想'
               icon="el-icon-info"
               icon-color="red"
               title="确定删除吗？"
               @confirm="del(scope.row.id)"
           >
             <el-button type="danger" slot="reference" icon="el-icon-delete" circle></el-button> <!--删除-->
           </el-popconfirm>
         </template>

       </el-table-column>
     </el-table>

     <!-- 分页模块 -->
     <!--        :current-page="pageNum" 当前页码  :page-size="pageSize" 每页个数  total="total" 后台数据绑定    @size-change="handleSizeChange"
                   @current-change="handleCurrentChange" 触发-->
     <div style="padding: 10px 0">
       <el-pagination
           @size-change="handleSizeChange"
           @current-change="handleCurrentChange"
           :current-page="pageNum"
           :page-sizes="[10, 20, 30, 40]"
           :page-size="pageSize"
           layout="total, sizes, prev, pager, next, jumper"
           :total="total">
       </el-pagination>
     </div>

     <!--新增弹窗-->
     <!-- Form -->
     <el-dialog title="用户信息新增" :visible.sync="dialogFormVisible" width="30%" >
       <el-form label-width="80px" size="small">
         <el-form-item label="用户名" >
           <el-input v-model="form.username" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="昵称" >
           <el-input v-model="form.nickname" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="邮箱" >
           <el-input v-model="form.email" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="电话" >
           <el-input v-model="form.phone" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="地址" >
           <el-input v-model="form.address" autocomplete="off"></el-input>
         </el-form-item>
       </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button @click="dialogFormVisible = false">取 消</el-button>
         <el-button type="primary" @click="save">确 定</el-button>
       </div>
     </el-dialog>
   </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,  //显示内容
      username: "",
      email: "",
      address: "",
      phone: "",
      form: {}, //弹窗数据绑定
      dialogFormVisible: false, //弹窗默认隐藏
      multipleSelection: [], //批量选择
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods:{
    load() {
      //这里对应后台的 UserController.java "/page"
      this.request.get("/user/page",{
        params:{
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          username: this.username,
          email: this.email,
          address: this.address,
          phone: this.phone
        }
      }).then(res => {
        //请求页码 内容
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //新增用户提交方法
    save(){
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false //关闭窗口
          this.load() // 重新加载
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    //弹窗点击事件
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    //编辑点击事件
    handleEdit(row){
      this.form = row  //赋予数据
      this.dialogFormVisible = true //打开窗口
    },
    //删除方法
    del(id){
      //先请求接口
      this.request.delete("/user/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.dialogFormVisible = false //关闭窗口
          this.load() // 重新加载
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    //导入事件
    handleExcelImportSuccess() {
      this.$message.success("导入成功")  //提示
      this.load() //刷新
    },
    //导出事件
    exp(){
      window.open("http://localhost:9090/user/export")  //window 打开文件
    },
    //批量删除事件
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [1,2,3] 改变数组
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load() // 重新加载
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    //重置按钮事件
    reset(){
      this.username = null
      this.email = null
      this.phone = null
      this.address = null
      //清空后表格重新刷新
      this.load()
    },
    //页码
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>