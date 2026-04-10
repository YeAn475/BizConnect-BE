const BASE_URL = '/api'

function getToken(): string | null {
  return localStorage.getItem('accessToken')
}

export function setToken(token: string) {
  localStorage.setItem('accessToken', token)
}

export function removeToken() {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('role')
}

export async function request<T>(
  path: string,
  options: RequestInit = {}
): Promise<T> {
  const token = getToken()

  const headers: HeadersInit = {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...(options.headers ?? {}),
  }

  const res = await fetch(`${BASE_URL}${path}`, { ...options, headers })

  if (res.status === 401) {
    removeToken()
    window.location.href = '/login'
    throw new Error('인증이 만료되었습니다. 다시 로그인해주세요.')
  }

  if (!res.ok) {
    let message = `HTTP ${res.status}`
    try {
      const body = await res.json()
      message = body.message ?? body.error ?? message
    } catch {}
    throw new Error(message)
  }

  // 204 No Content
  if (res.status === 204) return undefined as T

  return res.json()
}

/** multipart/form-data 전용 (Content-Type 헤더를 직접 설정하지 않음) */
export async function requestForm<T>(
  path: string,
  formData: FormData,
  method = 'POST'
): Promise<T> {
  const token = getToken()

  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: token ? { Authorization: `Bearer ${token}` } : {},
    body: formData,
  })

  if (res.status === 401) {
    removeToken()
    window.location.href = '/login'
    throw new Error('인증이 만료되었습니다.')
  }

  if (!res.ok) {
    let message = `HTTP ${res.status}`
    try {
      const body = await res.json()
      message = body.message ?? body.error ?? message
    } catch {}
    throw new Error(message)
  }

  if (res.status === 204) return undefined as T
  return res.json()
}
