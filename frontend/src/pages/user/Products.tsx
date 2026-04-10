import { useState, useEffect } from 'react'
import { Search, Package, Tag, Box, ShoppingCart, Loader2 } from 'lucide-react'
import clsx from 'clsx'
import { productApi } from '../../services/product'
import type { Product } from '../../types'

const categories = ['전체', '전자부품', '사무용품', '기계부품', '소프트웨어', '기타']

const statusStyles: Record<string, string> = {
  '판매중': 'badge-active',
  '품절': 'badge-pending',
  '단종': 'badge-closed',
}

export default function Products() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('전체')
  const [detail, setDetail] = useState<Product | null>(null)

  useEffect(() => {
    productApi.getList({ page: 0, size: 50 })
      .then(setProducts)
      .catch(e => setError(e.message))
      .finally(() => setLoading(false))
  }, [])

  const filtered = products.filter(p =>
    (category === '전체' || p.category?.name === category) &&
    p.name.includes(search)
  )

  if (loading) return <div className="flex justify-center py-20"><Loader2 className="animate-spin text-primary-500" size={32} /></div>
  if (error) return <div className="bg-red-50 text-red-600 rounded-xl p-4 text-sm">{error}</div>

  return (
    <div className="space-y-5">
      {/* Search & Filter */}
      <div className="flex items-center gap-2">
        <div className="relative w-72">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
          <input
            type="text"
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="상품명 검색..."
            className="input pl-9"
          />
        </div>
        <p className="ml-auto text-sm text-gray-500">
          총 <strong className="text-gray-900">{filtered.length}</strong>개 상품
        </p>
      </div>

      {/* Category filter */}
      <div className="flex items-center gap-2 flex-wrap">
        {categories.map(c => (
          <button
            key={c}
            onClick={() => setCategory(c)}
            className={clsx(
              'px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
              category === c
                ? 'bg-primary-600 text-white'
                : 'bg-white text-gray-600 border border-gray-200 hover:border-gray-300'
            )}
          >
            {c}
          </button>
        ))}
      </div>

      {/* Product Grid */}
      <div className="grid grid-cols-4 gap-4">
        {filtered.map(product => (
          <div
            key={product.productNo}
            onClick={() => setDetail(product)}
            className="bg-white rounded-xl border border-gray-200 overflow-hidden hover:shadow-md hover:border-gray-300 transition-all cursor-pointer"
          >
            <div className="h-40 bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center relative">
              {product.imageUrl
                ? <img src={product.imageUrl} alt={product.name} className="w-full h-full object-cover" />
                : <Package size={48} className="text-gray-200" />
              }
              <div className="absolute top-2 right-2">
                <span className={statusStyles[product.productStatus?.name ?? '판매중']}>
                  {product.productStatus?.name ?? '판매중'}
                </span>
              </div>
            </div>

            <div className="p-4">
              <h3 className="font-semibold text-gray-900 text-sm">{product.name}</h3>
              <p className="text-xs text-gray-400 mb-2 line-clamp-1">{product.content}</p>
              <div className="flex gap-1 mb-3">
                <span className="inline-flex items-center gap-1 text-xs text-gray-500 bg-gray-50 px-2 py-0.5 rounded-md">
                  <Tag size={10} /> {product.category?.name}
                </span>
                <span className="inline-flex items-center gap-1 text-xs text-gray-500 bg-gray-50 px-2 py-0.5 rounded-md">
                  <Box size={10} /> {product.unit?.name}
                </span>
              </div>
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-xs text-gray-400">{product.manufacturer?.name}</p>
                  <p className="text-base font-bold text-gray-900">₩{product.price.toLocaleString()}</p>
                </div>
                <button
                  onClick={e => { e.stopPropagation(); }}
                  className="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center hover:bg-primary-700 transition-colors"
                >
                  <ShoppingCart size={14} className="text-white" />
                </button>
              </div>
            </div>
          </div>
        ))}
        {filtered.length === 0 && (
          <div className="col-span-4 py-20 text-center text-gray-400">
            <Package size={36} className="mx-auto mb-2 text-gray-200" />
            <p className="text-sm">상품이 없습니다</p>
          </div>
        )}
      </div>

      {/* Product Detail Modal */}
      {detail && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">상품 상세</h2>
              <button onClick={() => setDetail(null)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="h-48 bg-gray-50 rounded-xl flex items-center justify-center">
                {detail.imageUrl
                  ? <img src={detail.imageUrl} alt={detail.name} className="h-full object-contain rounded-xl" />
                  : <Package size={56} className="text-gray-200" />
                }
              </div>
              <div>
                <h3 className="text-xl font-bold text-gray-900">{detail.name}</h3>
                <p className="text-sm text-gray-500 mt-1">{detail.content}</p>
              </div>
              <div className="grid grid-cols-2 gap-3 text-sm">
                {[
                  { label: '카테고리', value: detail.category?.name },
                  { label: '제조사', value: detail.manufacturer?.name },
                  { label: '단위', value: detail.unit?.name },
                  { label: '상태', value: detail.productStatus?.name },
                ].map(({ label, value }) => (
                  <div key={label} className="bg-gray-50 rounded-xl p-3">
                    <p className="text-xs text-gray-500 mb-1">{label}</p>
                    <p className="font-semibold text-gray-900">{value ?? '-'}</p>
                  </div>
                ))}
              </div>
              <div className="flex items-center justify-between bg-primary-50 rounded-xl p-4">
                <div>
                  <p className="text-xs text-primary-600">단가</p>
                  <p className="text-2xl font-bold text-primary-700">₩{detail.price.toLocaleString()}</p>
                </div>
                <button className="btn-primary flex items-center gap-2">
                  <ShoppingCart size={16} /> 주문하기
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
