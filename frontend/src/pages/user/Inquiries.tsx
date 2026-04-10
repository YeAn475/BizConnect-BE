import { useState } from 'react'
import { Search, Plus, MessageSquare, Paperclip } from 'lucide-react'
import clsx from 'clsx'

const inquiries = [
  { id: 1, title: '상품 재고 관련 문의', category: '상품', status: 'PENDING', company: '삼성전자', user: '김민준', createdAt: '2024-04-10 14:30', content: 'SSD 1TB 상품의 재고가 부족한데, 추가 입고 일정이 언제인지 알 수 있을까요?' },
  { id: 2, title: '주문 취소 요청', category: '주문', status: 'ANSWERED', company: 'LG전자', user: '이수진', createdAt: '2024-04-10 11:20', content: 'ORD-1041 주문을 취소하고 싶습니다.' },
  { id: 3, title: '세금계산서 재발행 요청', category: '정산', status: 'CLOSED', company: 'SK하이닉스', user: '박철수', createdAt: '2024-04-09 15:00', content: '3월 거래 건 세금계산서 재발행 부탁드립니다.' },
  { id: 4, title: '배송 지연 문의', category: '배송', status: 'PENDING', company: '현대자동차', user: '정수현', createdAt: '2024-04-09 10:00', content: '이번 주 월요일에 주문한 상품이 아직 도착하지 않았습니다.' },
  { id: 5, title: '시스템 오류 신고', category: '시스템', status: 'ANSWERED', company: 'POSCO', user: '최지영', createdAt: '2024-04-08 09:30', content: '주문 등록 시 500 오류가 발생합니다.' },
]

const statusConfig: Record<string, { label: string; className: string }> = {
  PENDING: { label: '답변대기', className: 'badge-pending' },
  ANSWERED: { label: '답변완료', className: 'badge-active' },
  CLOSED: { label: '종료', className: 'badge-closed' },
}

const categories = ['전체', '상품', '주문', '배송', '정산', '시스템', '기타']

export default function Inquiries() {
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('전체')
  const [status, setStatus] = useState('전체')
  const [selected, setSelected] = useState<typeof inquiries[0] | null>(null)
  const [showCreate, setShowCreate] = useState(false)

  const filtered = inquiries.filter(i =>
    (category === '전체' || i.category === category) &&
    (status === '전체' || i.status === status) &&
    (i.title.includes(search) || i.company.includes(search))
  )

  return (
    <div className="space-y-5">
      {/* Controls */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="relative w-72">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={15} />
            <input
              type="text"
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="문의 제목, 기업 검색..."
              className="input pl-9"
            />
          </div>
          <select
            value={status}
            onChange={e => setStatus(e.target.value)}
            className="input w-auto"
          >
            <option value="전체">전체 상태</option>
            <option value="PENDING">답변대기</option>
            <option value="ANSWERED">답변완료</option>
            <option value="CLOSED">종료</option>
          </select>
        </div>
        <button onClick={() => setShowCreate(true)} className="btn-primary flex items-center gap-2">
          <Plus size={15} />
          문의 등록
        </button>
      </div>

      {/* Category filter */}
      <div className="flex gap-2">
        {categories.map(c => (
          <button
            key={c}
            onClick={() => setCategory(c)}
            className={clsx(
              'px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
              category === c ? 'bg-primary-600 text-white' : 'bg-white text-gray-600 border border-gray-200 hover:border-gray-300'
            )}
          >
            {c}
          </button>
        ))}
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200">
            <tr>
              <th className="table-header">번호</th>
              <th className="table-header">제목</th>
              <th className="table-header">카테고리</th>
              <th className="table-header">기업</th>
              <th className="table-header">등록자</th>
              <th className="table-header">상태</th>
              <th className="table-header">등록일</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {filtered.map(inquiry => (
              <tr
                key={inquiry.id}
                onClick={() => setSelected(inquiry)}
                className="hover:bg-gray-50 transition-colors cursor-pointer"
              >
                <td className="table-cell text-gray-400 text-xs">{inquiry.id}</td>
                <td className="table-cell">
                  <div className="flex items-center gap-2">
                    <span className="font-medium text-gray-900">{inquiry.title}</span>
                    {inquiry.status === 'PENDING' && (
                      <span className="w-1.5 h-1.5 bg-yellow-400 rounded-full" />
                    )}
                  </div>
                </td>
                <td className="table-cell">
                  <span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">{inquiry.category}</span>
                </td>
                <td className="table-cell font-medium text-gray-700">{inquiry.company}</td>
                <td className="table-cell text-gray-500">{inquiry.user}</td>
                <td className="table-cell">
                  <span className={statusConfig[inquiry.status].className}>{statusConfig[inquiry.status].label}</span>
                </td>
                <td className="table-cell text-gray-400 text-xs">{inquiry.createdAt}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && (
          <div className="py-16 text-center">
            <MessageSquare size={36} className="text-gray-200 mx-auto mb-2" />
            <p className="text-sm text-gray-400">문의 내역이 없습니다</p>
          </div>
        )}
      </div>

      {/* Detail Modal */}
      {selected && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-xl">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <div className="flex items-center gap-3">
                <h2 className="text-lg font-semibold text-gray-900">{selected.title}</h2>
                <span className={statusConfig[selected.status].className}>{statusConfig[selected.status].label}</span>
              </div>
              <button onClick={() => setSelected(null)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="flex gap-3 flex-wrap text-xs">
                <span className="bg-gray-100 text-gray-600 px-2.5 py-1 rounded-full">{selected.category}</span>
                <span className="text-gray-500">기업: <strong>{selected.company}</strong></span>
                <span className="text-gray-500">등록자: <strong>{selected.user}</strong></span>
                <span className="text-gray-400">{selected.createdAt}</span>
              </div>
              <div className="bg-gray-50 rounded-xl p-4">
                <p className="text-sm text-gray-700 leading-relaxed">{selected.content}</p>
              </div>
              {selected.status === 'ANSWERED' && (
                <div className="bg-blue-50 rounded-xl p-4 border-l-4 border-primary-500">
                  <p className="text-xs font-semibold text-primary-700 mb-1">관리자 답변</p>
                  <p className="text-sm text-gray-700">확인 후 처리 완료했습니다. 추가 문의사항이 있으시면 연락 주세요.</p>
                </div>
              )}
              {selected.status === 'PENDING' && (
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">답변 작성</label>
                  <textarea className="input h-28 resize-none" placeholder="답변 내용을 입력하세요..." />
                </div>
              )}
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setSelected(null)} className="btn-secondary flex-1">닫기</button>
              {selected.status === 'PENDING' && (
                <button className="btn-primary flex-1">답변 등록</button>
              )}
            </div>
          </div>
        </div>
      )}

      {/* Create Modal */}
      {showCreate && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">새 문의 등록</h2>
              <button onClick={() => setShowCreate(false)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">카테고리</label>
                <select className="input">
                  {categories.filter(c => c !== '전체').map(c => <option key={c}>{c}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">제목</label>
                <input type="text" className="input" placeholder="문의 제목을 입력하세요" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">내용</label>
                <textarea className="input h-32 resize-none" placeholder="문의 내용을 상세하게 입력하세요" />
              </div>
              <div className="flex items-center gap-2 text-sm text-gray-500 border border-dashed border-gray-200 rounded-lg p-3 cursor-pointer hover:border-gray-300 transition-colors">
                <Paperclip size={14} />
                <span>첨부파일 추가 (선택사항)</span>
              </div>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setShowCreate(false)} className="btn-secondary flex-1">취소</button>
              <button className="btn-primary flex-1">등록</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
