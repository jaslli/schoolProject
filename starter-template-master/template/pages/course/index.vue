<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="initOne()">全部</a>
                </li>
                <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active:oneIndex == index}">
                  <a :title="item.title" href="#" @click="searchOne(item.id,index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item,index) in subSubjectList" :key="index" :class="{active:twoIndex == index}">
                  <a :title="item.title" href="#" @click="searchTwo(item.id,index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                  <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                  <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- 无数据提示 开始-->
          <section class="no-data-wrap" v-if="total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理 中...</span>
          </section>
          <!-- 无数据提示 结束-->
          <article v-if="total > 0" class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="course in list" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span v-if="Number(course.price) === 0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{course.buyCount}}人学习</i>|<i class="c-999 f-fA">{{course.viewCount}}浏览</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 分页 开始 -->
        <el-pagination
          background
          layout="total,prev, pager, next,jumper"
          :current-page="page"
          :page-size="limit"
          :total="total"
          style="padding: 30px 0; text-align: center;"
          @current-change="getList"
        />
        <!-- 分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>

import indexApi from '@/api/index'

export default {
  data() {
    return{
      list:null, // 课程列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 8, // 每页记录数
      subjectNestedList:[], // 一级分类列表
      subSubjectList:[], // 二级分类列表
      searchObj: {}, // 条件查询的表单
      oneIndex: -1,
      twoIndex: -1,
      buyCountSort:"",  // 是否选中销量排序
      gmtCreateSort:"", // 是否选择时间排序
      priceSort:""  // 是否选择价格排序
    }
  },
  created() {
    this.getList()
    this.getSubjectList()
  },
  methods: {
    // 条件查询获取数据获取数据
    getList(page = 1) {
      this.page = page
      indexApi.getCourseList(this.page, this.limit,this.searchObj).then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
      }).catch(error => { console.log(error) })
    },
    // 查询所有分类
    getSubjectList() {
      indexApi.getAllSubject()
       .then(response => {
         this.subjectNestedList = response.data.data.list
       })
    },
    // 初始化分类，即不查询二级分类
    initOne() {
      this.subSubjectList = []
      this.searchObj.subjectParentId = ""
      this.searchObj.subjectId = ""
      this.oneIndex = -1
      this.getList()
    },
    // 点击一级分类实现查询
    searchOne(subjectParentId,index) {
      // 一级分类样式生效,并清除二级分类状态
      this.oneIndex = index
      this.twoIndex = -1
      this.searchObj.subjectId = ""
      this.subSubjectList = []
      // 查询一级分类的课程数据
      this.searchObj.subjectParentId = subjectParentId
      this.getList()

      // 查询对应的二级分类
      for(let one of this.subjectNestedList) {
        if(subjectParentId == one.id) {
          this.subSubjectList = one.children
        }
      }
    },
    // 点击二级分类实现查询
    searchTwo(subjectId,index) {
      // 样式生效
      this.twoIndex = index
      // 添加条件,进行查询
      this.searchObj.subjectId = subjectId
      this.getList()
    },
    // 点击销量排序进行查询
    searchBuyCount() {
      // 样式生效
      this.buyCountSort = "1"
      this.gmtCreateSort = ""
      this.priceSort = ""

      // 添加条件,进行查询
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.getList()
    },
    // 点击时间排序进行查询
    searchGmtCreate() {
      // 样式生效
      this.buyCountSort = ""
      this.gmtCreateSort = "1"
      this.priceSort = ""

      // 添加条件,进行查询
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.getList()
    },
    // 点击价格排序进行查询
    searchPrice() {
      // 样式生效
      this.buyCountSort = ""
      this.gmtCreateSort = ""
      this.priceSort = "1"

      // 添加条件,进行查询
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.getList()
    },
  }
}
</script>

<style scoped>
 .active {
   background: #bdbdbd;
 }
 .hide {
   display: none;
 }
 .show {
   display: block;
 }
</style>