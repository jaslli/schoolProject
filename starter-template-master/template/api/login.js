import request from '@/utils/request'

export default {

    // 登陆
    login(userInfo) {
        return request({
            url: `/educenter/member/login`,
            method: 'post',
            data: userInfo
        })
    },

    // 根据Token获取用户信息
    getInfo() {
        return request({
            url: `/educenter/member/getMemberInfo`,
            method: 'get'
        })
    }

}