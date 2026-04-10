import { request } from './api'
import type { Company, CompanyJoinRequest } from '../types'

export interface CompanyRegisterRequest {
  name: string
  phoneNumber: string
  address: string
  affiliationNo: number
  branchNo?: number
}

export interface UpdateCompanyRequest {
  name?: string
  phoneNumber?: string
  address?: string
}

export const companyApi = {
  getList: () =>
    request<Company[]>('/company/list'),

  requestRegister: (body: CompanyRegisterRequest) =>
    request('/company/request', { method: 'POST', body: JSON.stringify(body) }),

  update: (body: UpdateCompanyRequest) =>
    request<Company>('/company/', { method: 'PATCH', body: JSON.stringify(body) }),

  addAccount: (accountNumber: string) =>
    request('/company/account', { method: 'POST', body: JSON.stringify({ number: accountNumber }) }),

  addRegistration: (businessNumber: string) =>
    request('/company/registration', { method: 'POST', body: JSON.stringify({ number: businessNumber }) }),
}
