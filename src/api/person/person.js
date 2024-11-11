import request from '../request';
import qs from 'qs';
export function getPerson() {

    return request({
        url: '/developer/getDeveloper',
        method: 'get',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        timeout: 120000, // 设置 90 秒超时
        // data: qs.stringify(data), // 将对象转换为 URL 编码字符串
    });
}
export function getList(data) {

    return request({
        url: '/developer/getUrlDeveloper',
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        data: qs.stringify(data), // 将对象转换为 URL 编码字符串


    });
}
export function getSingle(data) {

    return request({
        url: '/developer/getLoginDeveloper',
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        data: data,


    });
}