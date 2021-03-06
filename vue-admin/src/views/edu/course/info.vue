/* eslint-disable vue/valid-v-for */
/* eslint-disable vue/valid-v-for */
<template>
  <div class="app-container">

    <h2 style="text-align: center;"> 发布新课程 </h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程的基本信息" />
      <el-step title="创建课程的大纲" />
      <el-step title="" />
    </el-steps>

    <el-form label-width="120px">

      <!-- 课程标题 -->
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder="示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写" />
      </el-form-item>

      <!-- 课程分类 -->
      <el-form-item label="课程分类">
        <el-select v-model="courseInfo.subjectParentId" placeholder="请选择课程的分类" @change="subjectLevelOneChanged">
          <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id" />
        </el-select>
        <el-select v-model="courseInfo.subjectId" placeholder="请选择课程的二级分类">
          <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title" :value="subject.id" />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择讲师">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id" />
        </el-select>
      </el-form-item>

      <!-- 课时 -->
      <el-form-item label="总课时">
        <el-input-number v-model="courseInfo.lessonNum" :min="0" controls-position="right" placeholder="请填写课程的总课时数" />
      </el-form-item>

      <!-- 课程价格 -->
      <el-form-item label="课程价格">
        <el-input-number v-model="courseInfo.price" :min="0" controls-position="right" placeholder="免费课程请设置为0元" /> 元
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <!-- 课程简介 -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="课程的简介信息" />
      </el-form-item>

      <!-- 保存按钮 -->
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import courseApi from '@/api/edu/course'
import teacherApi from '@/api/edu/teacher'
export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseInfo: {
        title: '',
        subjectId: '', // 二级分类id
        subjectParentId: '', // 一级分类id
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: 'https://my-project-00.oss-cn-hangzhou.aliyuncs.com/2021/02/03/941aaa4a19aa4b8ba39eceaeb84e0ddfseven.jpg',
        price: 0
      },
      teacherList: [], // 讲师数据
      subjectOneList: [], // 一级分类
      subjectTwoList: [], // 二级分类
      courseId: '', // 课程ID
      BASE_API: process.env.VUE_APP_BASE_API
    }
  },
  created() {
    //  判断路由是否有ID
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      // 数据回显
      this.getInfo()
    } else {
      // 初始化讲师数据
      this.getList()
      // 初始化一级分类数据
      this.getOneSubjectList()
    }
  },
  methods: {
    // 获取所有的一级分类
    getOneSubjectList() {
      courseApi.getSubjectList()
        .then(response => {
          this.subjectOneList = response.data.list
        })
    },
    // 选取一级分类后获取该一级分类的二级分类
    subjectLevelOneChanged(value) {
      // 该value是被vue封装的，其实就是一级分类的ID值
      for (const oneSubject of this.subjectOneList) {
        if (value === oneSubject.id) {
          this.subjectTwoList = oneSubject.children
          this.courseInfo.subjectId = ''
        }
      }
    },
    // 获取所有讲师数据
    getList() {
      teacherApi.getAllTeacherList()
        .then(response => {
          this.teacherList = response.data.items
        })
    },
    // 添加课程信息
    save() {
      courseApi.addCourseInfo(this.courseInfo)
        .then(response => {
          // 添加成功的提示
          this.$message({
            type: 'success',
            message: '添加课程信息成功'
          })
          this.$router.push({ path: '/course/chapter/' + response.data.courseId })
        })
    },
    // 修改课程信息
    update() {
      courseApi.updateCourseInfo(this.courseInfo)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改课程成功！'
          })
          this.$router.push({ path: '/course/chapter/' + this.courseId })
        })
    },
    // 保存并下一步按钮的方法
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        this.save()
      } else {
        this.update()
      }
    },
    // 上传封面成功的方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    },
    // 上传之前的方法，规定文件格式
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传的封面图片只能是JPG格式')
      }
      if (!isLt2M) {
        this.$message.error('上传的封面图片大小不能超过2MB')
      }
      return isJPG && isLt2M
    },
    // 根据课程ID查询课程信息
    getInfo() {
      courseApi.getCourseInfoById(this.courseId)
        .then(response => {
          // 获取课程的数据
          this.courseInfo = response.data.courseInfoVo
          // 初始化二级分类
          courseApi.getSubjectList().then(response => {
            this.subjectOneList = response.data.list
            for (const one of this.subjectOneList) {
              if (this.courseInfo.subjectParentId === one.id) {
                this.subjectTwoList = one.children
              }
            }
          })
        })
      this.getList()
    }
  }
}
</script>

<style scoped>
  img{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.342);
    border-radius: 2px;
    height: 200px;
    width: 200px;
}
</style>
