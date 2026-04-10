import { useState, useEffect } from 'react'
import { Search, Plus, Edit2, Trash2, Package, Tag, Box, Image, Loader2 } from 'lucide-react'
import clsx from 'clsx'
import { productApi } from '../../services/product'
import type { Product } from '../../types'

const categories = ['전체', '전자부품', '사무용품', '기계부품', '소프트웨어', '기타']

const statusStyles: Record<string, string> = {
  '판매중': 'badge-active',
  '품절': 'badge-pending',
  '단종': 'badge-closed',
}

export default function AdminProducts() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('전체')
  const [showModal, setShowModal] = useState(false)
  const [editTarget, setEditTarget] = useState<Product | null>(null)
  const [form, setForm] = useState({ name: '', content: '', price: '', categoryNo: '1', unitNo: '1', manufacturerNo: '1' })
  const [imageFile, setImageFile] = useState<File | null>(null)
  const [submitting, setSubmitting] = useState(false)

  useEffect(() => {
    fetchProducts()
  }, [])

  const fetchProducts = async () => {
    try {
      setLoading(true)
      const data = await productApi.getList({ page: 0, size: 50 })
      setProducts(data)
    } catch (e: any) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setSubmitting(true)
    try {
      const created = await productApi.create({
        name: form.name,
        content: form.content,
        price: Number(form.price),
        categoryNo: Number(form.categoryNo),
        unitNo: Number(form.unitNo),
        manufacturerNo: Number(form.manufacturerNo),
      })
      if (imageFile) {
        await productApi.uploadImage(created.productNo, imageFile)
      }
      await fetchProducts()
      setShowModal(false)
      setForm({ name: '', content: '', price: '', categoryNo: '1', unitNo: '1', manufacturerNo: '1' })
    } catch (e: any) {
      alert('상품 등록에 실패했습니다: ' + e.message)
    } finally {
      setSubmitting(false)
    }
  }

  const filtered = products.filter(p =>
    (category === '전체' || p.category?.name === category) &&
    p.name.includes(search)
  )

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="relative w-72">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
            <input type="text" value={search} onChange={e => setSearch(e.target.value)} placeholder="상품명 검색..." className="input pl-9" />
          </div>
        </div>
        <button onClick={() => { setEditTarget(null); setShowModal(true) }} className="btn-primary flex items-center gap-2">
          <Plus size={16} /> 상품 등록
        </button>
      </div>

      <div className="flex gap-2">
        {categories.map(c => (
          <button key={c} onClick={() => setCategory(c)}
            className={clsx('px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
              category === c ? 'bg-primary-600 text-white' : 'bg-white text-gray-600 border border-gray-200 hover:border-gray-300'
            )}>
            {c}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="flex justify-center py-20">
          <Loader2 className="animate-spin text-primary-500" size={32} />
        </div>
      ) : error ? (
        <div className="bg-red-50 text-red-600 rounded-xl p-4 text-sm">{error}</div>
      ) : (
        <div className="grid grid-cols-4 gap-4">
          {filtered.map(product => (
            <div key={product.productNo} className="bg-white rounded-xl border border-gray-200 overflow-hidden hover:shadow-md transition-all group">
              <div className="h-40 bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center relative">
                {product.imageUrl
                  ? <img src={product.imageUrl} alt={product.name} className="w-full h-full object-cover" />
                  : <Package size={48} className="text-gray-200" />
                }
                <div className="absolute top-2 right-2">
                  <span className={statusStyles[product.productStatus?.name ?? '판매중']}>{product.productStatus?.name ?? '판매중'}</span>
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
                  <div className="flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                    <button onClick={() => { setEditTarget(product); setShowModal(true) }}
                      className="p-1.5 rounded-lg hover:bg-gray-100 text-gray-500">
                      <Edit2 size={14} />
                    </button>
                    <button className="p-1.5 rounded-lg hover:bg-red-50 text-red-400">
                      <Trash2 size={14} />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
          {filtered.length === 0 && (
            <div className="col-span-4 py-20 text-center text-gray-400">
              <Package size={36} className="mx-auto mb-2 text-gray-200" />
              <p className="text-sm">등록된 상품이 없습니다</p>
            </div>
          )}
        </div>
      )}

      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">{editTarget ? '상품 수정' : '새 상품 등록'}</h2>
              <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="p-6 space-y-4">
                <label className="flex items-center justify-center border-2 border-dashed border-gray-200 rounded-xl h-28 cursor-pointer hover:border-primary-400 hover:bg-primary-50 transition-colors">
                  <div className="text-center text-gray-400">
                    <Image size={24} className="mx-auto mb-1" />
                    <p className="text-xs">{imageFile ? imageFile.name : '이미지 업로드'}</p>
                  </div>
                  <input type="file" accept="image/*" className="hidden" onChange={e => setImageFile(e.target.files?.[0] ?? null)} />
                </label>
                <div className="grid grid-cols-2 gap-3">
                  <div className="col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-1">상품명 *</label>
                    <input type="text" required value={form.name} onChange={e => setForm(f => ({ ...f, name: e.target.value }))} className="input" placeholder="상품명" />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">카테고리</label>
                    <select className="input" value={form.categoryNo} onChange={e => setForm(f => ({ ...f, categoryNo: e.target.value }))}>
                      <option value="1">전자부품</option>
                      <option value="2">사무용품</option>
                      <option value="3">기계부품</option>
                      <option value="4">소프트웨어</option>
                    </select>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">단위</label>
                    <select className="input" value={form.unitNo} onChange={e => setForm(f => ({ ...f, unitNo: e.target.value }))}>
                      <option value="1">개</option>
                      <option value="2">박스</option>
                      <option value="3">팩</option>
                      <option value="4">세트</option>
                    </select>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">단가 *</label>
                    <input type="number" required min="0" value={form.price} onChange={e => setForm(f => ({ ...f, price: e.target.value }))} className="input" placeholder="0" />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">제조사</label>
                    <input type="text" value={form.manufacturerNo} onChange={e => setForm(f => ({ ...f, manufacturerNo: e.target.value }))} className="input" placeholder="제조사" />
                  </div>
                  <div className="col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-1">상품 설명</label>
                    <textarea value={form.content} onChange={e => setForm(f => ({ ...f, content: e.target.value }))} className="input h-20 resize-none" placeholder="상품 설명" />
                  </div>
                </div>
              </div>
              <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
                <button type="button" onClick={() => setShowModal(false)} className="btn-secondary flex-1">취소</button>
                <button type="submit" disabled={submitting} className="btn-primary flex-1 flex items-center justify-center gap-2">
                  {submitting && <Loader2 size={14} className="animate-spin" />}
                  {editTarget ? '수정' : '등록'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
