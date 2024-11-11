import request from '../request';
// import qs from 'qs';

export function aiUrl() {

    return request({
        url: '/ai/aiChat',
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
        timeout: 120000,
        // data: qs.stringify(data), // 将对象转换为 URL 编码字符串
    });
}