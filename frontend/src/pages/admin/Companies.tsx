import { useState, useEffect } from 'react'
import { Search, Building2, MapPin, Phone, Plus, ChevronRight, Users, Package, Loader2 } from 'lucide-react'
import clsx from 'clsx'
import { companyApi } from '../../services/company'
import type { Company } from '../../types'

const affiliations = ['전체', 'IT·전자', '반도체', '자동차', '철강·소재', '유통·물류', '통신·서비스']

export default function Companies() {
  const [companies, setCompanies] = useState<Company[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [search, setSearch] = useState('')
  const [affiliation, setAffiliation] = useState('전체')

  useEffect(() => {
    companyApi.getList()
      .then(setCompanies)
      .catch(e => setError(e.message))
      .finally(() => setLoading(false))
  }, [])

  const filtered = companies.filter(c =>
    (affiliation === '전체' || c.affiliation?.name === affiliation) &&
    c.name.includes(search)
  )

  if (loading) return <div className="flex justify-center py-20"><Loader2 className="animate-spin text-primary-500" size={32} /></div>
  if (error) return <div className="bg-red-50 text-red-600 rounded-xl p-4 text-sm">{error}</div>

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
          <div key={company.companyNo} className="card hover:shadow-md hover:border-gray-300 transition-all cursor-pointer group">
            <div className="flex items-start gap-4">
              <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-primary-100 to-indigo-100 flex items-center justify-center flex-shrink-0">
                <span className="text-lg font-bold text-primary-700">{company.name[0]}</span>
              </div>

              <div className="flex-1 min-w-0">
                <div className="flex items-start justify-between">
                  <div>
                    <h3 className="font-semibold text-gray-900">{company.name}</h3>
                    {company.affiliation && (
                      <span className="text-xs text-primary-600 bg-primary-50 px-2 py-0.5 rounded-full">{company.affiliation.name}</span>
                    )}
                  </div>
                  <ChevronRight size={16} className="text-gray-400 group-hover:text-gray-600 transition-colors" />
                </div>

                <div className="mt-3 space-y-1">
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <MapPin size={12} />
                    <span className="truncate">{company.address}</span>
                  </div>
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Phone size={12} />
                    <span>{company.phoneNumber}</span>
                  </div>
                  {company.branch && (
                  <div className="flex items-center gap-1.5 text-xs text-gray-500">
                    <Building2 size={12} />
                    <span>{company.branch.name}</span>
                  </div>
                  )}
                </div>
                <div className="flex items-center gap-4 mt-3 pt-3 border-t border-gray-100">
                  <span className={company.isDeleted ? 'badge-rejected' : 'badge-active'}>
                    {company.isDeleted ? '비활성' : '활성'}
                  </span>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
