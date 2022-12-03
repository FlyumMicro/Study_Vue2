<template>
    <div>
      <el-row :gutter="10" style="margin-bottom: 100px">
        <el-col :span="6">
            <el-card style="color: #409EFF">
              <div ><i class="el-icon-user-solid" ></i>用户总数</div>
              <div style="padding: 10px 0; text-align: center; font-weight: bold ">100</div>
            </el-card>

        </el-col>
        <el-col :span="6">
          <el-card style="color: #409EFF" >
            <div ><i class="el-icon-s-data" ></i>销售总量</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold ">100</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #409EFF">
            <div ><i class="el-icon-s-marketing" ></i>收益额</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold ">100</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #409EFF">
            <div ><i class="el-icon-s-shop" ></i>门店总数</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold ">100</div>
          </el-card>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="12">
          <div class="main" id="main" ></div>
        </el-col>

        <el-col :span="12">
          <div class="main" id="pie"></div>
        </el-col>

      </el-row>




    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted() { //页面元素渲染之后再出发
    // 折线图
    var option = {
      title: {
        text: '用户数量统计',
        subtext: '折线图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: 123,
          data: [],
          type: 'line'
        },
        {
          name: 567,
          data: [],
          type: 'bar'
        }
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);


    var pieDon = document.getElementById('pie');
    var pieChart = echarts.init(pieDon);


    this.request.get("/echarts/members").then(res => {
      //填空
      //option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data
      //数据准备完毕之后再set
      myChart.setOption(option);

      pieoption.series[0].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]}
      ]
      pieChart.setOption(pieoption);
    })

    // this.request.get("/echarts/example").then(res =>{
    //
    // })

    // 饼图
    var pieoption = {
      title: {
        text: '用户数量统计 (饼图)',
        subtext: '折线图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'right'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',  //半径
          label:{    //图标签
            normal:{
              show: true,
              position:'inner',  //标签位置
              textStyle:{
                fontWeight: 300,
                fontSize: 13,  //文字大小
                color: "#ffff"
              },
              formatter:'{d}%'  //加百分号
            }
          },
          data: [],  //数据
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
  }
}
</script>

<style>
.main{
  margin-left: 20px;
  width: 700px;
  height: 500px;
}
</style>