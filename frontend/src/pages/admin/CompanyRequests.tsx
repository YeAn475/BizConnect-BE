import { useState, useEffect } from 'react'
import { Search, Building2, User, Check, X, Clock, CheckCircle, XCircle, Loader2 } from 'lucide-react'
import clsx from 'clsx'
import { companyAlarmApi } from '../../services/companyAlarm'
import type { CompanyJoinRequest } from '../../types'

const statusConfig: Record<string, { label: string; icon: typeof Clock; color: string; className: string }> = {
  PENDING:  { label: '검토중', icon: Clock, color: 'text-yellow-500', className: 'badge-pending' },
  APPROVED: { label: '승인됨', icon: CheckCircle, color: 'text-green-500', className: 'badge-active' },
  REJECTED: { label: '거절됨', icon: XCircle, color: 'text-red-500', className: 'badge-rejected' },
}

export default function CompanyRequests() {
  const [requests, setRequests] = useState<CompanyJoinRequest[]>([])
  const [loading, setLoading] = useState(true)
  const [search, setSearch] = useState('')
  const [statusFilter, setStatusFilter] = useState('전체')
  const [selected, setSelected] = useState<CompanyJoinRequest | null>(null)
  const [rejectReason, setRejectReason] = useState('')
  const [processing, setProcessing] = useState(false)

  // 알림 목록에서 가입 요청을 가져옴 (CompanyAlarm은 별도 조회 API 미구현, 알림 referenceNo 기반)
  // 실제 구현 시 별도 API 엔드포인트 필요
  useEffect(() => {
    setLoading(false) // API 미구현 - UI만 표시
  }, [])

  const handleApprove = async () => {
    if (!selected) return
    setProcessing(true)
    try {
      await companyAlarmApi.approve(selected.companyRequestNo)
      setRequests(prev => prev.map(r =>
        r.companyRequestNo === selected.companyRequestNo ? { ...r, status: 'APPROVED' } : r
      ))
      setSelected(null)
    } catch (e: any) {
      alert('승인 실패: ' + e.message)
    } finally {
      setProcessing(false)
    }
  }

  const handleReject = async () => {
    if (!selected) return
    setProcessing(true)
    try {
      await companyAlarmApi.reject(selected.companyRequestNo, rejectReason)
      setRequests(prev => prev.map(r =>
        r.companyRequestNo === selected.companyRequestNo ? { ...r, status: 'REJECTED' } : r
      ))
      setSelected(null)
      setRejectReason('')
    } catch (e: any) {
      alert('거절 실패: ' + e.message)
    } finally {
      setProcessing(false)
    }
  }

  const filtered = requests.filter(r =>
    (statusFilter === '전체' || r.status === statusFilter) &&
    (r.user.name.includes(search) || r.company.name.includes(search))
  )

  if (loading) return <div className="flex justify-center py-20"><Loader2 className="animate-spin text-primary-500" size={32} /></div>

  const stats = {
    total: requests.length,
    pending: requests.filter(r => r.status === 'PENDING').length,
    approved: requests.filter(r => r.status === 'APPROVED').length,
    rejected: requests.filter(r => r.status === 'REJECTED').length,
  }

  return (
    <div className="space-y-5">
      {/* Stats */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: '전체 요청', value: stats.total, color: 'text-gray-900', bg: 'bg-gray-50', icon: Building2 },
          { label: '검토중', value: stats.pending, color: 'text-yellow-700', bg: 'bg-yellow-50', icon: Clock },
          { label: '승인됨', value: stats.approved, color: 'text-green-700', bg: 'bg-green-50', icon: CheckCircle },
          { label: '거절됨', value: stats.rejected, color: 'text-red-700', bg: 'bg-red-50', icon: XCircle },
        ].map(item => (
          <div key={item.label} className={`${item.bg} rounded-xl p-4 flex items-center gap-3`}>
            <item.icon className={item.color} size={22} />
            <div>
              <p className="text-xs text-gray-500">{item.label}</p>
              <p className={`text-2xl font-bold ${item.color}`}>{item.value}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Controls */}
      <div className="flex items-center gap-3">
        <div className="relative w-72">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={15} />
          <input
            type="text"
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="이름, 기업 검색..."
            className="input pl-9"
          />
        </div>
        <div className="flex border border-gray-200 rounded-lg overflow-hidden">
          {['전체', 'PENDING', 'APPROVED', 'REJECTED'].map(s => (
            <button
              key={s}
              onClick={() => setStatusFilter(s)}
              className={clsx(
                'px-3 py-2 text-sm font-medium transition-colors',
                statusFilter === s ? 'bg-gray-900 text-white' : 'text-gray-500 hover:bg-gray-50'
              )}
            >
              {s === '전체' ? '전체' : statusConfig[s].label}
            </button>
          ))}
        </div>
      </div>

      {/* Request list */}
      <div className="space-y-3">
        {filtered.map(req => {
          const cfg = statusConfig[req.status]
          return (
            <div key={req.companyRequestNo} className="bg-white rounded-xl border border-gray-200 p-5 hover:shadow-sm transition-shadow">
              <div className="flex items-start gap-4">
                {/* Icons */}
                <div className="flex gap-2 flex-shrink-0">
                  <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-primary-100 to-indigo-200 flex items-center justify-center">
                    <User size={16} className="text-primary-600" />
                  </div>
                </div>

                {/* Info */}
                <div className="flex-1">
                  <div className="flex items-start justify-between">
                    <div>
                      <div className="flex items-center gap-2 mb-1">
                        <h3 className="font-semibold text-gray-900">{req.user.name}</h3>
                        <span className={cfg.className}>{cfg.label}</span>
                      </div>
                      <p className="text-sm text-gray-500">{req.user.email}</p>
                    </div>
                    <span className="text-xs text-gray-400">{req.createdAt}</span>
                  </div>

                  <div className="flex items-center gap-2 mt-3 p-3 bg-gray-50 rounded-xl">
                    <Building2 size={14} className="text-gray-500" />
                    <div>
                      <span className="text-sm font-semibold text-gray-900">{req.company.name}</span>
                    </div>
                  </div>
                </div>

                {/* Actions */}
                {req.status === 'PENDING' && (
                  <div className="flex gap-2 flex-shrink-0">
                    <button
                      onClick={() => setSelected(req)}
                      className="flex items-center gap-1.5 px-3 py-1.5 bg-green-500 text-white text-sm font-medium rounded-lg hover:bg-green-600 transition-colors"
                    >
                      <Check size={14} /> 승인
                    </button>
                    <button
                      onClick={() => setSelected(req)}
                      className="flex items-center gap-1.5 px-3 py-1.5 bg-red-50 text-red-600 text-sm font-medium rounded-lg hover:bg-red-100 transition-colors border border-red-200"
                    >
                      <X size={14} /> 거절
                    </button>
                  </div>
                )}
              </div>
            </div>
          )
        })}
      </div>

      {filtered.length === 0 && (
        <div className="py-20 text-center">
          <ClipboardList />
          <p className="text-sm text-gray-400 mt-2">가입 요청이 없습니다</p>
        </div>
      )}

      {/* Approve/Reject Modal */}
      {selected && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">기업 가입 요청 처리</h2>
              <button onClick={() => setSelected(null)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="bg-gray-50 rounded-xl p-4">
                <p className="text-sm text-gray-600 mb-1">요청자</p>
                <p className="font-semibold text-gray-900">{selected.user.name}</p>
                <p className="text-sm text-gray-500">{selected.user.email}</p>
                <p className="text-sm font-medium text-primary-600 mt-2">→ {selected.company.name}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">거절 사유 (거절 시)</label>
                <textarea
                  value={rejectReason}
                  onChange={e => setRejectReason(e.target.value)}
                  className="input h-24 resize-none"
                  placeholder="거절 사유를 입력하세요 (선택사항)"
                />
              </div>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setSelected(null)} disabled={processing} className="btn-secondary flex-1">취소</button>
              <button onClick={handleReject} disabled={processing}
                className="flex-1 flex items-center justify-center gap-2 bg-red-600 text-white py-2 rounded-lg hover:bg-red-700 transition-colors text-sm font-medium disabled:opacity-50">
                {processing ? <Loader2 size={14} className="animate-spin" /> : <X size={14} />} 거절
              </button>
              <button onClick={handleApprove} disabled={processing}
                className="flex-1 flex items-center justify-center gap-2 bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition-colors text-sm font-medium disabled:opacity-50">
                {processing ? <Loader2 size={14} className="animate-spin" /> : <Check size={14} />} 승인
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

function ClipboardList() {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" className="text-gray-200 mx-auto">
      <rect x="8" y="2" width="8" height="4" rx="1" ry="1"/>
      <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"/>
      <path d="M12 11h4"/><path d="M12 16h4"/><path d="M8 11h.01"/><path d="M8 16h.01"/>
    </svg>
  )
}
