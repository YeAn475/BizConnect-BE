import { NavLink, useNavigate } from 'react-router-dom'
import {
  LayoutDashboard,
  Package,
  Building2,
  ShoppingCart,
  MessageSquare,
  Bell,
  UserCircle,
  Users,
  HelpCircle,
  ClipboardList,
  LogOut,
  Zap,
} from 'lucide-react'
import clsx from 'clsx'

const navItems = [
  { path: '/dashboard', label: '대시보드', icon: LayoutDashboard },
  { path: '/products', label: '상품 관리', icon: Package },
  { path: '/companies', label: '기업 목록', icon: Building2 },
  { path: '/orders', label: '주문 관리', icon: ShoppingCart },
  { path: '/friends', label: '비즈 네트워크', icon: Users },
  { path: '/chat', label: '메시지', icon: MessageSquare, badge: 3 },
  { path: '/notifications', label: '알림', icon: Bell, badge: 5 },
  { path: '/inquiries', label: '문의 관리', icon: HelpCircle },
  { path: '/company-requests', label: '기업 가입 요청', icon: ClipboardList },
  { path: '/profile', label: '내 프로필', icon: UserCircle },
]

export default function Sidebar() {
  const navigate = useNavigate()

  return (
    <aside className="fixed left-0 top-0 h-screen w-60 bg-white border-r border-gray-200 flex flex-col z-30">
      {/* Logo */}
      <div className="flex items-center gap-2.5 px-5 py-5 border-b border-gray-200">
        <div className="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center">
          <Zap className="w-5 h-5 text-white" strokeWidth={2.5} />
        </div>
        <div>
          <span className="text-base font-bold text-gray-900">BizConnect</span>
          <p className="text-[10px] text-gray-400 leading-none mt-0.5">비즈니스 연결 플랫폼</p>
        </div>
      </div>

      {/* User Quick Info */}
      <div className="flex items-center gap-3 px-4 py-3 mx-3 mt-3 bg-gray-50 rounded-xl">
        <div className="w-9 h-9 rounded-full bg-primary-100 flex items-center justify-center flex-shrink-0">
          <span className="text-sm font-semibold text-primary-700">김</span>
        </div>
        <div className="min-w-0">
          <p className="text-sm font-semibold text-gray-900 truncate">김민준</p>
          <p className="text-xs text-gray-500 truncate">삼성전자 · 매니저</p>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 overflow-y-auto px-3 py-3 space-y-0.5">
        {navItems.map(({ path, label, icon: Icon, badge }) => (
          <NavLink
            key={path}
            to={path}
            className={({ isActive }) =>
              clsx(
                'flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors duration-150',
                isActive
                  ? 'bg-primary-50 text-primary-700'
                  : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
              )
            }
          >
            <Icon className="w-4.5 h-4.5 flex-shrink-0" size={18} />
            <span className="flex-1">{label}</span>
            {badge && (
              <span className="ml-auto bg-primary-600 text-white text-[10px] font-bold w-4.5 h-4.5 rounded-full flex items-center justify-center min-w-[18px] h-[18px] px-1">
                {badge}
              </span>
            )}
          </NavLink>
        ))}
      </nav>

      {/* Logout */}
      <div className="px-3 py-4 border-t border-gray-200">
        <button
          onClick={() => navigate('/login')}
          className="flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium text-gray-500 hover:bg-red-50 hover:text-red-600 w-full transition-colors duration-150"
        >
          <LogOut size={18} />
          <span>로그아웃</span>
        </button>
      </div>
    </aside>
  )
}
