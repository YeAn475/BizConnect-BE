import { useState } from 'react'
import { Search, Filter, Download, Eye, Ban, UserCheck, MoreVertical } from 'lucide-react'
import clsx from 'clsx'

const users = [
  { no: 1, name: '김민준', email: 'minjun.kim@samsung.com', company: '삼성전자', position: '매니저', role: 'USER', status: '활성', isOpen: true, joinedAt: '2023-03-15' },
  { no: 2, name: '이수진', email: 'sujin@lg.com', company: 'LG전자', position: '팀장', role: 'USER', status: '활성', isOpen: true, joinedAt: '2023-05-22' },
  { no: 3, name: '박철수', email: 'park@sk.com', company: 'SK하이닉스', position: '매니저', role: 'USER', status: '활성', isOpen: false, joinedAt: '2023-07-10' },
  { no: 4, name: '정수현', email: 'suhyun@hyundai.com', company: '현대자동차', position: '부장', role: 'MANAGER', status: '활성', isOpen: true, joinedAt: '2023-04-01' },
  { no: 5, name: '최지영', email: 'jiyoung@posco.com', company: 'POSCO', position: '대리', role: 'USER', status: '비활성', isOpen: false, joinedAt: '2023-09-18' },
  { no: 6, name: '한명수', email: 'ms.han@lotte.com', company: '롯데쇼핑', position: '임원', role: 'MANAGER', status: '활성', isOpen: true, joinedAt: '2023-02-28' },
  { no: 7, name: '강수연', email: 'sy.kang@kt.com', company: 'KT', position: '매니저', role: 'USER', status: '활성', isOpen: true, joinedAt: '2024-01-05' },
  { no: 8, name: '오민석', email: 'ms.oh@globaltech.com', company: '-', position: '팀장', role: 'USER', status: '비활성', isOpen: false, joinedAt: '2024-02-14' },
]

const roleColors: Record<string, string> = {
  USER: 'bg-gray-100 text-gray-600',
  MANAGER: 'bg-blue-50 text-blue-600',
  ADMIN: 'bg-purple-50 text-purple-600',
}

export default function Users() {
  const [search, setSearch] = useState('')
  const [statusFilter, setStatusFilter] = useState('전체')
  const [selectedUser, setSelectedUser] = useState<typeof users[0] | null>(null)

  const filtered = users.filter(u =>
    (statusFilter === '전체' || u.status === statusFilter) &&
    (u.name.includes(search) || u.email.includes(search) || u.company.includes(search))
  )

  return (
    <div className="space-y-5">
      {/* Stats */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: '전체 회원', value: users.length, color: 'text-gray-900', bg: 'bg-gray-50' },
          { label: '활성 회원', value: users.filter(u => u.status === '활성').length, color: 'text-green-700', bg: 'bg-green-50' },
          { label: '비활성', value: users.filter(u => u.status === '비활성').length, color: 'text-red-700', bg: 'bg-red-50' },
          { label: '매니저', value: users.filter(u => u.role === 'MANAGER').length, color: 'text-blue-700', bg: 'bg-blue-50' },
        ].map(item => (
          <div key={item.label} className={`${item.bg} rounded-xl p-4`}>
            <p className="text-xs text-gray-500">{item.label}</p>
            <p className={`text-2xl font-bold mt-1 ${item.color}`}>{item.value}</p>
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
            placeholder="이름, 이메일, 기업 검색..."
            className="input pl-9"
          />
        </div>
        <div className="flex border border-gray-200 rounded-lg overflow-hidden">
          {['전체', '활성', '비활성'].map(s => (
            <button
              key={s}
              onClick={() => setStatusFilter(s)}
              className={clsx(
                'px-3 py-2 text-sm font-medium transition-colors',
                statusFilter === s ? 'bg-gray-900 text-white' : 'text-gray-500 hover:bg-gray-50'
              )}
            >
              {s}
            </button>
          ))}
        </div>
        <button className="btn-secondary flex items-center gap-2 ml-auto">
          <Download size={14} /> 내보내기
        </button>
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200">
            <tr>
              <th className="table-header">번호</th>
              <th className="table-header">이름</th>
              <th className="table-header">이메일</th>
              <th className="table-header">기업</th>
              <th className="table-header">직급</th>
              <th className="table-header">권한</th>
              <th className="table-header">상태</th>
              <th className="table-header">프로필 공개</th>
              <th className="table-header">가입일</th>
              <th className="table-header w-20">관리</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {filtered.map(user => (
              <tr key={user.no} className="hover:bg-gray-50 transition-colors">
                <td className="table-cell text-gray-400 text-xs">{user.no}</td>
                <td className="table-cell">
                  <div className="flex items-center gap-2">
                    <div className="w-7 h-7 rounded-full bg-primary-100 flex items-center justify-center text-xs font-semibold text-primary-700">
                      {user.name[0]}
                    </div>
                    <span className="font-medium text-gray-900">{user.name}</span>
                  </div>
                </td>
                <td className="table-cell text-gray-500">{user.email}</td>
                <td className="table-cell font-medium text-gray-700">{user.company}</td>
                <td className="table-cell text-gray-500">{user.position}</td>
                <td className="table-cell">
                  <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${roleColors[user.role]}`}>{user.role}</span>
                </td>
                <td className="table-cell">
                  <span className={user.status === '활성' ? 'badge-active' : 'badge-rejected'}>{user.status}</span>
                </td>
                <td className="table-cell text-center">
                  <span className={`text-xs font-medium ${user.isOpen ? 'text-green-600' : 'text-gray-400'}`}>
                    {user.isOpen ? '공개' : '비공개'}
                  </span>
                </td>
                <td className="table-cell text-gray-400 text-xs">{user.joinedAt}</td>
                <td className="table-cell">
                  <div className="flex items-center gap-1">
                    <button onClick={() => setSelectedUser(user)} className="p-1.5 rounded hover:bg-gray-100 text-gray-500">
                      <Eye size={13} />
                    </button>
                    <button className="p-1.5 rounded hover:bg-red-50 text-gray-400 hover:text-red-500">
                      <Ban size={13} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* User Detail Modal */}
      {selectedUser && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">회원 상세</h2>
              <button onClick={() => setSelectedUser(null)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div className="flex items-center gap-4">
                <div className="w-14 h-14 rounded-2xl bg-primary-100 flex items-center justify-center">
                  <span className="text-2xl font-bold text-primary-700">{selectedUser.name[0]}</span>
                </div>
                <div>
                  <h3 className="text-lg font-bold text-gray-900">{selectedUser.name}</h3>
                  <p className="text-sm text-gray-500">{selectedUser.email}</p>
                  <div className="flex gap-2 mt-1">
                    <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${roleColors[selectedUser.role]}`}>{selectedUser.role}</span>
                    <span className={selectedUser.status === '활성' ? 'badge-active' : 'badge-rejected'}>{selectedUser.status}</span>
                  </div>
                </div>
              </div>
              <div className="grid grid-cols-2 gap-3 text-sm">
                {[
                  { label: '기업', value: selectedUser.company },
                  { label: '직급', value: selectedUser.position },
                  { label: '가입일', value: selectedUser.joinedAt },
                  { label: '프로필 공개', value: selectedUser.isOpen ? '공개' : '비공개' },
                ].map(({ label, value }) => (
                  <div key={label} className="bg-gray-50 rounded-xl p-3">
                    <p className="text-xs text-gray-500 mb-1">{label}</p>
                    <p className="font-semibold text-gray-900">{value}</p>
                  </div>
                ))}
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">권한 변경</label>
                <select defaultValue={selectedUser.role} className="input">
                  <option value="USER">USER</option>
                  <option value="MANAGER">MANAGER</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
              </div>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setSelectedUser(null)} className="btn-secondary flex-1">닫기</button>
              <button className="flex-1 flex items-center justify-center gap-2 bg-red-600 text-white py-2 rounded-lg hover:bg-red-700 text-sm font-medium transition-colors">
                <Ban size={14} /> 계정 정지
              </button>
              <button className="btn-primary flex-1">저장</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
