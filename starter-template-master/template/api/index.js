import request from '@/utils/request'

export default {
    //--------------------首页-------------------------
    // 获取三条Banner数据
    getBanner() {
        return request({
            url: `/educms/bannerfront/find`,
            method: 'get'
        })
    },

    // 获取八条课程数据和四条讲师数据
    getData() {
        return request({
            url: `/eduservice/indexfront/index`,
            method: 'get'
        })
    },

    //--------------------讲师页-------------------------
    // 分页查询讲师
    getTeacherList(page, limit) {
        return request({
            url: `/eduservice/teacherFront/getTeacherFrontList/${page}/${limit}`,
            method: 'post'
        })
    },

    // 根据讲师ID查询讲师信息和课程信息
    getTeacherInfo(teacherid) {
        return request({
            url: `/eduservice/teacherFront/getTeacherFrontInfo/${teacherid}`,
            method: 'get'
        })
    },
    //--------------------课程页-------------------------
    // 分页条件查询课程信息
    getCourseList(page, limit, QueryCourse) {
        return request({
            url: `/eduservice/courseFront/getFrontCourseList/${page}/${limit}`,
            method: 'post',
            data: QueryCourse
        })
    },

    // 查询所有分类数据
    getAllSubject() {
        return request({
            url: `/eduservice/subject/getAllSubject`,
            method: 'get'
        })
    },

    // 查询课程详情信息
    getCourseInfo(courseId) {
        return request({
            url: `/eduservice/courseFront/getFrontCourseInfo/${courseId}`,
            method: 'get'
        })
    },

    // 根据课程ID查询课程的评论
    getComment(courseId,current,limit) {
        return request({
            url: `/eduservice/comment/getComment/${courseId}/${current}/${limit}`,
            method: 'get'
        })
    },

    // 添加评论
    addComment(commentInfo) {
        return request({
            url: `/eduservice/comment/addComment`,
            method: 'post',
            data: commentInfo
        })
    }

}