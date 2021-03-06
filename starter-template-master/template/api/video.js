import request from '@/utils/request'

export default {

    // 根据视频ID获取视频的凭证
    getPlayAuth(vid) {
        return request({
            url: `/eduvod/video/getPlayAuth/${vid}`,
            method: 'get'
        })
    }

}