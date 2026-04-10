import { useState, useEffect } from 'react'
import { Building2, Phone, MapPin, CreditCard, FileText, Edit2, Plus, Loader2 } from 'lucide-react'
import { companyApi } from '../../services/company'
import { userApi } from '../../services/user'
import type { Company } from '../../types'

export default function MyCompany() {
  const [company, setCompany] = useState<Company | null>(null)
  const [loading, setLoading] = useState(true)
  const [isEditing, setIsEditing] = useState(false)
  const [saving, setSaving] = useState(false)
  const [form, setForm] = useState({ name: '', phoneNumber: '', address: '' })
  const [showAccountModal, setShowAccountModal] = useState(false)
  const [showRegModal, setShowRegModal] = useState(false)
  const [accountNumber, setAccountNumber] = useState('')
  const [businessNumber, setBusinessNumber] = useState('')

  useEffect(() => {
    userApi.getProfile()
      .then(profile => {
        if (profile.company) {
          setCompany(profile.company)
          setForm({
            name: profile.company.name,
            phoneNumber: profile.company.phoneNumber,
            address: profile.company.address,
          })
        }
      })
      .catch(console.error)
      .finally(() => setLoading(false))
  }, [])

  const handleSave = async () => {
    setSaving(true)
    try {
      const updated = await companyApi.update(form)
      setCompany(updated)
      setIsEditing(false)
    } catch (e: any) {
      alert('저장 실패: ' + e.message)
    } finally {
      setSaving(false)
    }
  }

  const handleAddAccount = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await companyApi.addAccount(accountNumber)
      alert('법인 계좌가 등록되었습니다.')
      setShowAccountModal(false)
      setAccountNumber('')
    } catch (e: any) {
      alert('등록 실패: ' + e.message)
    }
  }

  const handleAddRegistration = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await companyApi.addRegistration(businessNumber)
      alert('사업자등록번호가 등록되었습니다.')
      setShowRegModal(false)
      setBusinessNumber('')
    } catch (e: any) {
      alert('등록 실패: ' + e.message)
    }
  }

  if (loading) return <div className="flex justify-center py-20"><Loader2 className="animate-spin text-primary-500" size={32} /></div>

  if (!company) {
    return (
      <div className="max-w-2xl mx-auto">
        <div className="card text-center py-12">
          <Building2 size={48} className="text-gray-200 mx-auto mb-4" />
          <h3 className="text-lg font-semibold text-gray-900 mb-2">소속 기업이 없습니다</h3>
          <p className="text-sm text-gray-500 mb-6">기업에 가입 요청하거나 새 기업을 등록해보세요.</p>
          <div className="flex gap-3 justify-center">
            <button className="btn-secondary">기업 검색 & 가입 요청</button>
            <button className="btn-primary">새 기업 등록</button>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-3xl mx-auto space-y-5">
      {/* Company Header */}
      <div className="card">
        <div className="flex items-start gap-5">
          <div className="w-16 h-16 rounded-2xl bg-gradient-to-br from-primary-100 to-indigo-200 flex items-center justify-center flex-shrink-0">
            <span className="text-2xl font-bold text-primary-700">{company.name[0]}</span>
          </div>
          <div className="flex-1">
            <div className="flex items-start justify-between">
              <div>
                <h2 className="text-xl font-bold text-gray-900">{company.name}</h2>
                <div className="flex gap-2 mt-1">
                  {company.affiliation && (
                    <span className="text-xs bg-primary-50 text-primary-700 px-2 py-0.5 rounded-full font-medium">{company.affiliation.name}</span>
                  )}
                  {company.branch && (
                    <span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">{company.branch.name}</span>
                  )}
                  <span className={company.isDeleted ? 'badge-rejected' : 'badge-active'}>
                    {company.isDeleted ? '비활성' : '활성'}
                  </span>
                </div>
              </div>
              <button
                onClick={isEditing ? handleSave : () => setIsEditing(true)}
                disabled={saving}
                className={`${isEditing ? 'btn-primary' : 'btn-secondary'} text-sm flex items-center gap-1.5`}
              >
                {saving && <Loader2 size={13} className="animate-spin" />}
                {isEditing ? '저장' : <><Edit2 size={14} /> 수정</>}
              </button>
            </div>

            <div className="grid grid-cols-2 gap-3 mt-4">
              {[
                { label: '전화번호', key: 'phoneNumber', value: company.phoneNumber, icon: Phone },
                { label: '주소', key: 'address', value: company.address, icon: MapPin, col2: true },
              ].map(({ label, key, value, icon: Icon, col2 }) => (
                <div key={key} className={col2 ? 'col-span-2' : ''}>
                  <label className="flex items-center gap-1 text-xs text-gray-500 mb-1">
                    <Icon size={11} /> {label}
                  </label>
                  {isEditing ? (
                    <input
                      type="text"
                      value={form[key as keyof typeof form]}
                      onChange={e => setForm(f => ({ ...f, [key]: e.target.value }))}
                      className="input text-sm"
                    />
                  ) : (
                    <p className="text-sm font-medium text-gray-900 py-2 px-3 bg-gray-50 rounded-lg">{value || '-'}</p>
                  )}
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* 법인 계좌 & 사업자번호 */}
      <div className="grid grid-cols-2 gap-4">
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-2">
              <CreditCard size={18} className="text-gray-500" />
              <h3 className="font-semibold text-gray-900">법인 계좌</h3>
            </div>
            <button onClick={() => setShowAccountModal(true)} className="w-7 h-7 bg-primary-600 rounded-lg flex items-center justify-center hover:bg-primary-700 transition-colors">
              <Plus size={14} className="text-white" />
            </button>
          </div>
          <p className="text-sm text-gray-400 text-center py-6">등록된 법인 계좌가 없습니다</p>
        </div>

        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-2">
              <FileText size={18} className="text-gray-500" />
              <h3 className="font-semibold text-gray-900">사업자등록번호</h3>
            </div>
            <button onClick={() => setShowRegModal(true)} className="w-7 h-7 bg-primary-600 rounded-lg flex items-center justify-center hover:bg-primary-700 transition-colors">
              <Plus size={14} className="text-white" />
            </button>
          </div>
          <p className="text-sm text-gray-400 text-center py-6">등록된 사업자번호가 없습니다</p>
        </div>
      </div>

      {/* 법인 계좌 추가 모달 */}
      {showAccountModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-sm">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">법인 계좌 등록</h2>
              <button onClick={() => setShowAccountModal(false)} className="text-gray-400 text-xl">×</button>
            </div>
            <form onSubmit={handleAddAccount} className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">계좌번호</label>
                <input type="text" required value={accountNumber} onChange={e => setAccountNumber(e.target.value)}
                  className="input" placeholder="000-0000-0000-00" />
              </div>
              <div className="flex gap-3">
                <button type="button" onClick={() => setShowAccountModal(false)} className="btn-secondary flex-1">취소</button>
                <button type="submit" className="btn-primary flex-1">등록</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* 사업자등록번호 추가 모달 */}
      {showRegModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-sm">
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
              <h2 className="text-lg font-semibold text-gray-900">사업자등록번호 등록</h2>
              <button onClick={() => setShowRegModal(false)} className="text-gray-400 text-xl">×</button>
            </div>
            <form onSubmit={handleAddRegistration} className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1.5">사업자등록번호</label>
                <input type="text" required value={businessNumber} onChange={e => setBusinessNumber(e.target.value)}
                  className="input" placeholder="000-00-00000" />
              </div>
              <div className="flex gap-3">
                <button type="button" onClick={() => setShowRegModal(false)} className="btn-secondary flex-1">취소</button>
                <button type="submit" className="btn-primary flex-1">등록</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
