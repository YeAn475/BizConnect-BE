import { request } from './api'

export const companyAlarmApi = {
  approve: (requestId: number) =>
    request(`/companyAlarm/${requestId}/approve`, { method: 'POST' }),

  reject: (requestId: number, reason?: string) =>
    request(`/companyAlarm/${requestId}/reject`, {
      method: 'PUT',
      body: JSON.stringify({ reason }),
    }),
}
