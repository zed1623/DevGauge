import request from '../request';
import qs from 'qs';

export function analyseUrl(data) {

    return request({
        url: '/project/analyseUrl',
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        data: qs.stringify(data), // 将对象转换为 URL 编码字符串
    });
}
export function showUrl(data) {

    return request({
        url: '/project/selectUrl',
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        data: qs.stringify(data), // 将对象转换为 URL 编码字符串
    });
}