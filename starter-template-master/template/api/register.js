import request from '@/utils/request'

export default {

    // 注册
    registerMember(formitem) {
        return request({
            url: `/educenter/member/register`,
            method: 'post',
            data: formitem
        })
    }

}