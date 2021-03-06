<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section v-if="total == 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理 中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <!-- 数据显示 开始-->
          <article v-if="total > 0" class="i-teacher-list">
            <ul class="of">
              <li v-for="teacher in list" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href='"/teacher/"+teacher.id' :title=teacher.name target="_blank">
                      <img :src=teacher.avatar alt />
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href='"/teacher/"+teacher.id'  :title=teacher.name target="_blank" class="fsize18 c-666" >{{teacher.name}}</a >
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{teacher.career}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.intro}}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
          <!-- 数据显示 结束-->
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
    <!-- /讲师列表 结束 -->
  </div>
</template>

<script>

import indexApi from '@/api/index'

export default {
  data() {
    return {
      list: null, // 查询之后返回的结果
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 8, // 每页记录数
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 条件查询获取数据获取数据
    getList(page = 1) {
      this.page = page
      indexApi.getTeacherList(this.page, this.limit).then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
      }).catch(error => { console.log(error) })
    },
  }
}
</script>