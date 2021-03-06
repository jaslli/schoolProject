<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="搜索分类" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>

import courseApi from '@/api/edu/course'

export default {
  data() {
    return {
      filterText: '',
      data2: [], // 分类数据
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.getAllSubjectList()
  },

  methods: {
    // 查询分类
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    // 获取分类数据
    getAllSubjectList() {
      courseApi.getSubjectList()
        .then(response => {
          this.data2 = response.data.list
        })
    }
  }
}
</script>

