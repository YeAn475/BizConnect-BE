import { useState } from 'react'
import { Search, Plus, Filter, Package, Edit2, Trash2, Tag, Box } from 'lucide-react'
import clsx from 'clsx'

const products = [
  { id: 1, name: 'SSD 1TB', category: '전자부품', manufacturer: '삼성전자', price: 85000, unit: '개', status: '판매중', imageUrl: null, content: '고성능 NVMe SSD' },
  { id: 2, name: 'RAM 16GB DDR5', category: '전자부품', manufacturer: 'SK하이닉스', price: 62000, unit: '개', status: '판매중', imageUrl: null, content: 'DDR5 5600MHz 메모리' },
  { id: 3, name: '사무용 의자', category: '사무용품', manufacturer: '시디즈', price: 320000, unit: '개', status: '판매중', imageUrl: null, content: '인체공학적 메쉬 의자' },
  { id: 4, name: 'A4 복사지', category: '사무용품', manufacturer: '한솔제지', price: 22000, unit: '박스', status: '판매중', imageUrl: null, content: '80g/m² 500매' },
  { id: 5, name: '노트북 거치대', category: '사무용품', manufacturer: '에이스', price: 25000, unit: '개', status: '품절', imageUrl: null, content: '알루미늄 접이식' },
  { id: 6, name: '케이블 타이', category: '기계부품', manufacturer: '판도크', price: 4500, unit: '팩', status: '판매중', imageUrl: null, content: '100개입 200mm' },
  { id: 7, name: '프린터 잉크', category: '사무용품', manufacturer: 'HP', price: 12000, unit: '개', status: '판매중', imageUrl: null, content: 'HP LaserJet 호환' },
  { id: 8, name: '전동 드라이버', category: '기계부품', manufacturer: '보쉬', price: 185000, unit: '개', status: '단종', imageUrl: null, content: '12V 리튬 배터리' },
]

const categories = ['전체', '전자부품', '사무용품', '기계부품', '소프트웨어', '기타']
const statuses = ['전체', '판매중', '품절', '단종']

const statusStyles: Record<string, string> = {
  '판매중': 'badge-active',
  '품절': 'badge-pending',
  '단종': 'badge-closed',
}

export default function Products() {
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('전체')
  const [status, setStatus] = useState('전체')
  const [view, setView] = useState<'grid' | 'list'>('grid')
  const [showModal, setShowModal] = useState(false)

  const filtered = products.filter(p =>
    (category === '전체' || p.category === category) &&
    (status === '전체' || p.status === status) &&
    (p.name.includes(search) || p.manufacturer.includes(search))
  )

  return (
    <div className="space-y-5">
      {/* Top bar */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="relative w-72">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
            <input
              type="text"
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="상품명, 제조사 검색..."
              className="input pl-9"
            />
          </div>
          <button className="btn-secondary flex items-center gap-2">
            <Filter size={14} />
            필터
          </button>
        </div>
        <button
          onClick={() => setShowModal(true)}
          className="btn-primary flex items-center gap-2"
        >
          <Plus size={16} />
          상품 등록
        </button>
      </div>

      {/* Category filter */}
      <div className="flex items-center gap-2">
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
        <div className="ml-auto flex items-center gap-2 text-sm text-gray-500">
          <span>상태:</span>
          {statuses.map(s => (
            <button
              key={s}
              onClick={() => setStatus(s)}
              className={clsx(
                'px-2.5 py-1 rounded-lg text-xs font-medium transition-colors',
                status === s ? 'bg-gray-900 text-white' : 'text-gray-500 hover:text-gray-700'
              )}
            >
              {s}
            </button>
          ))}
        </div>
      </div>

      {/* Stats */}
      <div className="flex gap-3 text-sm text-gray-500">
        <span>총 <strong className="text-gray-900">{filtered.length}</strong>개 상품</span>
        <span>·</span>
        <span>판매중 <strong className="text-green-600">{products.filter(p => p.status === '판매중').length}</strong>개</span>
        <span>·</span>
        <span>품절 <strong className="text-yellow-500">{products.filter(p => p.status === '품절').length}</strong>개</span>
      </div>

      {/* Product Grid */}
      <div className="grid grid-cols-4 gap-4">
        {filtered.map(product => (
          <div key={product.id} className="bg-white rounded-xl border border-gray-200 overflow-hidden hover:shadow-md hover:border-gray-300 transition-all group">
            {/* Image placeholder */}
            <div className="h-40 bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center relative">
              <Package size={48} className="text-gray-200" />
              <div className="absolute top-2 right-2">
                <span className={statusStyles[product.status]}>{product.status}</span>
              </div>
            </div>

            <div className="p-4">
              <div className="flex items-start justify-between mb-1">
                <h3 className="font-semibold text-gray-900 text-sm">{product.name}</h3>
              </div>
              <p className="text-xs text-gray-400 mb-2">{product.content}</p>
              <div className="flex items-center gap-1 mb-3">
                <span className="inline-flex items-center gap-1 text-xs text-gray-500 bg-gray-50 px-2 py-0.5 rounded-md">
                  <Tag size={10} /> {product.category}
                </span>
                <span className="inline-flex items-center gap-1 text-xs text-gray-500 bg-gray-50 px-2 py-0.5 rounded-md">
                  <Box size={10} /> {product.unit}
                </span>
              </div>
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-xs text-gray-400">{product.manufacturer}</p>
                  <p className="text-base font-bold text-gray-900">₩{product.price.toLocaleString()}</p>
                </div>
                <div className="flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                  <button className="p-1.5 rounded-lg hover:bg-gray-100 text-gray-500">
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
      </div>

      {/* Add Product Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">새 상품 등록</h2>
              <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-600 text-xl leading-none">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="border-2 border-dashed border-gray-200 rounded-xl h-32 flex items-center justify-center text-gray-400 cursor-pointer hover:border-primary-400 hover:text-primary-500 transition-colors">
                <div className="text-center">
                  <Package size={28} className="mx-auto mb-1" />
                  <p className="text-sm">이미지 업로드</p>
                </div>
              </div>
              <div className="grid grid-cols-2 gap-3">
                <div className="col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-1">상품명</label>
                  <input type="text" className="input" placeholder="상품명을 입력하세요" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">카테고리</label>
                  <select className="input">
                    <option>전자부품</option>
                    <option>사무용품</option>
                    <option>기계부품</option>
                    <option>소프트웨어</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">단위</label>
                  <select className="input">
                    <option>개</option>
                    <option>박스</option>
                    <option>팩</option>
                    <option>세트</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">단가</label>
                  <input type="number" className="input" placeholder="0" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">제조사</label>
                  <input type="text" className="input" placeholder="제조사명" />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-1">상품 설명</label>
                  <textarea className="input h-20 resize-none" placeholder="상품 설명을 입력하세요" />
                </div>
              </div>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setShowModal(false)} className="btn-secondary flex-1">취소</button>
              <button className="btn-primary flex-1">등록</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
