import { useState } from 'react'
import { Plus, Megaphone, Edit2, Trash2, Pin } from 'lucide-react'

// Notice 엔티티는 백엔드에 있지만 컨트롤러 미구현 → UI만
const notices = [
  { id: 1, title: '시스템 정기 점검 안내', content: '4월 15일(월) 새벽 2:00~4:00 정기 점검이 예정되어 있습니다. 해당 시간 동안 서비스 이용이 불가합니다.', pinned: true, createdAt: '2024-04-10' },
  { id: 2, title: '4월 이용약관 업데이트 안내', content: 'BizConnect 이용약관이 업데이트되었습니다. 주요 변경사항을 확인해주세요.', pinned: false, createdAt: '2024-04-08' },
  { id: 3, title: '신규 기능 출시: 비즈 파트너 추천', content: '함께 아는 파트너 기반으로 새로운 비즈니스 연결을 추천해드리는 기능이 출시되었습니다.', pinned: false, createdAt: '2024-04-05' },
  { id: 4, title: '3월 서비스 리포트', content: '3월 플랫폼 활동 현황을 공유드립니다. 총 주문 750건, 신규 기업 가입 12건 달성했습니다.', pinned: false, createdAt: '2024-04-01' },
]

export default function Notices() {
  const [showModal, setShowModal] = useState(false)
  const [form, setForm] = useState({ title: '', content: '', pinned: false })

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between">
        <p className="text-sm text-gray-500">전체 <strong className="text-gray-900">{notices.length}</strong>개 공지</p>
        <button onClick={() => setShowModal(true)} className="btn-primary flex items-center gap-2">
          <Plus size={15} /> 공지 작성
        </button>
      </div>

      <div className="space-y-3">
        {notices.map(notice => (
          <div key={notice.id} className="bg-white rounded-xl border border-gray-200 p-5 hover:shadow-sm transition-shadow">
            <div className="flex items-start gap-3">
              <div className={`p-2.5 rounded-xl flex-shrink-0 ${notice.pinned ? 'bg-primary-50' : 'bg-gray-50'}`}>
                {notice.pinned ? <Pin size={16} className="text-primary-600" /> : <Megaphone size={16} className="text-gray-500" />}
              </div>
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-1">
                  {notice.pinned && (
                    <span className="text-xs font-semibold bg-primary-600 text-white px-2 py-0.5 rounded-full">고정</span>
                  )}
                  <h3 className="font-semibold text-gray-900">{notice.title}</h3>
                </div>
                <p className="text-sm text-gray-600 line-clamp-2">{notice.content}</p>
                <p className="text-xs text-gray-400 mt-2">{notice.createdAt}</p>
              </div>
              <div className="flex gap-1 flex-shrink-0">
                <button className="p-1.5 rounded-lg hover:bg-gray-100 text-gray-400">
                  <Edit2 size={14} />
                </button>
                <button className="p-1.5 rounded-lg hover:bg-red-50 text-red-400">
                  <Trash2 size={14} />
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-xl">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">공지 작성</h2>
              <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-600 text-xl">×</button>
            </div>
            <div className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">제목</label>
                <input type="text" value={form.title} onChange={e => setForm(f => ({ ...f, title: e.target.value }))} className="input" placeholder="공지 제목" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">내용</label>
                <textarea value={form.content} onChange={e => setForm(f => ({ ...f, content: e.target.value }))} className="input h-36 resize-none" placeholder="공지 내용" />
              </div>
              <label className="flex items-center gap-2 cursor-pointer">
                <input type="checkbox" checked={form.pinned} onChange={e => setForm(f => ({ ...f, pinned: e.target.checked }))}
                  className="w-4 h-4 rounded border-gray-300 text-primary-600" />
                <span className="text-sm font-medium text-gray-700">상단 고정</span>
              </label>
            </div>
            <div className="flex gap-3 px-6 py-4 border-t border-gray-200">
              <button onClick={() => setShowModal(false)} className="btn-secondary flex-1">취소</button>
              <button className="btn-primary flex-1">게시</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
