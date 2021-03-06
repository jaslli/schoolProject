<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">修改头像</el-button>
        <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件
          @crop-upload-success：上传成功后的回调
        -->
        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :width="300"
          :height="300"
          :url="BASE_API + '/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate"> 保存 </el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>

// eslint-disable-next-line no-unused-vars
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: 'https://img.yww52.com/avatar.jpg'
      },
      // 上传组件是否显示
      imagecropperShow: false,
      //  上传组件的Key值
      imagecropperKey: 0,
      // 保存按钮是否禁用
      saveBtnDisabled: false,
      BASE_API: process.env.VUE_APP_BASE_API
    }
  },

  watch: {
    $route(to, from) {
      this.init()
    }
  },

  created() {
    this.init()
  },

  methods: {
    // 页面初始化
    init() {
      // 判断路由中是否有ID来确定是编辑页面还是添加页面
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      }
    },

    // 获取信息
    getInfo(id) {
      teacherApi.getTeacherInfoById(id)
        .then(response => { this.teacher = response.data.items })
    },

    // 按钮方法
    saveOrUpdate() {
      // 通过teacher对象中是否有ID判断是修改还是添加
      if (!this.teacher.id) {
        this.saveTeacher()
      } else {
        this.update()
      }
    },

    // 修改讲师信息
    update() {
      teacherApi.updateTeacher(this.teacher)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 返回讲师列表(重定向)
          this.$router.push({ path: '/teacher/table' })
        })
    },

    // 添加讲师
    saveTeacher() {
      teacherApi.addTeacher(this.teacher)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 返回讲师列表(重定向)
          this.$router.push({ path: '/teacher/table' })
        })
    },
    // 关闭上传头像的弹框,即弹框上面的X的触发事件
    close() {
      this.imagecropperShow = false
      // 初始化弹框
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 上传成功的方法，即弹框保存按钮的方法
    cropSuccess(data) {
      this.teacher.avatar = data.url
      // 初始化弹框
      this.imagecropperKey = this.imagecropperKey + 1
    }

  }
}
</script>

<style>

</style>
