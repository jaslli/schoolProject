// eslint-disable-next-line no-unused-vars
import request from '@/utils/request'

export default {
  // 条件查询讲师
  getTeacherList(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },

  // 查询所有的讲师
  getAllTeacherList() {
    return request({
      url: '/eduservice/teacher/findAll',
      method: 'get'
    })
  },

  // 删除讲师
  deleteTeacherById(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    })
  },

  // 添加讲师
  addTeacher(EduTeacher) {
    return request({
      url: `/eduservice/teacher/addTeacher`,
      method: 'post',
      data: EduTeacher
    })
  },

  // 根据ID查询讲师信息
  getTeacherInfoById(id) {
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method: 'get'
    })
  },

  // 修改讲师
  updateTeacher(teacher) {
    return request({
      url: `/eduservice/teacher/updateTeacher`,
      method: 'post',
      data: teacher
    })
  }

}
