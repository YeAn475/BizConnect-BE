import { useState } from 'react'
import { Search, Eye, ShoppingCart } from 'lucide-react'
import clsx from 'clsx'

// Order 컨트롤러 미구현 → UI만
const orders = [
  { no: 'ORD-1042', company: '삼성전자', user: '김민준', product: 'SSD 1TB × 10', total: 850000, status: 'ACTIVE', createdAt: '2024-04-10 14:32' },
  { no: 'ORD-1041', company: 'LG전자', user: '이수진', product: '노트북 거치대 × 5', total: 125000, status: 'ANSWERED', createdAt: '2024-04-10 11:20' },
  { no: 'ORD-1040', company: 'SK하이닉스', user: '박철수', product: 'RAM 16GB × 20', total: 1240000, status: 'CLOSED', createdAt: '2024-04-10 09:15' },
  { no: 'ORD-1039', company: '현대자동차', user: '정수현', product: '케이블 타이 × 100', total: 45000, status: 'ACTIVE', createdAt: '2024-04-09 16:48' },
  { no: 'ORD-1038', company: 'POSCO', user: '최지영', product: '프린터 잉크 × 8', total: 96000, status: 'ANSWERED', createdAt: '2024-04-09 14:22' },
]

const statusConfig: Record<string, { label: string; className: string }> = {
  ACTIVE:   { label: '진행중', className: 'badge-active' },
  ANSWERED: { label: '답변완료', className: 'badge-pending' },
  CLOSED:   { label: '완료', className: 'badge-closed' },
}

export default function AdminOrders() {
  const [search, setSearch] = useState('')
  const [status, setStatus] = useState('전체')

  const filtered = orders.filter(o =>
    (status === '전체' || o.status === status) &&
    (o.no.includes(search) || o.company.includes(search))
  )

  return (
    <div className="space-y-5">
      <div className="rounded-xl bg-amber-50 border border-amber-200 px-4 py-3 text-sm text-amber-700">
        ⚠️ 주문 관리 API는 현재 개발 예정 단계입니다. 현재 화면은 UI 미리보기입니다.
      </div>

      <div className="grid grid-cols-3 gap-4">
        {[
          { label: '전체 주문', value: orders.length },
          { label: '진행중', value: orders.filter(o => o.status === 'ACTIVE').length },
          { label: '답변완료', value: orders.filter(o => o.status === 'ANSWERED').length },
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
          <input type="text" value={search} onChange={e => setSearch(e.target.value)} placeholder="주문번호, 기업 검색..." className="input pl-9" />
        </div>
        <div className="flex border border-gray-200 rounded-lg overflow-hidden">
          {['전체', 'ACTIVE', 'ANSWERED', 'CLOSED'].map(s => (
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
              <th className="table-header">주문번호</th>
              <th className="table-header">기업</th>
              <th className="table-header">담당자</th>
              <th className="table-header">상품</th>
              <th className="table-header">총액</th>
              <th className="table-header">상태</th>
              <th className="table-header">주문일시</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {filtered.map(order => (
              <tr key={order.no} className="hover:bg-gray-50 transition-colors">
                <td className="table-cell font-mono text-xs text-gray-500">{order.no}</td>
                <td className="table-cell font-medium text-gray-900">{order.company}</td>
                <td className="table-cell text-gray-600">{order.user}</td>
                <td className="table-cell text-gray-600">{order.product}</td>
                <td className="table-cell font-semibold text-gray-900">₩{order.total.toLocaleString()}</td>
                <td className="table-cell"><span className={statusConfig[order.status].className}>{statusConfig[order.status].label}</span></td>
                <td className="table-cell text-gray-400 text-xs">{order.createdAt}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && (
          <div className="py-16 text-center">
            <ShoppingCart size={36} className="text-gray-200 mx-auto mb-2" />
            <p className="text-sm text-gray-400">주문 내역이 없습니다</p>
          </div>
        )}
      </div>
    </div>
  )
}
