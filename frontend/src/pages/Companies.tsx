import { useState } from 'react'
import { Search, Building2, MapPin, Phone, Plus, ChevronRight, Users, Package } from 'lucide-react'
import clsx from 'clsx'

const companies = [
  { id: 1, name: '삼성전자', affiliation: 'IT·전자', branch: '서울 본사', address: '서울특별시 서초구 삼성로', phone: '02-2255-0114', memberCount: 48, productCount: 128, isConnected: true },
  { id: 2, name: 'LG전자', affiliation: 'IT·전자', branch: '서울 본사', address: '서울특별시 영등포구 여의대로', phone: '02-3777-1114', memberCount: 35, productCount: 96, isConnected: true },
  { id: 3, name: 'SK하이닉스', affiliation: '반도체', branch: '경기 이천', address: '경기도 이천시 부발읍', phone: '031-630-4114', memberCount: 22, productCount: 45, isConnected: false },
  { id: 4, name: '현대자동차', affiliation: '자동차', branch: '서울 본사', address: '서울특별시 서초구 헌릉로', phone: '02-3464-1114', memberCount: 17, productCount: 72, isConnected: false },
  { id: 5, name: 'POSCO', affiliation: '철강·소재', branch: '포항 본사', address: '경상북도 포항시 남구', phone: '054-220-0114', memberCount: 12, productCount: 34, isConnected: true },
  { id: 6, name: '롯데쇼핑', affiliation: '유통·물류', branch: '서울 본사', address: '서울특별시 중구 남대문로', phone: '02-771-2500', memberCount: 8, productCount: 220, isConnected: false },
  { id: 7, name: 'KT', affiliation: '통신·서비스', branch: '서울 본사', address: '서울특별시 종로구 종로', phone: '02-100', memberCount: 29, productCount: 18, isConnected: true },
  { id: 8, name: '(주)넥스텍', affiliation: 'IT·전자', branch: '판교 사무소', address: '경기도 성남시 분당구 판교', phone: '031-8022-0000', memberCount: 6, productCount: 12, isConnected: false },
]

const affiliations = ['전체', 'IT·전자', '반도체', '자동차', '철강·소재', '유통·물류', '통신·서비스']

export default function Companies() {
  const [search, setSearch] = useState('')
  const [affiliation, setAffiliation] = useState('전체')
  const [filter, setFilter] = useState<'전체' | '연결됨' | '미연결'>('전체')

  const filtered = companies.filter(c =>
    (affiliation === '전체' || c.affiliation === affiliation) &&
    (filter === '전체' || (filter === '연결됨' ? c.isConnected : !c.isConnected)) &&
    c.name.includes(search)
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
              placeholder="기업명 검색..."
              className="input pl-9"
            />
          </div>
          {(['전체', '연결됨', '미연결'] as const).map(f => (
            <button
              key={f}
              onClick={() => setFilter(f)}
              className={clsx(
                'px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                filter === f ? 'bg-gray-900 text-white' : 'text-gray-500 hover:text-gray-700 hover:bg-gray-100'
              )}
            >
              {f}
            </button>
          ))}
        </div>
        <button className="btn-primary flex items-center gap-2">
          <Plus size={16} />
          기업 등록 요청
        </button>
      </div>

      {/* Affiliation filter */}
      <div className="flex items-center gap-2">
        {affiliations.map(a => (
          <button
            key={a}
            onClick={() => setAffiliation(a)}
            className={clsx(
              'px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
              affiliation === a
                ? 'bg-primary-600 text-white'
                : 'bg-white text-gray-600 border border-gray-200 hover:border-gray-300'
            )}
          >
            {a}
          </button>
        ))}
      </div>

      <p className="text-sm text-gray-500">총 <strong className="text-gray-900">{filtered.length}</strong>개 기업</p>

      {/* Company Grid */}
      <div className="grid grid-cols-2 gap-4">
        {filtered.map(company => (
          <div key={company.id} className="card hover:shadow-md hover:border-gray-300 transition-all cursor-pointer group">
            <div className="flex items-start gap-4">
              {/* Company Logo */}
              <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-primary-100 to-indigo-100 flex items-center justify-center flex-shrink-0">
                <span className="text-lg font-bold text-primary-700">{company.name[0]}</span>
              </div>

              <div className="flex-1 min-w-0">
                <div className="flex items-start justify-between">
                  <div>
                    <h3 className="font-semibold text-gray-900">{company.name}</h3>
                    <span className="text-xs text-primary-600 bg-primary-50 px-2 py-0.5 rounded-full">{company.affiliation}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    {company.isConnected ? (
                      <span className="badge-active">연결됨</span>
                    ) : (
                      <button className="text-xs text-primary-600 border border-primary-300 px-2 py-0.5 rounded-full hover:bg-primary-50 transition-colors">
                        연결 요청
                      </button>
                    )}
                    <ChevronRight size={16} className="text-gray-400 group-hover:text-gray-600 transition-colors" />
                  </div>
                </div>

                <div className="mt-3 space-y-1">
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <MapPin size={12} />
                    <span className="truncate">{company.address}</span>
                  </div>
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Phone size={12} />
                    <span>{company.phone}</span>
                  </div>
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Building2 size={12} />
                    <span>{company.branch}</span>
                  </div>
                </div>

                <div className="flex items-center gap-4 mt-3 pt-3 border-t border-gray-100">
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Users size={12} />
                    <span><strong className="text-gray-700">{company.memberCount}</strong> 구성원</span>
                  </div>
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Package size={12} />
                    <span><strong className="text-gray-700">{company.productCount}</strong> 상품</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
