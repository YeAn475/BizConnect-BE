import { request, requestForm } from './api'
import type { User } from '../types'

export interface UpdateProfileRequest {
  name?: string
  phoneNumber?: string
  address?: string
  positionNo?: number
}

export interface ChangePasswordRequest {
  currentPassword: string
  newPassword: string
}

export const userApi = {
  getProfile: () =>
    request<User>('/user/profile'),

  updateProfile: (body: UpdateProfileRequest) =>
    request<User>('/user/profile', { method: 'PATCH', body: JSON.stringify(body) }),

  changePassword: (body: ChangePasswordRequest) =>
    request('/user/password', { method: 'PUT', body: JSON.stringify(body) }),

  toggleProfileVisibility: () =>
    request('/user/prifle/change', { method: 'PUT' }),

  deleteAccount: () =>
    request('/user/account/delete', { method: 'PUT' }),

  uploadProfileImage: (file: File) => {
    const form = new FormData()
    form.append('image', file)
    return requestForm<User>('/user/profile-image', form)
  },
}
