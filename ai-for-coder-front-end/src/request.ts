import axios from 'axios'
import type {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError,
  InternalAxiosRequestConfig
} from 'axios'

// 定义响应数据结构
export interface ApiResponse<T = any> {
  code: number
  data: T
  message: string
  success: boolean
}

// 请求配置
const REQUEST_CONFIG = {
  // 全局接口请求地址
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8389/ai-for-coder/',
  // 超时时间（毫秒）
  timeout: 10000,
  // 请求头配置
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  withCredentials: true,
}

// 创建axios实例
const request: AxiosInstance = axios.create(REQUEST_CONFIG)

// 请求拦截器
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 在发送请求之前做些什么
    console.log('发起请求:', config)

    // 添加认证token
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers['Authorization'] = `Bearer ${token}`
    }

    // 添加请求时间戳（防止缓存）
    if (config.method === 'get' && config.params) {
      config.params._t = Date.now()
    }

    return config
  },
  (error: AxiosError) => {
    // 对请求错误做些什么
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    // 对响应数据做点什么
    console.log('响应数据:', response)

    const { data, status } = response

    // HTTP状态码检查
    if (status >= 200 && status < 300) {
      // 业务状态码检查
      if (data.success || data.code === 0 || data.code === 200) {
        return response
      } else {
        // 业务错误处理
        const errorMsg = data.message || '请求失败'
        console.error('业务错误:', errorMsg)

        // 根据不同的业务错误码进行处理
        switch (data.code) {
          case 401:
            // 未授权，清除token并跳转登录
            localStorage.removeItem('token')
            window.location.href = '/login'
            break
          case 403:
            console.error('权限不足')
            break
          case 404:
            console.error('资源不存在')
            break
          case 500:
            console.error('服务器内部错误')
            break
          default:
            console.error('未知错误:', errorMsg)
        }

        return Promise.reject(new Error(errorMsg))
      }
    }

    return response
  },
  (error: AxiosError<ApiResponse>) => {
    // 对响应错误做点什么
    console.error('响应错误:', error)

    let errorMessage = '网络请求失败'

    if (error.response) {
      // 服务器返回了错误状态码
      const { status, data } = error.response

      switch (status) {
        case 400:
          errorMessage = data?.message || '请求参数错误'
          break
        case 401:
          errorMessage = '未授权，请重新登录'
          // 清除token并跳转登录
          localStorage.removeItem('token')
          window.location.href = '/login'
          break
        case 403:
          errorMessage = '权限不足'
          break
        case 404:
          errorMessage = '请求的资源不存在'
          break
        case 405:
          errorMessage = '请求方法不允许'
          break
        case 408:
          errorMessage = '请求超时'
          break
        case 500:
          errorMessage = '服务器内部错误'
          break
        case 502:
          errorMessage = '网关错误'
          break
        case 503:
          errorMessage = '服务不可用'
          break
        case 504:
          errorMessage = '网关超时'
          break
        default:
          errorMessage = data?.message || `请求失败 (${status})`
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应
      if (error.code === 'ECONNABORTED') {
        errorMessage = '请求超时，请稍后重试'
      } else if (error.message.includes('Network Error')) {
        errorMessage = '网络连接失败，请检查网络'
      } else {
        errorMessage = '网络请求失败，请稍后重试'
      }
    } else {
      // 其他错误
      errorMessage = error.message || '未知错误'
    }

    // 这里可以集成全局消息提示组件
    // message.error(errorMessage)

    return Promise.reject(new Error(errorMessage))
  }
)

// 导出请求实例和常用方法
export default request

// 具名导出，用于生成的API文件
export { request }

// 常用请求方法封装
export const api = {
  // GET请求
  get<T = any>(url: string, params?: any, config?: AxiosRequestConfig): Promise<ApiResponse<T>> {
    return request.get(url, { params, ...config }).then(res => res.data)
  },

  // POST请求
  post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<ApiResponse<T>> {
    return request.post(url, data, config).then(res => res.data)
  },

  // PUT请求
  put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<ApiResponse<T>> {
    return request.put(url, data, config).then(res => res.data)
  },

  // DELETE请求
  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<ApiResponse<T>> {
    return request.delete(url, config).then(res => res.data)
  },

  // 文件上传
  upload<T = any>(url: string, file: File, config?: AxiosRequestConfig): Promise<ApiResponse<T>> {
    const formData = new FormData()
    formData.append('file', file)
    return request.post(url, formData, {
      ...config,
      headers: {
        'Content-Type': 'multipart/form-data',
        ...config?.headers
      }
    }).then(res => res.data)
  }
}

// 请求类型定义
export interface RequestConfig extends AxiosRequestConfig {
  // 是否显示加载状态
  loading?: boolean
  // 是否显示错误提示
  showError?: boolean
}
