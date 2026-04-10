import { useState } from 'react'
import { Search, UserPlus, MessageSquare, Building2, Check, X, Users } from 'lucide-react'
import clsx from 'clsx'

const friends = [
  { id: 1, name: '이수진', company: 'LG전자', position: '팀장', email: 'sujin@lg.com', isOnline: true },
  { id: 2, name: '박철수', company: 'SK하이닉스', position: '매니저', email: 'park@sk.com', isOnline: false },
  { id: 3, name: '정수현', company: '현대자동차', position: '부장', email: 'suhyun@hyundai.com', isOnline: true },
  { id: 4, name: '최지영', company: 'POSCO', position: '대리', email: 'jiyoung@posco.com', isOnline: false },
  { id: 5, name: '최영준', company: 'KT', position: '매니저', email: 'yj.choi@kt.com', isOnline: true },
  { id: 6, name: '한명수', company: '롯데쇼핑', position: '임원', email: 'ms.han@lotte.com', isOnline: false },
]

const requests = [
  { id: 1, name: '박지수', company: 'LG전자', position: '매니저', time: '1시간 전', type: 'received' },
  { id: 2, name: '김도현', company: 'NAVER', position: '팀장', time: '3시간 전', type: 'received' },
  { id: 3, name: '오민석', company: '카카오', position: '매니저', time: '어제', type: 'sent' },
]

const suggested = [
  { id: 1, name: '송민아', company: '삼성SDI', position: '매니저', mutualCount: 3 },
  { id: 2, name: '윤성호', company: '두산중공업', position: '부장', mutualCount: 2 },
  { id: 3, name: '노재현', company: '효성', position: '팀장', mutualCount: 1 },
]

export default function Friends() {
  const [tab, setTab] = useState<'friends' | 'requests' | 'discover'>('friends')
  const [search, setSearch] = useState('')

  const filteredFriends = friends.filter(f =>
    f.name.includes(search) || f.company.includes(search)
  )

  return (
    <div className="space-y-5">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex gap-1 bg-gray-100 rounded-xl p-1">
          {([
            { key: 'friends', label: `파트너 (${friends.length})` },
            { key: 'requests', label: `요청 (${requests.filter(r => r.type === 'received').length})` },
            { key: 'discover', label: '추천' },
          ] as const).map(({ key, label }) => (
            <button
              key={key}
              onClick={() => setTab(key)}
              className={clsx(
                'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                tab === key ? 'bg-white text-gray-900 shadow-sm' : 'text-gray-500 hover:text-gray-700'
              )}
            >
              {label}
            </button>
          ))}
        </div>

        <div className="flex items-center gap-2">
          <div className="relative w-64">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={15} />
            <input
              type="text"
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="이름, 기업 검색..."
              className="input pl-9"
            />
          </div>
          <button className="btn-primary flex items-center gap-2 text-sm">
            <UserPlus size={14} />
            파트너 추가
          </button>
        </div>
      </div>

      {/* Friends List */}
      {tab === 'friends' && (
        <div>
          <div className="mb-4 flex items-center gap-3 text-sm text-gray-500">
            <span><strong className="text-gray-900">{filteredFriends.filter(f => f.isOnline).length}</strong>명 온라인</span>
            <span>·</span>
            <span>전체 <strong className="text-gray-900">{filteredFriends.length}</strong>명</span>
          </div>
          <div className="grid grid-cols-3 gap-4">
            {filteredFriends.map(friend => (
              <div key={friend.id} className="card hover:shadow-md hover:border-gray-300 transition-all">
                <div className="flex items-start gap-3">
                  <div className="relative flex-shrink-0">
                    <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-primary-100 to-indigo-200 flex items-center justify-center">
                      <span className="text-base font-bold text-primary-700">{friend.name[0]}</span>
                    </div>
                    <span className={clsx(
                      'absolute -bottom-0.5 -right-0.5 w-3.5 h-3.5 rounded-full border-2 border-white',
                      friend.isOnline ? 'bg-green-500' : 'bg-gray-300'
                    )} />
                  </div>
                  <div className="flex-1 min-w-0">
                    <h3 className="font-semibold text-gray-900 text-sm">{friend.name}</h3>
                    <p className="text-xs text-gray-500 truncate">{friend.position}</p>
                    <div className="flex items-center gap-1 mt-1">
                      <Building2 size={10} className="text-gray-400" />
                      <p className="text-xs text-gray-500 truncate">{friend.company}</p>
                    </div>
                  </div>
                </div>
                <div className="flex gap-2 mt-3 pt-3 border-t border-gray-100">
                  <button className="flex-1 flex items-center justify-center gap-1.5 py-1.5 text-xs font-medium text-primary-600 border border-primary-200 rounded-lg hover:bg-primary-50 transition-colors">
                    <MessageSquare size={12} /> 메시지
                  </button>
                  <button className="flex-1 flex items-center justify-center gap-1.5 py-1.5 text-xs font-medium text-gray-600 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
                    <Users size={12} /> 프로필
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Requests */}
      {tab === 'requests' && (
        <div className="space-y-4">
          <div>
            <h3 className="text-sm font-semibold text-gray-700 mb-3">받은 요청</h3>
            <div className="space-y-2">
              {requests.filter(r => r.type === 'received').map(req => (
                <div key={req.id} className="card flex items-center gap-4">
                  <div className="w-11 h-11 rounded-xl bg-gradient-to-br from-green-100 to-emerald-200 flex items-center justify-center flex-shrink-0">
                    <span className="font-bold text-green-700">{req.name[0]}</span>
                  </div>
                  <div className="flex-1">
                    <p className="font-semibold text-gray-900 text-sm">{req.name}</p>
                    <p className="text-xs text-gray-500">{req.company} · {req.position}</p>
                    <p className="text-xs text-gray-400 mt-0.5">{req.time}</p>
                  </div>
                  <div className="flex gap-2">
                    <button className="w-9 h-9 rounded-xl bg-green-500 text-white flex items-center justify-center hover:bg-green-600 transition-colors">
                      <Check size={16} />
                    </button>
                    <button className="w-9 h-9 rounded-xl bg-gray-100 text-gray-500 flex items-center justify-center hover:bg-gray-200 transition-colors">
                      <X size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div>
            <h3 className="text-sm font-semibold text-gray-700 mb-3">보낸 요청</h3>
            <div className="space-y-2">
              {requests.filter(r => r.type === 'sent').map(req => (
                <div key={req.id} className="card flex items-center gap-4">
                  <div className="w-11 h-11 rounded-xl bg-gradient-to-br from-blue-100 to-sky-200 flex items-center justify-center flex-shrink-0">
                    <span className="font-bold text-blue-700">{req.name[0]}</span>
                  </div>
                  <div className="flex-1">
                    <p className="font-semibold text-gray-900 text-sm">{req.name}</p>
                    <p className="text-xs text-gray-500">{req.company} · {req.position}</p>
                    <p className="text-xs text-gray-400 mt-0.5">{req.time}</p>
                  </div>
                  <button className="text-xs text-gray-500 border border-gray-200 px-3 py-1.5 rounded-lg hover:bg-gray-50 transition-colors">
                    취소
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {/* Discover */}
      {tab === 'discover' && (
        <div className="space-y-3">
          <p className="text-sm text-gray-500">함께 아는 파트너를 기반으로 추천합니다</p>
          {suggested.map(person => (
            <div key={person.id} className="card flex items-center gap-4">
              <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-purple-100 to-violet-200 flex items-center justify-center flex-shrink-0">
                <span className="text-base font-bold text-purple-700">{person.name[0]}</span>
              </div>
              <div className="flex-1">
                <p className="font-semibold text-gray-900">{person.name}</p>
                <p className="text-sm text-gray-500">{person.company} · {person.position}</p>
                <p className="text-xs text-primary-600 mt-0.5">함께 아는 파트너 {person.mutualCount}명</p>
              </div>
              <button className="btn-primary text-sm flex items-center gap-1.5">
                <UserPlus size={13} /> 요청
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
