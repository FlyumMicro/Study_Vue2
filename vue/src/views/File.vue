<template>
 <div>
   <!-- 功能模块 -->
   <div style="margin: 10px 0">
     <div style="padding: 10px 0;">
       <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
       <el-button type="primary" icon="el-icon-search" @click="load" class="ml-5">搜索</el-button>
       <el-button type="warning" icon="el-icon-set-up" @click="reset" >重置</el-button>
     </div>
     <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" accept="xlsx" :on-success="handleFileUploadSuccess" style="display: inline-block">
       <el-button type="primary" class="ml-5"> 上传文件 <i class="el-icon-upload"></i></el-button>
     </el-upload>
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
   <el-table :data="tableData" border stripe style="width: 100%" :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
     <!--多项Checkbox-->
     <el-table-column type="selection" width="55">
     </el-table-column>
     <el-table-column prop="id" label="序号" width="80">
     </el-table-column>
     <el-table-column prop="name" label="文件名称" >
     </el-table-column>
     <el-table-column prop="type" label="文件类型" width="120">
     </el-table-column>
     <el-table-column prop="size" label="文件大小（KB）" width="120">
     </el-table-column>
     <el-table-column  label="下载">
       <template slot-scope="scope">
         <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
       </template>
     </el-table-column>
     <el-table-column label="启用">
         <template slot-scope="scope">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
         </template>
     </el-table-column>

     <!-- 修改新增 -->
     <el-table-column prop="funt" label="操作" width="200" align="center">
       <template slot-scope="scope">


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
 </div>

</template>

<script>
export default {
  name: "File",
  data(){
    return {
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      this.request.get("/file/page",{
        params:{
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          name: this.name,
        }
      }).then(res => {
        //请求页码 内容
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    changeEnable(row){
      this.request.post("file/update", row).then(res =>{
        if (res.code === '200'){
          this.$message.success("操作成功")
        }
      })
    },
    //删除方法
    del(id){
      //先请求接口
      this.request.delete("/file/" + id).then(res => {
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
      this.request.post("/file/del/batch", ids).then(res => {
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
      this.name = ""
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
    },
    //文件导入成功
    handleFileUploadSuccess(res){
      console.log(res)
      this.load()
    },
    download(url){
      window.open(url)
    }
  }
}
</script>

<style scoped>

</style>