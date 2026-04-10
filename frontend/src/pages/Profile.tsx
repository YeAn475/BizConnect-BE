import { useState } from 'react'
import { User, Mail, Phone, MapPin, Building2, Briefcase, Camera, Lock, Trash2, Eye, EyeOff, Shield, Bell } from 'lucide-react'

export default function Profile() {
  const [tab, setTab] = useState<'info' | 'security' | 'notification'>('info')
  const [isEditing, setIsEditing] = useState(false)
  const [showPassword, setShowPassword] = useState(false)
  const [isPublic, setIsPublic] = useState(true)
  const [form, setForm] = useState({
    name: '김민준',
    email: 'minjun.kim@samsung.com',
    phoneNumber: '010-1234-5678',
    address: '서울특별시 서초구 삼성로 129',
    position: '매니저',
  })

  const tabs = [
    { key: 'info', label: '기본 정보', icon: User },
    { key: 'security', label: '보안', icon: Shield },
    { key: 'notification', label: '알림 설정', icon: Bell },
  ] as const

  return (
    <div className="max-w-3xl mx-auto space-y-6">
      {/* Profile Header Card */}
      <div className="card">
        <div className="flex items-start gap-6">
          {/* Avatar */}
          <div className="relative flex-shrink-0">
            <div className="w-20 h-20 rounded-2xl bg-gradient-to-br from-primary-400 to-indigo-500 flex items-center justify-center shadow-lg">
              <span className="text-3xl font-bold text-white">김</span>
            </div>
            <button className="absolute -bottom-1 -right-1 w-7 h-7 bg-white rounded-full shadow-md border border-gray-200 flex items-center justify-center hover:bg-gray-50 transition-colors">
              <Camera size={12} className="text-gray-600" />
            </button>
          </div>

          {/* Info */}
          <div className="flex-1">
            <div className="flex items-start justify-between">
              <div>
                <h2 className="text-xl font-bold text-gray-900">{form.name}</h2>
                <p className="text-sm text-gray-500">{form.email}</p>
                <div className="flex items-center gap-2 mt-2">
                  <span className="inline-flex items-center gap-1 text-xs font-medium bg-blue-50 text-blue-700 px-2 py-0.5 rounded-full">
                    <Building2 size={10} /> 삼성전자
                  </span>
                  <span className="inline-flex items-center gap-1 text-xs font-medium bg-purple-50 text-purple-700 px-2 py-0.5 rounded-full">
                    <Briefcase size={10} /> {form.position}
                  </span>
                  <span className="inline-flex items-center gap-1 text-xs font-medium bg-green-50 text-green-700 px-2 py-0.5 rounded-full">
                    활성 계정
                  </span>
                </div>
              </div>
              <div className="flex items-center gap-2">
                <div className="flex items-center gap-2 text-sm">
                  <span className="text-gray-500">프로필 공개</span>
                  <button
                    onClick={() => setIsPublic(v => !v)}
                    className={`relative w-10 h-5.5 rounded-full transition-colors ${isPublic ? 'bg-primary-500' : 'bg-gray-200'}`}
                    style={{ height: '22px' }}
                  >
                    <span className={`absolute top-0.5 w-4.5 h-4.5 rounded-full bg-white shadow transition-all ${isPublic ? 'left-5' : 'left-0.5'}`}
                      style={{ width: '18px', height: '18px', top: '2px', left: isPublic ? '20px' : '2px' }}
                    />
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="flex border-b border-gray-200">
        {tabs.map(({ key, label, icon: Icon }) => (
          <button
            key={key}
            onClick={() => setTab(key)}
            className={`flex items-center gap-2 px-4 py-2.5 text-sm font-medium border-b-2 transition-colors ${
              tab === key
                ? 'border-primary-600 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-700'
            }`}
          >
            <Icon size={15} />
            {label}
          </button>
        ))}
      </div>

      {/* Tab Content */}
      {tab === 'info' && (
        <div className="card space-y-5">
          <div className="flex items-center justify-between">
            <h3 className="font-semibold text-gray-900">기본 정보</h3>
            <button
              onClick={() => setIsEditing(v => !v)}
              className={isEditing ? 'btn-primary text-sm' : 'btn-secondary text-sm'}
            >
              {isEditing ? '저장' : '수정'}
            </button>
          </div>

          <div className="grid grid-cols-2 gap-4">
            {[
              { label: '이름', value: form.name, key: 'name', icon: User },
              { label: '이메일', value: form.email, key: 'email', icon: Mail },
              { label: '연락처', value: form.phoneNumber, key: 'phoneNumber', icon: Phone },
              { label: '직급', value: form.position, key: 'position', icon: Briefcase },
            ].map(({ label, value, key, icon: Icon }) => (
              <div key={key}>
                <label className="flex items-center gap-1.5 text-xs font-medium text-gray-500 mb-1.5">
                  <Icon size={12} /> {label}
                </label>
                {isEditing ? (
                  <input
                    type="text"
                    value={value}
                    onChange={e => setForm(f => ({ ...f, [key]: e.target.value }))}
                    className="input"
                  />
                ) : (
                  <p className="text-sm font-medium text-gray-900 py-2.5 px-3 bg-gray-50 rounded-lg">{value}</p>
                )}
              </div>
            ))}
            <div className="col-span-2">
              <label className="flex items-center gap-1.5 text-xs font-medium text-gray-500 mb-1.5">
                <MapPin size={12} /> 주소
              </label>
              {isEditing ? (
                <input
                  type="text"
                  value={form.address}
                  onChange={e => setForm(f => ({ ...f, address: e.target.value }))}
                  className="input"
                />
              ) : (
                <p className="text-sm font-medium text-gray-900 py-2.5 px-3 bg-gray-50 rounded-lg">{form.address}</p>
              )}
            </div>
          </div>
        </div>
      )}

      {tab === 'security' && (
        <div className="space-y-4">
          <div className="card space-y-4">
            <h3 className="font-semibold text-gray-900">비밀번호 변경</h3>
            <div className="space-y-3">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">현재 비밀번호</label>
                <div className="relative">
                  <input type={showPassword ? 'text' : 'password'} className="input pr-10" placeholder="현재 비밀번호" />
                  <button type="button" onClick={() => setShowPassword(v => !v)} className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400">
                    {showPassword ? <EyeOff size={16} /> : <Eye size={16} />}
                  </button>
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">새 비밀번호</label>
                <input type="password" className="input" placeholder="새 비밀번호 (8자 이상)" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">새 비밀번호 확인</label>
                <input type="password" className="input" placeholder="새 비밀번호 재입력" />
              </div>
            </div>
            <button className="btn-primary flex items-center gap-2">
              <Lock size={14} /> 비밀번호 변경
            </button>
          </div>

          <div className="card border-red-100">
            <h3 className="font-semibold text-red-600 mb-2">위험 구역</h3>
            <p className="text-sm text-gray-500 mb-4">계정을 삭제하면 모든 데이터가 영구적으로 삭제됩니다.</p>
            <button className="flex items-center gap-2 text-sm text-red-600 border border-red-200 px-4 py-2 rounded-lg hover:bg-red-50 transition-colors">
              <Trash2 size={14} /> 계정 삭제
            </button>
          </div>
        </div>
      )}

      {tab === 'notification' && (
        <div className="card space-y-4">
          <h3 className="font-semibold text-gray-900">알림 설정</h3>
          <div className="space-y-3">
            {[
              { label: '주문 알림', desc: '새 주문 및 주문 상태 변경 시 알림', enabled: true },
              { label: '기업 연결 요청', desc: '기업 가입/연결 요청 수신 시 알림', enabled: true },
              { label: '친구 요청', desc: '새 친구 요청 수신 시 알림', enabled: false },
              { label: '메시지', desc: '새 메시지 수신 시 알림', enabled: true },
              { label: '공지사항', desc: '시스템 공지 및 업데이트 알림', enabled: false },
            ].map(item => (
              <div key={item.label} className="flex items-center justify-between py-3 border-b border-gray-100 last:border-0">
                <div>
                  <p className="text-sm font-medium text-gray-800">{item.label}</p>
                  <p className="text-xs text-gray-400">{item.desc}</p>
                </div>
                <div className={`relative w-10 rounded-full cursor-pointer transition-colors ${item.enabled ? 'bg-primary-500' : 'bg-gray-200'}`} style={{ height: '22px' }}>
                  <span className={`absolute rounded-full bg-white shadow transition-all`} style={{ width: '18px', height: '18px', top: '2px', left: item.enabled ? '20px' : '2px' }} />
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
