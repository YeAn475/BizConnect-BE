import {
  AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  BarChart, Bar, PieChart, Pie, Cell,
} from 'recharts'
import {
  Users, Building2, Package, ShoppingCart,
  TrendingUp, TrendingDown, AlertCircle, CheckCircle, Clock, ArrowUpRight,
} from 'lucide-react'
import clsx from 'clsx'

const orderTrend = [
  { month: '10월', orders: 320, revenue: 42000000 },
  { month: '11월', orders: 410, revenue: 55000000 },
  { month: '12월', orders: 530, revenue: 71000000 },
  { month: '1월', orders: 480, revenue: 62000000 },
  { month: '2월', orders: 670, revenue: 88000000 },
  { month: '3월', orders: 750, revenue: 98000000 },
  { month: '4월', orders: 820, revenue: 107000000 },
]

const affiliationData = [
  { name: 'IT·전자', value: 38, color: '#3b82f6' },
  { name: '자동차', value: 22, color: '#8b5cf6' },
  { name: '철강·소재', value: 18, color: '#10b981' },
  { name: '유통·물류', value: 14, color: '#f59e0b' },
  { name: '기타', value: 8, color: '#6b7280' },
]

const recentJoinRequests = [
  { name: '(주)넥스텍', user: '김철민', affiliation: 'IT·전자', time: '14분 전', status: 'PENDING' },
  { name: '넥스트소프트', user: '박지수', affiliation: '소프트웨어', time: '1시간 전', status: 'PENDING' },
  { name: '글로벌테크', user: '오민석', affiliation: 'IT·전자', time: '3시간 전', status: 'APPROVED' },
]

const recentInquiries = [
  { title: '상품 재고 관련 문의', company: '삼성전자', category: '상품', time: '10분 전', status: 'PENDING' },
  { title: '주문 취소 요청', company: 'LG전자', category: '주문', time: '1시간 전', status: 'ANSWERED' },
  { title: '배송 지연 문의', company: '현대자동차', category: '배송', time: '3시간 전', status: 'PENDING' },
]

const kpiCards = [
  { label: '전체 회원', value: '1,284', sub: '+32 이번달', change: 12.4, icon: Users, color: 'bg-blue-500', bg: 'bg-blue-50', text: 'text-blue-600' },
  { label: '등록 기업', value: '247', sub: '+5 이번달', change: 8.2, icon: Building2, color: 'bg-purple-500', bg: 'bg-purple-50', text: 'text-purple-600' },
  { label: '전체 상품', value: '1,840', sub: '+68 이번달', change: 5.1, icon: Package, color: 'bg-green-500', bg: 'bg-green-50', text: 'text-green-600' },
  { label: '이번달 주문', value: '820', sub: '₩107,000,000', change: 9.3, icon: ShoppingCart, color: 'bg-orange-500', bg: 'bg-orange-50', text: 'text-orange-600' },
]

export default function AdminDashboard() {
  return (
    <div className="space-y-6">
      {/* KPI Cards */}
      <div className="grid grid-cols-4 gap-4">
        {kpiCards.map(({ label, value, sub, change, icon: Icon, bg, text }) => (
          <div key={label} className="card">
            <div className="flex items-start justify-between">
              <div>
                <p className="text-sm text-gray-500">{label}</p>
                <p className="text-2xl font-bold text-gray-900 mt-1">{value}</p>
                <p className="text-xs text-gray-400 mt-0.5">{sub}</p>
              </div>
              <div className={`${bg} p-2.5 rounded-xl`}>
                <Icon className={text} size={20} />
              </div>
            </div>
            <div className={clsx('flex items-center gap-1 mt-3 text-xs font-medium', change >= 0 ? 'text-green-600' : 'text-red-500')}>
              {change >= 0 ? <TrendingUp size={13} /> : <TrendingDown size={13} />}
              <span>{Math.abs(change)}% 전월 대비</span>
            </div>
          </div>
        ))}
      </div>

      {/* Alert bar */}
      <div className="bg-amber-50 border border-amber-200 rounded-xl px-5 py-3 flex items-center gap-3">
        <AlertCircle size={18} className="text-amber-500 flex-shrink-0" />
        <p className="text-sm text-amber-800">
          <strong>처리 대기 중:</strong> 기업 가입 요청 <strong className="text-amber-700">2건</strong>,
          미답변 문의 <strong className="text-amber-700">7건</strong>이 있습니다.
        </p>
        <a href="/admin/company-requests" className="ml-auto text-sm text-amber-700 font-semibold hover:text-amber-800 flex items-center gap-1">
          바로가기 <ArrowUpRight size={14} />
        </a>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-3 gap-4">
        <div className="card col-span-2">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">월별 주문 & 매출 추이</h3>
            <div className="flex items-center gap-3 text-xs text-gray-500">
              <span className="flex items-center gap-1"><span className="w-3 h-0.5 bg-primary-500 inline-block rounded" /> 주문수</span>
              <span className="flex items-center gap-1"><span className="w-3 h-0.5 bg-green-400 inline-block rounded" /> 매출</span>
            </div>
          </div>
          <ResponsiveContainer width="100%" height={210}>
            <AreaChart data={orderTrend}>
              <defs>
                <linearGradient id="ao" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.15} />
                  <stop offset="95%" stopColor="#3b82f6" stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
              <XAxis dataKey="month" tick={{ fontSize: 12, fill: '#9ca3af' }} />
              <YAxis tick={{ fontSize: 12, fill: '#9ca3af' }} />
              <Tooltip contentStyle={{ borderRadius: '8px', border: '1px solid #e5e7eb', fontSize: '12px' }} />
              <Area type="monotone" dataKey="orders" stroke="#3b82f6" strokeWidth={2} fill="url(#ao)" name="주문수" />
            </AreaChart>
          </ResponsiveContainer>
        </div>

        <div className="card">
          <h3 className="font-semibold text-gray-900 mb-4">업종별 기업 분포</h3>
          <ResponsiveContainer width="100%" height={160}>
            <PieChart>
              <Pie data={affiliationData} cx="50%" cy="50%" innerRadius={45} outerRadius={70} dataKey="value">
                {affiliationData.map((entry, index) => (
                  <Cell key={index} fill={entry.color} />
                ))}
              </Pie>
              <Tooltip contentStyle={{ borderRadius: '8px', border: '1px solid #e5e7eb', fontSize: '12px' }} />
            </PieChart>
          </ResponsiveContainer>
          <div className="space-y-1.5 mt-2">
            {affiliationData.map(item => (
              <div key={item.name} className="flex items-center justify-between text-xs">
                <div className="flex items-center gap-2">
                  <span className="w-2.5 h-2.5 rounded-full" style={{ backgroundColor: item.color }} />
                  <span className="text-gray-600">{item.name}</span>
                </div>
                <span className="font-semibold text-gray-800">{item.value}%</span>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Recent Requests + Inquiries */}
      <div className="grid grid-cols-2 gap-4">
        {/* Recent Join Requests */}
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">최근 가입 요청</h3>
            <a href="/admin/company-requests" className="text-xs text-primary-600 font-medium">전체보기</a>
          </div>
          <div className="space-y-3">
            {recentJoinRequests.map((req, i) => (
              <div key={i} className="flex items-center gap-3">
                <div className="w-9 h-9 rounded-xl bg-purple-50 flex items-center justify-center flex-shrink-0">
                  <Building2 size={16} className="text-purple-600" />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-900 truncate">{req.name}</p>
                  <p className="text-xs text-gray-400">{req.user} · {req.time}</p>
                </div>
                <span className={req.status === 'PENDING' ? 'badge-pending' : 'badge-active'}>
                  {req.status === 'PENDING' ? '대기' : '승인'}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Recent Inquiries */}
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-semibold text-gray-900">최근 문의</h3>
            <a href="/admin/inquiries" className="text-xs text-primary-600 font-medium">전체보기</a>
          </div>
          <div className="space-y-3">
            {recentInquiries.map((inq, i) => (
              <div key={i} className="flex items-center gap-3">
                <div className={`w-9 h-9 rounded-xl flex items-center justify-center flex-shrink-0 ${inq.status === 'PENDING' ? 'bg-red-50' : 'bg-green-50'}`}>
                  {inq.status === 'PENDING'
                    ? <Clock size={16} className="text-red-500" />
                    : <CheckCircle size={16} className="text-green-500" />
                  }
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-900 truncate">{inq.title}</p>
                  <p className="text-xs text-gray-400">{inq.company} · {inq.time}</p>
                </div>
                <span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">{inq.category}</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
