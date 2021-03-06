<template>
  <div class="app-container">
    <!-- 标题 -->
    <h2 style="text-align: center;">发布新课程</h2>
    <!-- 步骤条 -->
    <el-steps :active="2" process-status="wait" align-center style="marginbottom: 40px;">
      <el-step title="填写课程的基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终保存" />
    </el-steps>

    <!-- 添加章节按钮 -->
    <el-button type="primary" @click="openchapter()">添加按钮</el-button>

    <!-- 章节的弹框表单 -->
    <el-dialog title="章节信息" center="true" :visible.sync="dialogFormVisible1">
      <el-form :model="chapterInfo">

        <el-form-item label="章节标题" :label-width="formLabelWidth">
          <el-input v-model="chapterInfo.title" autocomplete="off" />
        </el-form-item>

        <el-form-item label="章节排序" :label-width="formLabelWidth">
          <el-input-number v-model="chapterInfo.sort" :min="0" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 小节的弹框表单 -->

    <el-dialog :visible.sync="dialogFormVisible2" title="添加课时">
      <el-form :model="videoInfo" label-width="120px">

        <el-form-item label="课时标题">
          <el-input v-model="videoInfo.title" />
        </el-form-item>

        <el-form-item label="课时排序">
          <el-input-number v-model="videoInfo.sort" :min="0" controlsposition="right" />
        </el-form-item>

        <el-form-item label="是否免费">
          <el-radio-group v-model="videoInfo.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            ref="upload"
            :auto-upload="false"
            :on-error="handleUploadError"
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadVideo'"
            :limit="1"
            class="upload-demo"
          >
            <!-- <el-button size="small" type="primary">上传视频</el-button> -->
            <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
            <el-button :disabled="uploadBtnDisabled" style="margin-left: 10px;" size="small" type="success" @click="submitUpload()">上传</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，一个小节只支持上传一个视频<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button @click="openVideo(chapter.id)">添加小节</el-button>
            <el-button type="primary" @click="initChapter(chapter.id)">编辑</el-button>
            <el-button type="danger" @click="deleteChapter(chapter.id)">删除</el-button>
          </span>
        </p>
        <!-- 小节 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button type="primary" @click="initVideo(video.id)">编辑</el-button>
                <el-button type="danger" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>

      </li>
    </ul>
    <!-- 跳转按钮 -->
    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">返回上一步修改</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import courseApi from '@/api/edu/course'
export default {
  data() {
    return {
      uploadBtnDisabled: false, // 上传按钮
      fileList: [], // 上传文件列表
      BASE_API: process.env.VUE_APP_BASE_API, // 接口API地址
      saveBtnDisabled: false, // 保存按钮是否禁用
      chapterList: [], // 课程章节数据
      courseId: '', // 课程ID
      chapterId: '', // 章节ID
      dialogFormVisible1: false, // 章节弹框
      dialogFormVisible2: false, // 小节弹框
      formLabelWidth: '120px', // 添加表单的宽度
      chapterInfo: { // 章节弹框内容
        title: '',
        sort: 0
      },
      videoInfo: { // 小节弹框内容
        title: '',
        sort: 0,
        free: 0,
        videoSourceId: '', // 视频ID
        videoOriginalName: '' // 视频名字
      }
    }
  },
  created() {
    //  判断路由是否有ID
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      this.getChapterVideo()
    }
  },
  methods: {
    // ************************小节的一些方法*********************************
    // 视频上传成功的方法
    handleVodUploadSuccess(response, file, fileList) {
      this.uploadBtnDisabled = false
      if (response.success) {
        // 上传视频id赋值
        this.videoInfo.videoSourceId = response.data.videoId
        // 上传视频名称赋值
        this.videoInfo.videoOriginalName = file.name
      } else {
        this.$message.error('上传失败（非20000）')
      }
    },

    // 上传失败
    handleUploadError() {
      this.uploadBtnDisabled = false
      this.$message.error('上传失败（http）')
    },

    // 上传多于一个的视频
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },

    // 删除目前文件列表中的视频
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}吗？`)
    },
    // 删除视频
    handleVodRemove() {
      courseApi.deleteVideoById(this.videoInfo.videoSourceId)
        .then(response => {
          // 删除成功的提示
          this.$message({
            type: 'success',
            message: '删除视频成功！'
          })
          // 清空文件列表
          this.fileList = []
          // 删除小节信息中视频信息清空
          this.videoInfo.videoSourceId = ''
          this.videoInfo.videoOriginalName = ''
          // 删除视频的同时更新video数据信息
          courseApi.updateVideo(this.videoInfo)
        })
    },

    // 上传按钮
    submitUpload() {
      this.uploadBtnDisabled = true// 禁用按钮
      this.$refs.upload.submit() // 提交上传请求
    },

    // ************************小节的一些方法*********************************
    // 添加小节按钮的方法
    openVideo(chapterId) {
      // 弹框
      this.dialogFormVisible2 = true
      // 表单数据清空
      this.videoInfo = {}
      this.fileList = []
      // 设置章节id
      this.videoInfo.chapterId = chapterId
    },
    // 删除小节
    deleteVideo(videoId) {
      this.$confirm('将要删除该小节，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        courseApi.deleteVideo(videoId).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          // 初始化列表数据
          this.getChapterVideo()
        })
      }).catch(() => {})
    },
    // 编辑小节的数据回显
    initVideo(videoId) {
      this.fileList = []
      this.dialogFormVisible2 = true
      if (videoId) {
        courseApi.getVideoById(videoId)
          .then(response => {
            this.videoInfo = response.data.video
            // 回显
            if (this.videoInfo.videoOriginalName) {
              this.fileList = [{ 'name': this.videoInfo.videoOriginalName }]
            }
          })
      }
    },
    // 添加小节
    addVideo() {
      // 获取课程和章节ID
      this.videoInfo.courseId = this.courseId
      courseApi.addVideo(this.videoInfo).then(response => {
        // 关闭表单
        this.dialogFormVisible2 = false
        // 提示信息
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        // 刷新数据
        this.getChapterVideo()
      })
    },

    // 修改小节
    updateVideo() {
      courseApi.updateVideo(this.videoInfo)
        .then(response => {
          // 关闭弹框
          this.dialogFormVisible2 = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          // 刷新页面数据
          this.getChapterVideo()
        })
    },

    // 添加或者是保存小节的按钮方法
    saveOrUpdateVideo() {
      if (!this.videoInfo.id) {
        this.addVideo()
      } else {
        this.updateVideo()
      }
    },
    // ************************章节的一些方法*********************************
    // 添加章节的按钮
    openchapter() {
      // 弹框
      this.dialogFormVisible1 = true
      // 清除数据
      this.chapterInfo = {}
    },
    // 删除章节
    deleteChapter(chapterId) {
      this.$confirm('将要删除该章节，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        courseApi.deleteChapter(chapterId).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getChapterVideo()
        })
      }).catch(() => {})
    },
    // 修改章节
    updateChapter() {
      courseApi.updateChapter(this.chapterInfo)
        .then(response => {
          // 关闭弹框
          this.dialogFormVisible1 = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          // 刷新页面,即重新查询章节和小节
          this.getChapterVideo()
          // 清空表单
          this.chapterInfo.title = ''
          this.chapterInfo.sort = 0
        })
    },
    // 修改章节表单的数据回显
    initChapter(chapterId) {
      this.dialogFormVisible1 = true
      courseApi.getChapterById(chapterId)
        .then(response => {
          this.chapterInfo = response.data.chapter
        })
    },
    // 添加章节
    addChapter() {
      this.chapterInfo.courseId = this.courseId
      courseApi.addChapter(this.chapterInfo)
        .then(response => {
          // 关闭弹框
          this.dialogFormVisible1 = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加成功'
          })
          // 刷新页面,即重新查询章节和小节
          this.getChapterVideo()
          // 清空表单
          this.chapterInfo.title = ''
          this.chapterInfo.sort = 0
        })
    },

    // 整合修改和添加的按钮
    saveOrUpdate() {
      if (!this.chapterInfo.id) {
        this.addChapter()
      } else {
        this.updateChapter()
      }
    },

    // 根据课程ID查询章节和小节
    getChapterVideo() {
      courseApi.getAllChapterInfo(this.courseId)
        .then(response => {
          this.chapterList = response.data.allChapterVideo
        })
    },
    // ************************跳转按钮的方法*********************************
    // 上一步按钮的方法
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/info/' + this.courseId })
    },
    // 下一步按钮的方法
    next() {
      console.log('previous')
      this.$router.push({ path: '/course/publish/' + this.courseId })
    }
  }
}
</script>

<style scoped>
.chanpterList{
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;

}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}
.videoList{
  padding-left: 50px;
}
.videoList p{
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>
