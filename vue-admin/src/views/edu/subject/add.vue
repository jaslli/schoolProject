<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">这里使用excel来建立分类,格式需要严格要求,详情请看模板文件</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/public/template.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/eduservice/subject/addSuject'"
          name="file"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button :loading="loading" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">{{ fileUploadBtnText }}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API,
      fileUploadBtnText: '上传到服务器', // 按钮的文字
      importBtnDisabled: false, // 按钮是否禁用
      loading: false
    }
  },

  created() {
  },

  methods: {
    // 上传按钮的上传方法
    submitUpload() {
      this.importBtnDisabled = true
      this.loading = true
      this.$refs.upload.submit()
    },
    // 上传成功后的方法
    fileUploadSuccess() {
      // 提示成功信息
      this.loading = false
      this.$message({
        type: 'success',
        message: '添加分类成功'
      })
      // 跳转到列表分类
      this.$router.push({ path: '/subject/list' })
    },
    // 上传失败后的方法
    fileUploadError() {
      // 提示失败信息
      this.loading = false
      this.$message({
        type: 'error',
        message: '添加失败'
      })
    }
  }
}
</script>

<style>

</style>
