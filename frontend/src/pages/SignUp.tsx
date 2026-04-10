import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Eye, EyeOff, Zap, CheckCircle2 } from 'lucide-react'

const steps = ['계정 정보', '개인 정보', '기업 연결']

export default function SignUp() {
  const navigate = useNavigate()
  const [step, setStep] = useState(0)
  const [showPassword, setShowPassword] = useState(false)
  const [form, setForm] = useState({
    email: '', password: '', confirmPassword: '',
    name: '', phoneNumber: '', address: '',
    companyName: '',
  })

  const update = (key: string) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
    setForm(f => ({ ...f, [key]: e.target.value }))

  const handleNext = (e: React.FormEvent) => {
    e.preventDefault()
    if (step < steps.length - 1) setStep(s => s + 1)
    else navigate('/login')
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-50 via-white to-indigo-50 flex items-center justify-center p-4">
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute -top-40 -right-40 w-96 h-96 bg-primary-100 rounded-full opacity-30 blur-3xl" />
        <div className="absolute -bottom-40 -left-40 w-96 h-96 bg-indigo-100 rounded-full opacity-30 blur-3xl" />
      </div>

      <div className="relative w-full max-w-md">
        {/* Logo */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-14 h-14 bg-primary-600 rounded-2xl shadow-lg mb-4">
            <Zap className="w-8 h-8 text-white" strokeWidth={2.5} />
          </div>
          <h1 className="text-2xl font-bold text-gray-900">BizConnect</h1>
          <p className="text-gray-500 mt-1 text-sm">기업 비즈니스 연결 플랫폼</p>
        </div>

        <div className="bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
          <h2 className="text-xl font-semibold text-gray-900 mb-2">회원가입</h2>

          {/* Step indicator */}
          <div className="flex items-center mb-6 mt-4">
            {steps.map((label, i) => (
              <div key={i} className="flex items-center flex-1">
                <div className="flex flex-col items-center">
                  <div className={`w-8 h-8 rounded-full flex items-center justify-center text-sm font-semibold transition-colors ${
                    i < step ? 'bg-primary-600 text-white' :
                    i === step ? 'bg-primary-600 text-white ring-4 ring-primary-100' :
                    'bg-gray-100 text-gray-400'
                  }`}>
                    {i < step ? <CheckCircle2 size={16} /> : i + 1}
                  </div>
                  <span className={`text-[11px] mt-1 whitespace-nowrap ${i === step ? 'text-primary-600 font-medium' : 'text-gray-400'}`}>
                    {label}
                  </span>
                </div>
                {i < steps.length - 1 && (
                  <div className={`flex-1 h-0.5 mb-4 mx-1 ${i < step ? 'bg-primary-600' : 'bg-gray-200'}`} />
                )}
              </div>
            ))}
          </div>

          <form onSubmit={handleNext} className="space-y-4">
            {step === 0 && (
              <>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">이메일</label>
                  <input type="email" value={form.email} onChange={update('email')} className="input" placeholder="이메일 주소" required />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">비밀번호</label>
                  <div className="relative">
                    <input
                      type={showPassword ? 'text' : 'password'}
                      value={form.password}
                      onChange={update('password')}
                      className="input pr-10"
                      placeholder="8자 이상 영문+숫자+특수문자"
                      required
                    />
                    <button type="button" onClick={() => setShowPassword(v => !v)} className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400">
                      {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                    </button>
                  </div>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">비밀번호 확인</label>
                  <input type="password" value={form.confirmPassword} onChange={update('confirmPassword')} className="input" placeholder="비밀번호 재입력" required />
                </div>
              </>
            )}

            {step === 1 && (
              <>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">이름</label>
                  <input type="text" value={form.name} onChange={update('name')} className="input" placeholder="실명을 입력하세요" required />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">연락처</label>
                  <input type="tel" value={form.phoneNumber} onChange={update('phoneNumber')} className="input" placeholder="010-0000-0000" required />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">주소</label>
                  <input type="text" value={form.address} onChange={update('address')} className="input" placeholder="주소를 입력하세요" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">직급</label>
                  <select className="input">
                    <option>매니저</option>
                    <option>팀장</option>
                    <option>부장</option>
                    <option>임원</option>
                    <option>대표</option>
                  </select>
                </div>
              </>
            )}

            {step === 2 && (
              <>
                <div className="bg-blue-50 rounded-xl p-4 text-sm text-blue-700">
                  <p className="font-medium mb-1">기업 연결 안내</p>
                  <p className="text-blue-600">기업명을 검색하여 소속 기업에 가입 요청을 보낼 수 있습니다. 관리자 승인 후 기업 기능이 활성화됩니다.</p>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1.5">기업 검색</label>
                  <input type="text" value={form.companyName} onChange={update('companyName')} className="input" placeholder="기업명을 검색하세요 (선택사항)" />
                </div>
                <div className="space-y-2">
                  {['삼성전자', 'LG전자', 'SK하이닉스'].map(name => (
                    <button
                      key={name}
                      type="button"
                      className="w-full flex items-center gap-3 p-3 border border-gray-200 rounded-lg hover:border-primary-400 hover:bg-primary-50 transition-colors text-left"
                    >
                      <div className="w-8 h-8 bg-gray-100 rounded-lg flex items-center justify-center text-xs font-bold text-gray-600">
                        {name[0]}
                      </div>
                      <div>
                        <p className="text-sm font-medium text-gray-900">{name}</p>
                        <p className="text-xs text-gray-500">IT·전자 · 서울특별시</p>
                      </div>
                    </button>
                  ))}
                </div>
              </>
            )}

            <div className="flex gap-3 pt-2">
              {step > 0 && (
                <button type="button" onClick={() => setStep(s => s - 1)} className="btn-secondary flex-1 py-3">
                  이전
                </button>
              )}
              <button type="submit" className="btn-primary flex-1 py-3">
                {step === steps.length - 1 ? '가입 완료' : '다음'}
              </button>
            </div>
          </form>

          <div className="mt-6 pt-6 border-t border-gray-100 text-center">
            <p className="text-sm text-gray-500">
              이미 계정이 있으신가요?{' '}
              <Link to="/login" className="text-primary-600 hover:text-primary-700 font-semibold">
                로그인
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}
