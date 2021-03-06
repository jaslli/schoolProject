import request from '@/utils/request'

export default {
  /*          课程部分                 */

  // 删除视频
  deleteVideoById(videoId) {
    return request({
      url: '/eduvod/video/deleteVideo/' + videoId,
      method: 'delete'
    })
  },

  /*          课程部分                 */

  // 添加课程信息
  addCourseInfo(coureseInfo) {
    return request({
      url: '/eduservice/course/addCourseInfo',
      method: 'post',
      data: coureseInfo
    })
  },

  // 根据课程ID查询章节和小节
  getAllChapterInfo(courseId) {
    return request({
      url: '/eduservice/chapter/getChapterInfo/' + courseId,
      method: 'get'
    })
  },

  // 根据课程ID查询课程基本信息
  getCourseInfoById(courseId) {
    return request({
      url: '/eduservice/course/getCourseInfo/' + courseId,
      method: 'get'
    })
  },

  // 根据课程ID修改课程基本信息
  updateCourseInfo(coureseInfo) {
    return request({
      url: '/eduservice/course/updateCourseInfo',
      method: 'post',
      data: coureseInfo
    })
  },

  // 根据课程ID查询课程确认信息
  getCourse(courseId) {
    return request({
      url: '/eduservice/course/getCourse/' + courseId,
      method: 'get'
    })
  },

  // 课程的最终发布
  publishCourse(courseId) {
    return request({
      url: '/eduservice/course/updateCourseStatus/' + courseId,
      method: 'post'
    })
  },

  // 条件查询带分页
  getPageList(page, limit, course) {
    return request({
      url: '/eduservice/course/queryGetInfo/' + page + '/' + limit,
      method: 'post',
      data: course
    })
  },

  // 删除课程
  deleteCourse(courseId) {
    return request({
      url: '/eduservice/course/deleteCourse/' + courseId,
      method: 'delete'
    })
  },

  /*              分类部分                 */

  // 查询分类数据
  getSubjectList() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },

  /*              章节部分                 */

  // 添加章节
  addChapter(chapter) {
    return request({
      url: '/eduservice/chapter/addChapter',
      method: 'post',
      data: chapter
    })
  },

  // 根据ID查询章节内容
  getChapterById(chapterId) {
    return request({
      url: '/eduservice/chapter/getChapterById/' + chapterId,
      method: 'get'
    })
  },

  // 修改章节内容
  updateChapter(chapter) {
    return request({
      url: '/eduservice/chapter/updateChapter',
      method: 'post',
      data: chapter
    })
  },

  // 删除章节
  deleteChapter(chapterId) {
    return request({
      url: '/eduservice/chapter/deleteChapter/' + chapterId,
      method: 'delete'
    })
  },
  /*              小节部分                 */

  // 添加小节
  addVideo(video) {
    return request({
      url: '/eduservice/video/addVideo',
      method: 'post',
      data: video
    })
  },

  // 删除小节
  deleteVideo(videoId) {
    return request({
      url: '/eduservice/video/deleteVideo/' + videoId,
      method: 'delete'
    })
  },

  // 根据小节ID查询小节数据
  getVideoById(videoId) {
    return request({
      url: '/eduservice/video/getVideoById/' + videoId,
      method: 'get'
    })
  },

  // 修改小节
  updateVideo(video) {
    return request({
      url: '/eduservice/video/updateVideo',
      method: 'post',
      data: video
    })
  }

}
