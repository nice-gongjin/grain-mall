import axios from 'axios';
import store from '../store/index'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 20000, // 请求超时时间
  headers:{
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization':"Bearer " + store.state.Authorization,
  },
  withCredentials: true
});

// request拦截器
service.interceptors.request.use(config => {
  config.headers.Authorization="Bearer "+ store.state.Authorization;//重新设置鉴权
  return config;
}, error => {
  Promise.reject(error);
});

export default service;
