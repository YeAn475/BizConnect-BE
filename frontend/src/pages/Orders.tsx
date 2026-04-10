import { useState } from 'react'
import { Search, Filter, Download, Eye, ChevronDown, ShoppingCart } from 'lucide-react'
import clsx from 'clsx'

const orders = [
  { no: 'ORD-1042', company: '삼성전자', user: '김민준', items: [{ name: 'SSD 1TB', qty: 10, price: 85000 }], total: 850000, status: 'ACTIVE', createdAt: '2024-04-10 14:32' },
  { no: 'ORD-1041', company: 'LG전자', user: '이수진', items: [{ name: '노트북 거치대', qty: 5, price: 25000 }], total: 125000, status: 'ANSWERED', createdAt: '2024-04-10 11:20' },
  { no: 'ORD-1040', company: 'SK하이닉스', user: '박철수', items: [{ name: 'RAM 16GB', qty: 20, price: 62000 }], total: 1240000, status: 'CLOSED', createdAt: '2024-04-10 09:15' },
  { no: 'ORD-1039', company: '현대자동차', user: '정수현', items: [{ name: '케이블 타이', qty: 100, price: 450 }], total: 45000, status: 'ACTIVE', createdAt: '2024-04-09 16:48' },
  { no: 'ORD-1038', company: 'POSCO', user: '최지영', items: [{ name: '프린터 잉크', qty: 8, price: 12000 }], total: 96000, status: 'ANSWERED', createdAt: '2024-04-09 14:22' },
  { no: 'ORD-1037', company: 'KT', user: '한명수', items: [{ name: 'A4 복사지', qty: 30, price: 22000 }], total: 660000, status: 'CLOSED', createdAt: '2024-04-09 11:05' },
  { no: 'ORD-1036', company: '롯데쇼핑', user: '임소연', items: [{ name: '사무용 의자', qty: 3, price: 320000 }], total: 960000, status: 'ACTIVE', createdAt: '2024-04-08 15:30' },
  { no: 'ORD-1035', company: '삼성전자', user: '강도현', items: [{ name: 'SSD 512GB', qty: 5, price: 52000 }], total: 260000, status: 'CLOSED', createdAt: '2024-04-08 10:45' },
]

const statusConfig: Record<string, { label: string; className: string; dot: string }> = {
  ACTIVE: { label: '진행중', className: 'badge-active', dot: 'bg-green-500' },
  ANSWERED: { label: '답변완료', className: 'badge-pending', dot: 'bg-yellow-400' },
  CLOSED: { label: '완료', className: 'badge-closed', dot: 'bg-gray-400' },
}

export default function Orders() {
  const [search, setSearch] = useState('')
  const [status, setStatus] = useState('전체')
  const [selected, setSelected] = useState<string[]>([])
  const [detailOrder, setDetailOrder] = useState<typeof orders[0] | null>(null)

  const statuses = ['전체', 'ACTIVE', 'ANSWERED', 'CLOSED']
  const statusLabels: Record<string, string> = { '전체': '전체', ACTIVE: '진행중', ANSWERED: '답변완료', CLOSED: '완료' }

  const filtered = orders.filter(o =>
    (status === '전체' || o.status === status) &&
    (o.no.includes(search) || o.company.includes(search) || o.user.includes(search))
  )

  const toggleAll = () => {
    setSelected(selected.length === filtered.length ? [] : filtered.map(o => o.no))
  }

  const toggleOne = (no: string) => {
    setSelected(s => s.includes(no) ? s.filter(x => x !== no) : [...s, no])
  }

  return (
    <div className="space-y-5">
      {/* Summary */}
      <div className="grid grid-cols-3 gap-4">
        {[
          { label: '전체 주문', count: orders.length, color: 'text-gray-900', bg: 'bg-gray-50' },
          { label: '진행중', count: orders.filter(o => o.status === 'ACTIVE').length, color: 'text-green-700', bg: 'bg-green-50' },
          { label: '답변완료', count: orders.filter(o => o.status === 'ANSWERED').length, color: 'text-yellow-700', bg: 'bg-yellow-50' },
        ].map(item => (
          <div key={item.label} className={`${item.bg} rounded-xl p-4 flex items-center gap-3`}>
            <div>
              <p className="text-xs text-gray-500">{item.label}</p>
              <p className={`text-2xl font-bold ${item.color}`}>{item.count}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Controls */}
      <div className="flex items-center gap-3">
        <div className="relative w-72">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
          <input
            type="text"
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="주문번호, 기업, 담당자 검색..."
            className="input pl-9"
          />
        </div>
        <div className="flex border border-gray-200 rounded-lg overflow-hidden">
          {statuses.map(s => (
            <button
              key={s}
              onClick={() => setStatus(s)}
              className={clsx(
                'px-3 py-2 text-sm font-medium transition-colors',
                status === s ? 'bg-gray-900 text-white' : 'text-gray-500 hover:bg-gray-50'
              )}
            >
              {statusLabels[s]}
            </button>
          ))}
        </div>
        <div className="ml-auto flex gap-2">
          {selected.length > 0 && (
            <span className="text-sm text-gray-500 self-center">{selected.length}개 선택됨</span>
          )}
          <button className="btn-secondary flex items-center gap-2 text-sm">
            <Download size={14} />
            내보내기
          </button>
        </div>
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200">
            <tr>
              <th className="table-header w-10">
                <input
                  type="checkbox"
                  checked={selected.length === filtered.length && filtered.length > 0}
                  onChange={toggleAll}
                  className="w-4 h-4 rounded border-gray-300 text-primary-600"
                />
              </th>
              <th className="table-header">주문번호</th>
              <th className="table-header">기업</th>
              <th className="table-header">담당자</th>
              <th className="table-header">상품</th>
              <th className="table-header">총액</th>
              <th className="table-header">상태</th>
              <th className="table-header">주문일시</th>
              <th className="table-header w-20">상세</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {filtered.map(order => (
              <tr key={order.no} className="hover:bg-gray-50 transition-colors">
                <td className="table-cell">
                  <input
                    type="checkbox"
                    checked={selected.includes(order.no)}
                    onChange={() => toggleOne(order.no)}
                    className="w-4 h-4 rounded border-gray-300 text-primary-600"
                  />
                </td>
                <td className="table-cell font-mono text-xs text-gray-500">{order.no}</td>
                <td className="table-cell font-medium text-gray-900">{order.company}</td>
                <td className="table-cell text-gray-600">{order.user}</td>
                <td className="table-cell text-gray-600">
                  {order.items[0].name} {order.items.length > 1 && <span className="text-gray-400 text-xs">외 {order.items.length - 1}건</span>}
                </td>
                <td className="table-cell font-semibold text-gray-900">₩{order.total.toLocaleString()}</td>
                <td className="table-cell">
                  <span className={statusConfig[order.status].className}>
                    {statusConfig[order.status].label}
                  </span>
                </td>
                <td className="table-cell text-gray-400 text-xs">{order.createdAt}</td>
                <td className="table-cell">
                  <button
                    onClick={() => setDetailOrder(order)}
                    className="flex items-center gap-1 text-primary-600 hover:text-primary-700 text-xs font-medium"
                  >
                    <Eye size={13} /> 보기
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {filtered.length === 0 && (
          <div className="py-16 text-center">
            <ShoppingCart size={36} className="text-gray-200 mx-auto mb-3" />
            <p className="text-sm text-gray-400">주문 내역이 없습니다</p>
          </div>
        )}
      </div>

      {/* Detail Modal */}
      {detailOrder && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <div>
                <h2 className="text-lg font-semibold text-gray-900">주문 상세</h2>
                <p className="text-sm text-gray-400 font-mono">{detailOrder.no}</p>
              </div>
              <button onClick={() => setDetailOrder(null)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div className="bg-gray-50 rounded-xl p-3">
                  <p className="text-xs text-gray-500 mb-1">발주 기업</p>
                  <p className="font-semibold text-gray-900">{detailOrder.company}</p>
                </div>
                <div className="bg-gray-50 rounded-xl p-3">
                  <p className="text-xs text-gray-500 mb-1">담당자</p>
                  <p className="font-semibold text-gray-900">{detailOrder.user}</p>
                </div>
                <div className="bg-gray-50 rounded-xl p-3">
                  <p className="text-xs text-gray-500 mb-1">주문일시</p>
                  <p className="font-semibold text-gray-900">{detailOrder.createdAt}</p>
                </div>
                <div className="bg-gray-50 rounded-xl p-3">
                  <p className="text-xs text-gray-500 mb-1">상태</p>
                  <span className={statusConfig[detailOrder.status].className}>{statusConfig[detailOrder.status].label}</span>
                </div>
              </div>
              <div>
                <p className="text-sm font-semibold text-gray-700 mb-2">주문 상품</p>
                {detailOrder.items.map((item, i) => (
                  <div key={i} className="flex justify-between items-center py-2 border-b border-gray-100 text-sm">
                    <span className="text-gray-700">{item.name}</span>
                    <div className="flex gap-4 text-gray-500">
                      <span>{item.qty}개</span>
                      <span className="font-semibold text-gray-900">₩{(item.qty * item.price).toLocaleString()}</span>
                    </div>
                  </div>
                ))}
                <div className="flex justify-between items-center pt-3 text-sm font-bold text-gray-900">
                  <span>합계</span>
                  <span>₩{detailOrder.total.toLocaleString()}</span>
                </div>
              </div>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setDetailOrder(null)} className="btn-secondary flex-1">닫기</button>
              {detailOrder.status === 'ACTIVE' && (
                <button className="btn-primary flex-1">답변하기</button>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
