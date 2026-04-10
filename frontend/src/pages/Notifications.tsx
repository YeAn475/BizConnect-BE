import { useState } from 'react'
import { ShoppingCart, Building2, Users, Bell, Check, CheckCheck, Trash2 } from 'lucide-react'
import clsx from 'clsx'

type AlarmType = 'ORDER_REQUEST' | 'COMPANY_REGISTER_REQUEST' | 'COMPANY_CONNECT_REQUEST' | 'FRIEND_REQUEST' | 'GENERAL'

interface Alarm {
  id: number
  type: AlarmType
  title: string
  content: string
  time: string
  isRead: boolean
}

const alarms: Alarm[] = [
  { id: 1, type: 'ORDER_REQUEST', title: '새 주문 접수', content: '삼성전자로부터 ORD-1042 주문이 접수되었습니다. (SSD 1TB × 10개)', time: '10분 전', isRead: false },
  { id: 2, type: 'COMPANY_REGISTER_REQUEST', title: '기업 가입 요청', content: '(주)넥스텍에서 귀사 기업에 가입 요청을 보냈습니다.', time: '30분 전', isRead: false },
  { id: 3, type: 'FRIEND_REQUEST', title: '친구 요청', content: '박지수님(LG전자)이 비즈 파트너 요청을 보냈습니다.', time: '1시간 전', isRead: false },
  { id: 4, type: 'COMPANY_CONNECT_REQUEST', title: '기업 연결 요청', content: 'POSCO에서 비즈니스 연결 요청을 보냈습니다.', time: '2시간 전', isRead: false },
  { id: 5, type: 'GENERAL', title: '시스템 공지', content: '4월 15일(월) 새벽 2:00 ~ 4:00 정기 점검이 예정되어 있습니다.', time: '3시간 전', isRead: false },
  { id: 6, type: 'ORDER_REQUEST', title: '주문 상태 변경', content: 'ORD-1041 주문이 답변완료 상태로 변경되었습니다.', time: '어제', isRead: true },
  { id: 7, type: 'FRIEND_REQUEST', title: '친구 요청 수락', content: '최영준님이 귀하의 비즈 파트너 요청을 수락했습니다.', time: '어제', isRead: true },
  { id: 8, type: 'GENERAL', title: '이용 약관 업데이트', content: 'BizConnect 이용약관이 업데이트되었습니다. 2024년 5월 1일부터 적용됩니다.', time: '2일 전', isRead: true },
]

const typeConfig: Record<AlarmType, { icon: typeof ShoppingCart; color: string; bg: string; label: string }> = {
  ORDER_REQUEST: { icon: ShoppingCart, color: 'text-blue-600', bg: 'bg-blue-50', label: '주문' },
  COMPANY_REGISTER_REQUEST: { icon: Building2, color: 'text-purple-600', bg: 'bg-purple-50', label: '기업 가입' },
  COMPANY_CONNECT_REQUEST: { icon: Building2, color: 'text-indigo-600', bg: 'bg-indigo-50', label: '기업 연결' },
  FRIEND_REQUEST: { icon: Users, color: 'text-green-600', bg: 'bg-green-50', label: '친구 요청' },
  GENERAL: { icon: Bell, color: 'text-orange-500', bg: 'bg-orange-50', label: '공지' },
}

const filterTabs = ['전체', '주문', '기업 가입', '기업 연결', '친구 요청', '공지'] as const

export default function Notifications() {
  const [filter, setFilter] = useState<string>('전체')
  const [items, setItems] = useState(alarms)

  const filtered = items.filter(a => {
    if (filter === '전체') return true
    return typeConfig[a.type].label === filter
  })

  const unreadCount = items.filter(a => !a.isRead).length

  const markAllRead = () => setItems(prev => prev.map(a => ({ ...a, isRead: true })))
  const markRead = (id: number) => setItems(prev => prev.map(a => a.id === id ? { ...a, isRead: true } : a))
  const deleteAlarm = (id: number) => setItems(prev => prev.filter(a => a.id !== id))

  return (
    <div className="max-w-3xl mx-auto space-y-5">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          <h2 className="text-lg font-semibold text-gray-900">알림</h2>
          {unreadCount > 0 && (
            <span className="bg-red-500 text-white text-xs font-bold px-2 py-0.5 rounded-full">
              {unreadCount} 새 알림
            </span>
          )}
        </div>
        <div className="flex gap-2">
          <button onClick={markAllRead} className="btn-secondary text-sm flex items-center gap-1.5">
            <CheckCheck size={14} />
            전체 읽음
          </button>
        </div>
      </div>

      {/* Filter tabs */}
      <div className="flex gap-2 flex-wrap">
        {filterTabs.map(f => (
          <button
            key={f}
            onClick={() => setFilter(f)}
            className={clsx(
              'px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
              filter === f
                ? 'bg-primary-600 text-white'
                : 'bg-white text-gray-600 border border-gray-200 hover:border-gray-300'
            )}
          >
            {f}
          </button>
        ))}
      </div>

      {/* Alarm list */}
      <div className="space-y-2">
        {filtered.length === 0 && (
          <div className="py-20 text-center">
            <Bell size={36} className="text-gray-200 mx-auto mb-3" />
            <p className="text-sm text-gray-400">알림이 없습니다</p>
          </div>
        )}
        {filtered.map(alarm => {
          const cfg = typeConfig[alarm.type]
          return (
            <div
              key={alarm.id}
              className={clsx(
                'flex items-start gap-4 p-4 rounded-xl border transition-colors',
                alarm.isRead
                  ? 'bg-white border-gray-200'
                  : 'bg-blue-50/30 border-blue-100'
              )}
            >
              <div className={`p-2.5 rounded-xl flex-shrink-0 ${cfg.bg}`}>
                <cfg.icon size={18} className={cfg.color} />
              </div>
              <div className="flex-1 min-w-0">
                <div className="flex items-start justify-between gap-2">
                  <div>
                    <div className="flex items-center gap-2">
                      <h4 className="text-sm font-semibold text-gray-900">{alarm.title}</h4>
                      {!alarm.isRead && (
                        <span className="w-2 h-2 bg-blue-500 rounded-full flex-shrink-0" />
                      )}
                    </div>
                    <p className="text-sm text-gray-600 mt-0.5">{alarm.content}</p>
                    <p className="text-xs text-gray-400 mt-1">{alarm.time}</p>
                  </div>
                  <div className="flex items-center gap-1 flex-shrink-0">
                    {!alarm.isRead && (
                      <button
                        onClick={() => markRead(alarm.id)}
                        className="p-1.5 rounded-lg hover:bg-gray-100 text-gray-400 hover:text-gray-600 transition-colors"
                        title="읽음 처리"
                      >
                        <Check size={14} />
                      </button>
                    )}
                    <button
                      onClick={() => deleteAlarm(alarm.id)}
                      className="p-1.5 rounded-lg hover:bg-red-50 text-gray-400 hover:text-red-500 transition-colors"
                      title="삭제"
                    >
                      <Trash2 size={14} />
                    </button>
                  </div>
                </div>

                {/* Action buttons for actionable alarms */}
                {!alarm.isRead && (alarm.type === 'FRIEND_REQUEST' || alarm.type === 'COMPANY_CONNECT_REQUEST' || alarm.type === 'COMPANY_REGISTER_REQUEST') && (
                  <div className="flex gap-2 mt-2">
                    <button className="btn-primary text-xs py-1 px-3">수락</button>
                    <button className="btn-secondary text-xs py-1 px-3">거절</button>
                  </div>
                )}
              </div>
            </div>
          )
        })}
      </div>
    </div>
  )
}
