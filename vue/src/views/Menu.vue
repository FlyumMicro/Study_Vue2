<template>
  <!-- 内容模块 -->
  <div>
    <!-- 页面标签 -->
    <!-- 搜索框模块 -->
    <div style="padding: 10px 0;">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>

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


    </div>

    <!-- 表格模块 -->
    <el-table :data="tableData"  border stripe style="width: 100%" :header-cell-class-name="'headerBg'" row-key="id" @selection-change="handleSelectionChange">
      <!--多项Checkbox-->
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="75">
      </el-table-column>
      <el-table-column prop="name" label="名称" >
      </el-table-column>
      <el-table-column prop="path" label="路径" >
      </el-table-column>
      <el-table-column prop="icon" label="图标" >
      </el-table-column>
      <el-table-column prop="description" label="描述" >
      </el-table-column>
      <!-- 修改新增 -->
      <el-table-column prop="funt" label="操作" width="200" align="center">
        <template slot-scope="scope">

          <el-button type="primary" icon="el-icon-plus" @click="handleAdd(scope.row.id)" circle v-if="!scope.row.pid && !scope.row.path"></el-button>
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
<!--    <div style="padding: 10px 0">-->


    <!--新增弹窗-->
    <!-- Form -->
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>


        <el-form-item label="路径" >
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="图标" >
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" >
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
      name: "",
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
      this.request.get("/menu",{
        params:{
          name: this.name
        }
      }).then(res => {
        //请求页码 内容
        console.log(res)
        this.tableData = res.data
      })
    },
    //新增用户提交方法
    save(){
      this.request.post("/menu", this.form).then(res => {
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
    handleAdd(pid){
      this.dialogFormVisible = true
      this.form = {}
      if(pid){
        this.form.pid = pid;
      }
    },
    //编辑点击事件
    handleEdit(row){
      this.form = row  //赋予数据
      this.dialogFormVisible = true //打开窗口
    },
    //删除方法
    del(id){
      //先请求接口
      this.request.delete("/menu/" + id).then(res => {
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
    //批量删除事件
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [1,2,3] 改变数组
      this.request.post("/menu/del/batch", ids).then(res => {
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
      this.name = null
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