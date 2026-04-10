import { useState } from 'react'
import { Search, MessageSquare } from 'lucide-react'
import clsx from 'clsx'

// Inquiry 컨트롤러 미구현 → UI만
const inquiries = [
  { id: 1, title: '상품 재고 관련 문의', category: '상품', status: 'PENDING', company: '삼성전자', user: '김민준', createdAt: '2024-04-10 14:30', content: 'SSD 1TB 상품의 재고가 부족한데, 추가 입고 일정이 언제인지 알 수 있을까요?' },
  { id: 2, title: '주문 취소 요청', category: '주문', status: 'ANSWERED', company: 'LG전자', user: '이수진', createdAt: '2024-04-10 11:20', content: 'ORD-1041 주문을 취소하고 싶습니다.' },
  { id: 3, title: '세금계산서 재발행 요청', category: '정산', status: 'CLOSED', company: 'SK하이닉스', user: '박철수', createdAt: '2024-04-09 15:00', content: '3월 거래 건 세금계산서 재발행 부탁드립니다.' },
  { id: 4, title: '배송 지연 문의', category: '배송', status: 'PENDING', company: '현대자동차', user: '정수현', createdAt: '2024-04-09 10:00', content: '이번 주 월요일에 주문한 상품이 아직 도착하지 않았습니다.' },
  { id: 5, title: '시스템 오류 신고', category: '시스템', status: 'ANSWERED', company: 'POSCO', user: '최지영', createdAt: '2024-04-08 09:30', content: '주문 등록 시 500 오류가 발생합니다.' },
]

const statusConfig: Record<string, { label: string; className: string }> = {
  PENDING:  { label: '답변대기', className: 'badge-pending' },
  ANSWERED: { label: '답변완료', className: 'badge-active' },
  CLOSED:   { label: '종료', className: 'badge-closed' },
}

export default function AdminInquiries() {
  const [search, setSearch] = useState('')
  const [status, setStatus] = useState('전체')
  const [selected, setSelected] = useState<typeof inquiries[0] | null>(null)
  const [answer, setAnswer] = useState('')

  const filtered = inquiries.filter(i =>
    (status === '전체' || i.status === status) &&
    (i.title.includes(search) || i.company.includes(search))
  )

  return (
    <div className="space-y-5">
      <div className="rounded-xl bg-amber-50 border border-amber-200 px-4 py-3 text-sm text-amber-700">
        ⚠️ 문의 관리 API는 현재 개발 예정 단계입니다. 현재 화면은 UI 미리보기입니다.
      </div>

      <div className="grid grid-cols-3 gap-4">
        {[
          { label: '전체 문의', value: inquiries.length },
          { label: '답변 대기', value: inquiries.filter(i => i.status === 'PENDING').length },
          { label: '답변 완료', value: inquiries.filter(i => i.status === 'ANSWERED').length },
        ].map(item => (
          <div key={item.label} className="bg-gray-50 rounded-xl p-4">
            <p className="text-xs text-gray-500">{item.label}</p>
            <p className="text-2xl font-bold text-gray-900 mt-1">{item.value}</p>
          </div>
        ))}
      </div>

      <div className="flex gap-3">
        <div className="relative w-72">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={15} />
          <input type="text" value={search} onChange={e => setSearch(e.target.value)} placeholder="제목, 기업 검색..." className="input pl-9" />
        </div>
        <div className="flex border border-gray-200 rounded-lg overflow-hidden">
          {['전체', 'PENDING', 'ANSWERED', 'CLOSED'].map(s => (
            <button key={s} onClick={() => setStatus(s)}
              className={clsx('px-3 py-2 text-sm font-medium transition-colors',
                status === s ? 'bg-gray-900 text-white' : 'text-gray-500 hover:bg-gray-50'
              )}>
              {s === '전체' ? '전체' : statusConfig[s].label}
            </button>
          ))}
        </div>
      </div>

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
            {filtered.map(inq => (
              <tr key={inq.id} onClick={() => setSelected(inq)} className="hover:bg-gray-50 cursor-pointer transition-colors">
                <td className="table-cell text-gray-400 text-xs">{inq.id}</td>
                <td className="table-cell font-medium text-gray-900">{inq.title}</td>
                <td className="table-cell"><span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">{inq.category}</span></td>
                <td className="table-cell text-gray-700">{inq.company}</td>
                <td className="table-cell text-gray-500">{inq.user}</td>
                <td className="table-cell"><span className={statusConfig[inq.status].className}>{statusConfig[inq.status].label}</span></td>
                <td className="table-cell text-gray-400 text-xs">{inq.createdAt}</td>
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

      {selected && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-xl">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <div className="flex items-center gap-3">
                <h2 className="text-lg font-semibold text-gray-900">{selected.title}</h2>
                <span className={statusConfig[selected.status].className}>{statusConfig[selected.status].label}</span>
              </div>
              <button onClick={() => setSelected(null)} className="text-gray-400 text-xl">×</button>
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
              {selected.status === 'PENDING' && (
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">답변 작성</label>
                  <textarea value={answer} onChange={e => setAnswer(e.target.value)}
                    className="input h-28 resize-none" placeholder="답변 내용을 입력하세요..." />
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
    </div>
  )
}
