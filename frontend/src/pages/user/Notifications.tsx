import { useState, useEffect } from 'react'
import { ShoppingCart, Building2, Users, Bell, Check, CheckCheck, Trash2, Loader2 } from 'lucide-react'
import clsx from 'clsx'
import { alarmApi } from '../../services/alarm'
import type { Alarm } from '../../types'

type AlarmType = 'ORDER_REQUEST' | 'COMPANY_REGISTER_REQUEST' | 'COMPANY_CONNECT_REQUEST' | 'FRIEND_REQUEST' | 'GENERAL'

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
  const [items, setItems] = useState<Alarm[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    alarmApi.getList()
      .then(setItems)
      .catch(console.error)
      .finally(() => setLoading(false))
  }, [])

  const filtered = items.filter(a => {
    if (filter === '전체') return true
    const cfg = typeConfig[a.alarmType as AlarmType]
    return cfg?.label === filter
  })

  const unreadCount = items.filter(a => !a.isRead).length

  const markAllRead = () => {
    items.filter(a => !a.isRead).forEach(a => alarmApi.markRead(a.alarmNo).catch(console.error))
    setItems(prev => prev.map(a => ({ ...a, isRead: true })))
  }
  const markRead = (alarmNo: number) => {
    alarmApi.markRead(alarmNo).catch(console.error)
    setItems(prev => prev.map(a => a.alarmNo === alarmNo ? { ...a, isRead: true } : a))
  }
  const deleteAlarm = (alarmNo: number) => {
    alarmApi.delete(alarmNo).catch(console.error)
    setItems(prev => prev.filter(a => a.alarmNo !== alarmNo))
  }

  if (loading) return <div className="flex justify-center py-20"><Loader2 className="animate-spin text-primary-500" size={32} /></div>

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
          const cfg = typeConfig[alarm.alarmType as AlarmType] ?? typeConfig.GENERAL
          return (
            <div
              key={alarm.alarmNo}
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
                    <p className="text-xs text-gray-400 mt-1">{alarm.createdAt}</p>
                  </div>
                  <div className="flex items-center gap-1 flex-shrink-0">
                    {!alarm.isRead && (
                      <button
                        onClick={() => markRead(alarm.alarmNo)}
                        className="p-1.5 rounded-lg hover:bg-gray-100 text-gray-400 hover:text-gray-600 transition-colors"
                        title="읽음 처리"
                      >
                        <Check size={14} />
                      </button>
                    )}
                    <button
                      onClick={() => deleteAlarm(alarm.alarmNo)}
                      className="p-1.5 rounded-lg hover:bg-red-50 text-gray-400 hover:text-red-500 transition-colors"
                      title="삭제"
                    >
                      <Trash2 size={14} />
                    </button>
                  </div>
                </div>

                {/* Action buttons for actionable alarms */}
                {!alarm.isRead && (['FRIEND_REQUEST', 'COMPANY_CONNECT_REQUEST', 'COMPANY_REGISTER_REQUEST'] as string[]).includes(alarm.alarmType) && (
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
