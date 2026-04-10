import { request, setToken, removeToken } from './api'

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken?: string
  role: string
}

export interface SignUpRequest {
  email: string
  password: string
  name: string
  phoneNumber: string
  address?: string
}

export const authApi = {
  login: async (body: LoginRequest): Promise<LoginResponse> => {
    const res = await request<LoginResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(body),
    })
    setToken(res.accessToken)
    localStorage.setItem('role', res.role ?? 'USER')
    return res
  },

  signup: (body: SignUpRequest) =>
    request('/user/signup', { method: 'POST', body: JSON.stringify(body) }),

  logout: async () => {
    try {
      await request('/auth/logout', { method: 'POST' })
    } finally {
      removeToken()
    }
  },

  refresh: async () => {
    const res = await request<{ accessToken: string }>('/auth/refresh', { method: 'POST' })
    setToken(res.accessToken)
    return res
  },
}
