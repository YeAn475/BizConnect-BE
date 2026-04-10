import {
  AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  BarChart, Bar,
} from 'recharts'
import {
  ShoppingCart, Package, Building2, Users,
  TrendingUp, ArrowUpRight, ArrowDownRight, Bell, CheckCircle, Clock, AlertCircle,
} from 'lucide-react'
import clsx from 'clsx'

const areaData = [
  { month: '10월', orders: 42, products: 28 },
  { month: '11월', orders: 58, products: 35 },
  { month: '12월', orders: 71, products: 40 },
  { month: '1월', orders: 55, products: 32 },
  { month: '2월', orders: 89, products: 52 },
  { month: '3월', orders: 102, products: 61 },
  { month: '4월', orders: 118, products: 74 },
]

const categoryData = [
  { name: '전자부품', value: 34 },
  { name: '소프트웨어', value: 28 },
  { name: '사무용품', value: 19 },
  { name: '기계부품', value: 14 },
  { name: '기타', value: 12 },
]

const recentOrders = [
  { no: 'ORD-1042', company: '삼성전자', product: 'SSD 1TB (10개)', amount: '₩850,000', status: 'ACTIVE', time: '10분 전' },
  { no: 'ORD-1041', company: 'LG전자', product: '노트북 거치대 (5개)', amount: '₩125,000', status: 'ANSWERED', time: '1시간 전' },
  { no: 'ORD-1040', company: 'SK하이닉스', product: 'RAM 16GB (20개)', amount: '₩1,200,000', status: 'CLOSED', time: '3시간 전' },
  { no: 'ORD-1039', company: '현대자동차', product: '케이블 타이 (100개)', amount: '₩45,000', status: 'ACTIVE', time: '5시간 전' },
  { no: 'ORD-1038', company: 'POSCO', product: '프린터 잉크 (8개)', amount: '₩96,000', status: 'ANSWERED', time: '1일 전' },
]

const alarms = [
  { type: 'order', message: 'ORD-1042 주문이 접수되었습니다', time: '10분 전', icon: ShoppingCart, color: 'text-blue-500 bg-blue-50' },
  { type: 'company', message: '(주)넥스텍에서 기업 가입 요청을 보냈습니다', time: '30분 전', icon: Building2, color: 'text-purple-500 bg-purple-50' },
  { type: 'friend', message: '박지수님이 친구 요청을 보냈습니다', time: '1시간 전', icon: Users, color: 'text-green-500 bg-green-50' },
  { type: 'general', message: '시스템 점검 안내 (4/15 02:00~04:00)', time: '2시간 전', icon: Bell, color: 'text-orange-500 bg-orange-50' },
]

const statCards = [
  { label: '이번달 주문', value: '118', change: +18.2, icon: ShoppingCart, color: 'bg-blue-500', bg: 'bg-blue-50', text: 'text-blue-600' },
  { label: '등록 상품', value: '284', change: +5.4, icon: Package, color: 'bg-green-500', bg: 'bg-green-50', text: 'text-green-600' },
  { label: '연결 기업', value: '47', change: +12.0, icon: Building2, color: 'bg-purple-500', bg: 'bg-purple-50', text: 'text-purple-600' },
  { label: '비즈 파트너', value: '132', change: -2.1, icon: Users, color: 'bg-orange-500', bg: 'bg-orange-50', text: 'text-orange-600' },
]

const statusMap: Record<string, { label: string; className: string }> = {
  ACTIVE: { label: '진행중', className: 'badge-active' },
  ANSWERED: { label: '답변완료', className: 'badge-pending' },
  CLOSED: { label: '완료', className: 'badge-closed' },
}

export default function Dashboard() {
  return (
    <div className="space-y-6">
      {/* Welcome Banner */}
      <div className="bg-gradient-to-r from-primary-600 to-indigo-600 rounded-2xl p-6 text-white">
        <div className="flex items-start justify-between">
          <div>
            <h2 className="text-xl font-bold">안녕하세요, 김민준 님 👋</h2>
            <p className="text-primary-100 mt-1 text-sm">오늘도 성공적인 비즈니스 연결을 시작해보세요.</p>
          </div>
          <div className="text-right">
            <p className="text-primary-200 text-xs">삼성전자 | 매니저</p>
            <p className="text-white font-semibold mt-0.5">2024년 4월 10일</p>
          </div>
        </div>
        <div className="flex gap-4 mt-4">
          <div className="bg-white/20 rounded-xl px-4 py-2 text-center">
            <p className="text-lg font-bold">5</p>
            <p className="text-xs text-primary-100">미확인 알림</p>
          </div>
          <div className="bg-white/20 rounded-xl px-4 py-2 text-center">
            <p className="text-lg font-bold">3</p>
            <p className="text-xs text-primary-100">처리 대기 주문</p>
          </div>
          <div className="bg-white/20 rounded-xl px-4 py-2 text-center">
            <p className="text-lg font-bold">2</p>
            <p className="text-xs text-primary-100">가입 요청</p>
          </div>
        </div>
      </div>

      {/* Stat Cards */}
      <div className="grid grid-cols-4 gap-4">
        {statCards.map(({ label, value, change, icon: Icon, color, bg, text }) => (
          <div key={label} className="card">
            <div className="flex items-start justify-between">
              <div>
                <p className="text-sm text-gray-500">{label}</p>
                <p className="text-2xl font-bold text-gray-900 mt-1">{value}</p>
              </div>
              <div className={`${bg} p-2.5 rounded-xl`}>
                <Icon className={text} size={20} />
              </div>
            </div>
            <div className={clsx(
              'flex items-center gap-1 mt-3 text-xs font-medium',
              change >= 0 ? 'text-green-600' : 'text-red-500'
            )}>
              {change >= 0 ? <ArrowUpRight size={14} /> : <ArrowDownRight size={14} />}
              <span>{Math.abs(change)}% 전월 대비</span>
            </div>
          </div>
        ))}
      </div>

      {/* Charts */}
      <div className="grid grid-cols-3 gap-4">
        <div className="card col-span-2">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">주문 & 상품 추이</h3>
            <div className="flex items-center gap-3 text-xs text-gray-500">
              <span className="flex items-center gap-1"><span className="w-3 h-0.5 bg-primary-500 inline-block rounded" /> 주문</span>
              <span className="flex items-center gap-1"><span className="w-3 h-0.5 bg-green-400 inline-block rounded" /> 상품</span>
            </div>
          </div>
          <ResponsiveContainer width="100%" height={200}>
            <AreaChart data={areaData}>
              <defs>
                <linearGradient id="orders" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.15} />
                  <stop offset="95%" stopColor="#3b82f6" stopOpacity={0} />
                </linearGradient>
                <linearGradient id="products" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#4ade80" stopOpacity={0.15} />
                  <stop offset="95%" stopColor="#4ade80" stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
              <XAxis dataKey="month" tick={{ fontSize: 12, fill: '#9ca3af' }} />
              <YAxis tick={{ fontSize: 12, fill: '#9ca3af' }} />
              <Tooltip contentStyle={{ borderRadius: '8px', border: '1px solid #e5e7eb', fontSize: '12px' }} />
              <Area type="monotone" dataKey="orders" stroke="#3b82f6" strokeWidth={2} fill="url(#orders)" />
              <Area type="monotone" dataKey="products" stroke="#4ade80" strokeWidth={2} fill="url(#products)" />
            </AreaChart>
          </ResponsiveContainer>
        </div>

        <div className="card">
          <h3 className="font-semibold text-gray-900 mb-4">카테고리별 주문</h3>
          <ResponsiveContainer width="100%" height={200}>
            <BarChart data={categoryData} layout="vertical">
              <XAxis type="number" tick={{ fontSize: 11, fill: '#9ca3af' }} />
              <YAxis type="category" dataKey="name" tick={{ fontSize: 11, fill: '#6b7280' }} width={60} />
              <Tooltip contentStyle={{ borderRadius: '8px', border: '1px solid #e5e7eb', fontSize: '12px' }} />
              <Bar dataKey="value" fill="#3b82f6" radius={[0, 4, 4, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Recent Orders + Alarms */}
      <div className="grid grid-cols-3 gap-4">
        {/* Recent Orders */}
        <div className="card col-span-2">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">최근 주문</h3>
            <a href="/orders" className="text-sm text-primary-600 hover:text-primary-700 font-medium flex items-center gap-1">
              전체보기 <TrendingUp size={14} />
            </a>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-gray-100">
                  <th className="table-header">주문번호</th>
                  <th className="table-header">기업</th>
                  <th className="table-header">상품</th>
                  <th className="table-header">금액</th>
                  <th className="table-header">상태</th>
                  <th className="table-header">시간</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-50">
                {recentOrders.map(order => (
                  <tr key={order.no} className="hover:bg-gray-50 transition-colors">
                    <td className="table-cell font-mono text-xs text-gray-500">{order.no}</td>
                    <td className="table-cell font-medium">{order.company}</td>
                    <td className="table-cell text-gray-600">{order.product}</td>
                    <td className="table-cell font-semibold text-gray-900">{order.amount}</td>
                    <td className="table-cell">
                      <span className={statusMap[order.status].className}>{statusMap[order.status].label}</span>
                    </td>
                    <td className="table-cell text-gray-400 text-xs">{order.time}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Alarms */}
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">최근 알림</h3>
            <span className="text-xs bg-red-100 text-red-600 px-2 py-0.5 rounded-full font-medium">5 NEW</span>
          </div>
          <div className="space-y-3">
            {alarms.map((alarm, i) => (
              <div key={i} className="flex items-start gap-3 p-3 rounded-xl hover:bg-gray-50 cursor-pointer transition-colors">
                <div className={`p-2 rounded-lg flex-shrink-0 ${alarm.color}`}>
                  <alarm.icon size={14} />
                </div>
                <div className="min-w-0">
                  <p className="text-xs font-medium text-gray-800 leading-tight">{alarm.message}</p>
                  <p className="text-[11px] text-gray-400 mt-1">{alarm.time}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Quick Status */}
      <div className="grid grid-cols-3 gap-4">
        {[
          { label: '처리 대기 주문', value: 3, icon: Clock, color: 'text-yellow-500', bg: 'bg-yellow-50', desc: '즉시 처리 필요' },
          { label: '완료된 주문 (이번달)', value: 89, icon: CheckCircle, color: 'text-green-500', bg: 'bg-green-50', desc: '월간 목표 88% 달성' },
          { label: '미처리 문의', value: 7, icon: AlertCircle, color: 'text-red-500', bg: 'bg-red-50', desc: '3일 이상 미답변 2건' },
        ].map(item => (
          <div key={item.label} className="card flex items-center gap-4">
            <div className={`${item.bg} p-3 rounded-xl`}>
              <item.icon className={item.color} size={22} />
            </div>
            <div>
              <p className="text-2xl font-bold text-gray-900">{item.value}</p>
              <p className="text-sm font-medium text-gray-700">{item.label}</p>
              <p className="text-xs text-gray-400">{item.desc}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
