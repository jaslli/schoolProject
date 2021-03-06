<template>
  <div class="app-container">
    <!-- 标题 -->
    <h2 style="text-align: center;">发布新课程</h2>
    <!-- 步骤条 -->
    <el-steps :active="3" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程的基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终保存" />
    </el-steps>

    <!-- 最终展示页面 -->
    <div class="ccInfo">
      <img :src="course.cover">
      <div class="main">
        <h2>{{ course.title }}</h2>
        <p class="gray"><span>共{{ course.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ course.subjectLevelOne }} — {{ course.subjectLevelTwo }}</span></p>
        <p>课程讲师：{{ course.teacherName }}</p>
        <h3 class="red">￥{{ course.price }}</h3>
      </div>
    </div>

    <!-- 跳转按钮 -->
    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">返回上一步修改</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>

import courseApi from '@/api/edu/course'

export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseId: '', // 课程ID
      course: {} // 课程信息
    }
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      this.getCourse()
    }
  },
  methods: {

    // 根据课程ID查询课程最终信息
    getCourse() {
      courseApi.getCourse(this.courseId)
        .then(response => {
          this.course = response.data.publishCourse
        })
    },
    //* ********************跳转按钮的方法****************************
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/chapter/' + this.courseId })
    },
    publish() {
      courseApi.publishCourse(this.courseId)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '课程发布成功!'
          })
          // 路由跳转
          this.$router.push({ path: '/course/list' })
        })
    }
  }
}
</script>

<style scoped>
.ccInfo {
  background: #f5f5f5;
  padding: 20px;
  overflow: hidden;
  border: 1px dashed #DDD;
  margin-bottom: 40px;
  position: relative;
}
.ccInfo img {
  background: #d6d6d6;
  width: 250px;
  height: 250px;
  display: block;
  float: left;
  border: none;
}

.ccInfo .main {
  margin-left: 520px;
}

.ccInfo .main h2 {
  font-size: 28px;
  margin-bottom: 30px;
  line-height: 1;
  font-weight: normal;
}
.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}
.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}
.ccInfo .main h3 {
  left: 540px;
  bottom: 20px;
  line-height: 1;
  font-size: 28px;
  color: #d32f24;
  font-weight: normal;
  position: absolute;
}
</style>
