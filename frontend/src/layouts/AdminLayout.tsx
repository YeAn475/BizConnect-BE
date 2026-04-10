import { Outlet, useLocation } from 'react-router-dom'
import AdminSidebar from './AdminSidebar'
import { Bell, Search } from 'lucide-react'

const pageTitles: Record<string, string> = {
  '/admin/dashboard': '대시보드',
  '/admin/companies': '기업 관리',
  '/admin/company-requests': '기업 가입 요청',
  '/admin/users': '회원 관리',
  '/admin/products': '상품 관리',
  '/admin/orders': '주문 관리',
  '/admin/inquiries': '문의 관리',
  '/admin/notices': '공지사항',
  '/admin/settings': '시스템 설정',
}

export default function AdminLayout() {
  const location = useLocation()
  const title = pageTitles[location.pathname] ?? '관리자 콘솔'

  return (
    <div className="min-h-screen bg-gray-50">
      <AdminSidebar />

      {/* Top Header */}
      <header className="fixed top-0 left-60 right-0 h-16 bg-white border-b border-gray-200 flex items-center px-6 gap-4 z-20">
        <h1 className="text-lg font-semibold text-gray-900">{title}</h1>
        <div className="flex-1" />
        <div className="relative w-64">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={15} />
          <input
            type="text"
            placeholder="검색..."
            className="w-full pl-9 pr-4 py-2 text-sm border border-gray-200 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:bg-white transition-all"
          />
        </div>
        <button className="relative w-9 h-9 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-colors">
          <Bell size={18} className="text-gray-600" />
          <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-red-500 rounded-full" />
        </button>
        <div className="flex items-center gap-2 pl-2 pr-3 py-1.5 rounded-lg bg-gray-900 text-white">
          <div className="w-6 h-6 rounded-full bg-primary-600 flex items-center justify-center text-xs font-bold">A</div>
          <span className="text-sm font-medium">관리자</span>
        </div>
      </header>

      <main className="ml-60 pt-16 min-h-screen">
        <div className="p-6">
          <Outlet />
        </div>
      </main>
    </div>
  )
}
