import { Search, Bell, ChevronDown } from 'lucide-react'
import { useLocation } from 'react-router-dom'

const pageTitles: Record<string, string> = {
  '/dashboard': '대시보드',
  '/products': '상품 관리',
  '/companies': '기업 목록',
  '/orders': '주문 관리',
  '/friends': '비즈 네트워크',
  '/chat': '메시지',
  '/notifications': '알림',
  '/inquiries': '문의 관리',
  '/company-requests': '기업 가입 요청',
  '/profile': '내 프로필',
}

export default function Header() {
  const location = useLocation()
  const title = pageTitles[location.pathname] ?? 'BizConnect'

  return (
    <header className="fixed top-0 left-60 right-0 h-16 bg-white border-b border-gray-200 flex items-center px-6 gap-4 z-20">
      <h1 className="text-lg font-semibold text-gray-900">{title}</h1>

      <div className="flex-1" />

      {/* Search */}
      <div className="relative w-72">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
        <input
          type="text"
          placeholder="검색..."
          className="w-full pl-9 pr-4 py-2 text-sm border border-gray-200 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:bg-white transition-all"
        />
      </div>

      {/* Notification bell */}
      <button className="relative w-9 h-9 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-colors">
        <Bell size={18} className="text-gray-600" />
        <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-red-500 rounded-full" />
      </button>

      {/* User dropdown */}
      <button className="flex items-center gap-2 pl-2 pr-3 py-1.5 rounded-lg hover:bg-gray-100 transition-colors">
        <div className="w-7 h-7 rounded-full bg-primary-100 flex items-center justify-center">
          <span className="text-xs font-semibold text-primary-700">김</span>
        </div>
        <span className="text-sm font-medium text-gray-700">김민준</span>
        <ChevronDown size={14} className="text-gray-400" />
      </button>
    </header>
  )
}
