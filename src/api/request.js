// request.js
import axios from 'axios';

// 创建 axios 实例
const request = axios.create({
    baseURL: 'http://47.113.195.131:8090',
    timeout: 10000, // 设置超时时间
    headers: {
        'Content-Type': 'application/json',
    }
});


export default request;
