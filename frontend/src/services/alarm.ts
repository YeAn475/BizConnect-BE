import { request } from './api'
import type { Alarm } from '../types'

export const alarmApi = {
  getList: () =>
    request<Alarm[]>('/alarm/list'),

  markRead: (alarmNo: number) =>
    request(`/alarm/read`, {
      method: 'PATCH',
      body: JSON.stringify({ alarmNo }),
    }),

  delete: (alarmNo: number) =>
    request(`/alarm/delete`, {
      method: 'PATCH',
      body: JSON.stringify({ alarmNo }),
    }),
}
