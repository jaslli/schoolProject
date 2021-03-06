import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie'

// 创建axios实例
const service = axios.create({
    // api的baseURL
    baseURL: 'http://localhost:9001',
    // 请求的超时时间
    timeout: 20000
})

// Http request 拦截器
service.interceptors.request.use(
    config => {
        // 将token放入头部，接口中获取信息的方法是从头部获取的token
        if (cookie.get('school_token')) {
            config.headers['token'] = cookie.get('school_token');
        }
        return config
    },
    err => {
    return Promise.reject(err);
})

export default service