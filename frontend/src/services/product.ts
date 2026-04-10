import { request, requestForm } from './api'
import type { Product } from '../types'

export interface ProductListRequest {
  page?: number
  size?: number
  categoryNo?: number
  keyword?: string
}

export interface CreateProductRequest {
  name: string
  content: string
  price: number
  categoryNo: number
  unitNo: number
  manufacturerNo: number
}

export const productApi = {
  getList: (body: ProductListRequest = {}) =>
    request<Product[]>('/product/list', {
      method: 'POST',
      body: JSON.stringify(body),
    }),

  getDetail: (productNo: number) =>
    request<Product>(`/product/${productNo}`, { method: 'POST' }),

  create: (body: CreateProductRequest) =>
    request<Product>('/product/', { method: 'POST', body: JSON.stringify(body) }),

  uploadImage: (productNo: number, file: File) => {
    const form = new FormData()
    form.append('image', file)
    return requestForm<Product>(`/product/${productNo}/image`, form)
  },
}
