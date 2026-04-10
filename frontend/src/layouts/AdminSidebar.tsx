import { NavLink, useNavigate } from 'react-router-dom'
import {
  LayoutDashboard,
  Building2,
  Package,
  Users,
  ShoppingCart,
  HelpCircle,
  Megaphone,
  ClipboardList,
  Settings,
  LogOut,
  Zap,
  Shield,
} from 'lucide-react'
import clsx from 'clsx'

const navItems = [
  { path: '/admin/dashboard', label: '대시보드', icon: LayoutDashboard },
  { divider: '운영 관리' },
  { path: '/admin/companies', label: '기업 관리', icon: Building2 },
  { path: '/admin/company-requests', label: '가입 요청', icon: ClipboardList, badge: 2 },
  { path: '/admin/users', label: '회원 관리', icon: Users },
  { divider: '서비스 관리' },
  { path: '/admin/products', label: '상품 관리', icon: Package },
  { path: '/admin/orders', label: '주문 관리', icon: ShoppingCart },
  { path: '/admin/inquiries', label: '문의 관리', icon: HelpCircle, badge: 3 },
  { path: '/admin/notices', label: '공지사항', icon: Megaphone },
  { divider: '설정' },
  { path: '/admin/settings', label: '시스템 설정', icon: Settings },
] as const

export default function AdminSidebar() {
  const navigate = useNavigate()

  return (
    <aside className="fixed left-0 top-0 h-screen w-60 bg-gray-900 flex flex-col z-30">
      {/* Logo */}
      <div className="flex items-center gap-2.5 px-5 py-5 border-b border-gray-800">
        <div className="w-8 h-8 bg-primary-500 rounded-lg flex items-center justify-center">
          <Zap className="w-5 h-5 text-white" strokeWidth={2.5} />
        </div>
        <div>
          <span className="text-base font-bold text-white">BizConnect</span>
          <div className="flex items-center gap-1 mt-0.5">
            <Shield size={9} className="text-primary-400" />
            <p className="text-[10px] text-primary-400 leading-none font-medium">관리자 콘솔</p>
          </div>
        </div>
      </div>

      {/* Admin User Info */}
      <div className="flex items-center gap-3 px-4 py-3 mx-3 mt-3 bg-gray-800 rounded-xl">
        <div className="w-9 h-9 rounded-full bg-primary-600 flex items-center justify-center flex-shrink-0">
          <Shield size={16} className="text-white" />
        </div>
        <div className="min-w-0">
          <p className="text-sm font-semibold text-white truncate">관리자</p>
          <p className="text-xs text-gray-400 truncate">admin@bizconnect.io</p>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 overflow-y-auto px-3 py-3">
        {navItems.map((item, i) => {
          if ('divider' in item) {
            return (
              <p key={i} className="text-[10px] font-semibold text-gray-500 uppercase tracking-widest px-3 pt-4 pb-1.5">
                {item.divider}
              </p>
            )
          }
          const { path, label, icon: Icon, badge } = item as { path: string; label: string; icon: any; badge?: number }
          return (
            <NavLink
              key={path}
              to={path}
              className={({ isActive }) =>
                clsx(
                  'flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors duration-150 mb-0.5',
                  isActive
                    ? 'bg-primary-600 text-white'
                    : 'text-gray-400 hover:bg-gray-800 hover:text-white'
                )
              }
            >
              <Icon size={17} className="flex-shrink-0" />
              <span className="flex-1">{label}</span>
              {badge && (
                <span className="bg-red-500 text-white text-[10px] font-bold min-w-[18px] h-[18px] rounded-full flex items-center justify-center px-1">
                  {badge}
                </span>
              )}
            </NavLink>
          )
        })}
      </nav>

      {/* Switch to User View + Logout */}
      <div className="px-3 py-4 border-t border-gray-800 space-y-1">
        <button
          onClick={() => navigate('/dashboard')}
          className="flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium text-gray-400 hover:bg-gray-800 hover:text-white w-full transition-colors"
        >
          <Users size={17} />
          <span>유저 화면으로</span>
        </button>
        <button
          onClick={() => navigate('/login')}
          className="flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium text-gray-500 hover:bg-red-900/30 hover:text-red-400 w-full transition-colors"
        >
          <LogOut size={17} />
          <span>로그아웃</span>
        </button>
      </div>
    </aside>
  )
}
