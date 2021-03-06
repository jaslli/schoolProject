<template>
  <div class="app-container">
    <div style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11; margin-left: 10px;"> 课程列表</div>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布" />
          <el-option value="Draft" label="未发布" />
        </el-select>
      </el-form-item>

      <el-button type="primary" plain="true" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" plain="true" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>

      <el-table-column label="序号" width="100" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="封面" width="250" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.cover" alt="scope.row.title" width="100%">
        </template>
      </el-table-column>

      <el-table-column label="课程信息">
        <template slot-scope="scope">
          <a href="">{{ scope.row.title }}</a>
          <p>
            课时：{{ scope.row.lessonNum }}
          </p>
          <p>
            浏览：{{ scope.row.viewCount }} /
            付费学员：{{ scope.row.buyCount }}
          </p>
        </template>
      </el-table-column>

      <el-table-column label="价格(元)" width="130" align="center">
        <template slot-scope="scope">

          <el-tag v-if="Number(scope.row.price) === 0" type="success">免费</el-tag>

          <el-tag v-else>{{ scope.row.price }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="status" label="课程状态" width="130" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 'Draft' ? 'warning' : 'success'">{{ scope.row.status === 'Draft' ? '未发布' : '已发布' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="gmtCreate" label="添加时间" width="170" />

      <el-table-column label="操作" align="center" style="height: 100%;">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" plain="true" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" plain="true" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
          </router-link>
          <el-button type="danger" plain="true" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程信息</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页设置-->
    <el-pagination
      background
      layout="total,prev, pager, next,jumper"
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      @current-change="getList"
    />

  </div>
</template>

<script>

import courseApi from '@/api/edu/course'

export default {
  data() {
    return {
      list: null, // 课程数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      courseQuery: {}, // 查询条件
      teacherList: [], // 讲师列表
      subjectOneList: [], // 一级分类列表
      subjectTwoList: [] // 二级分类列表
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 删除课程
    removeDataById(courseId) {
      this.$confirm('此操作将永久删除该课程，以及该课程下的章节,小节和视频，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        courseApi.deleteCourse(courseId)
          .then(response => {
            // 提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // 回到列表页面
            this.getList()
          })
      })
    },
    // 获取课程数据
    getList(page = 1) {
      this.page = page
      courseApi.getPageList(this.page, this.limit, this.courseQuery)
        .then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
    },
    // 条件清空
    resetData() {
      this.courseQuery = {}
      this.getList(this.page)
    }

  }
}
</script>

<style>

</style>
