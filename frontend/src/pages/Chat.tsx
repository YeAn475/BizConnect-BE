import { useState } from 'react'
import { Search, Send, Plus, Smile, Paperclip, MoreVertical } from 'lucide-react'
import clsx from 'clsx'

const chatrooms = [
  { id: 1, name: '삼성전자 구매팀', lastMessage: '네, 확인했습니다. 내일까지 처리해드리겠습니다.', time: '14:32', unread: 2, participants: 8 },
  { id: 2, name: 'LG전자 이수진', lastMessage: '견적서 보내드렸습니다 확인해주세요!', time: '11:20', unread: 0, participants: 2 },
  { id: 3, name: 'SK하이닉스 박철수', lastMessage: '다음 주 미팅 일정 조율 부탁드립니다', time: '09:15', unread: 5, participants: 2 },
  { id: 4, name: '파트너사 공지방', lastMessage: '4월 정기 미팅 안내: 4/15(월) 오후 2시', time: '어제', unread: 0, participants: 24 },
  { id: 5, name: '현대자동차 정수현', lastMessage: '감사합니다!', time: '어제', unread: 0, participants: 2 },
  { id: 6, name: 'POSCO 최지영', lastMessage: '확인 중입니다, 잠시만 기다려주세요', time: '2일 전', unread: 0, participants: 2 },
]

const messages = [
  { id: 1, sender: '이수진', senderInitial: '이', isMine: false, content: '안녕하세요! 지난번 주문 건 관련해서 문의드립니다.', time: '10:42', read: true },
  { id: 2, sender: '나', senderInitial: '김', isMine: true, content: '안녕하세요 이수진님! 네, 말씀해주세요.', time: '10:43', read: true },
  { id: 3, sender: '이수진', senderInitial: '이', isMine: false, content: 'ORD-1041 주문 건인데요, 배송 일정을 조금 앞당길 수 있을까요? 이번 주 금요일까지 가능할지요?', time: '10:44', read: true },
  { id: 4, sender: '나', senderInitial: '김', isMine: true, content: '확인해보겠습니다. 재고 현황이랑 배송사 일정을 체크해서 연락드리겠습니다!', time: '10:46', read: true },
  { id: 5, sender: '이수진', senderInitial: '이', isMine: false, content: '감사합니다! 빠른 답변 기대하겠습니다 😊', time: '10:47', read: true },
  { id: 6, sender: '나', senderInitial: '김', isMine: true, content: '배송 일정 확인했습니다. 이번 주 목요일 오전 중으로 배송 가능합니다. 괜찮으실까요?', time: '11:15', read: true },
  { id: 7, sender: '이수진', senderInitial: '이', isMine: false, content: '네, 확인했습니다. 내일까지 처리해드리겠습니다.', time: '11:20', read: false },
]

export default function Chat() {
  const [activeRoom, setActiveRoom] = useState(chatrooms[1])
  const [input, setInput] = useState('')
  const [search, setSearch] = useState('')

  const filteredRooms = chatrooms.filter(r => r.name.includes(search))

  const handleSend = () => {
    if (!input.trim()) return
    setInput('')
  }

  return (
    <div className="flex h-[calc(100vh-7rem)] bg-white rounded-xl border border-gray-200 overflow-hidden">
      {/* Chatroom List */}
      <div className="w-72 border-r border-gray-200 flex flex-col flex-shrink-0">
        {/* Header */}
        <div className="px-4 py-3 border-b border-gray-200">
          <div className="flex items-center justify-between mb-3">
            <h2 className="font-semibold text-gray-900">메시지</h2>
            <button className="w-7 h-7 bg-primary-600 rounded-lg flex items-center justify-center hover:bg-primary-700 transition-colors">
              <Plus size={14} className="text-white" />
            </button>
          </div>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={14} />
            <input
              type="text"
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="대화 검색..."
              className="w-full pl-8 pr-3 py-1.5 text-sm border border-gray-200 rounded-lg bg-gray-50 focus:outline-none focus:ring-1 focus:ring-primary-500"
            />
          </div>
        </div>

        {/* List */}
        <div className="flex-1 overflow-y-auto">
          {filteredRooms.map(room => (
            <button
              key={room.id}
              onClick={() => setActiveRoom(room)}
              className={clsx(
                'w-full flex items-start gap-3 px-4 py-3 hover:bg-gray-50 transition-colors text-left border-b border-gray-50',
                activeRoom.id === room.id && 'bg-primary-50'
              )}
            >
              <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary-100 to-indigo-200 flex items-center justify-center flex-shrink-0">
                <span className="text-sm font-semibold text-primary-700">{room.name[0]}</span>
              </div>
              <div className="flex-1 min-w-0">
                <div className="flex items-center justify-between mb-0.5">
                  <span className="text-sm font-semibold text-gray-900 truncate">{room.name}</span>
                  <span className="text-[11px] text-gray-400 flex-shrink-0">{room.time}</span>
                </div>
                <div className="flex items-center justify-between">
                  <p className="text-xs text-gray-500 truncate">{room.lastMessage}</p>
                  {room.unread > 0 && (
                    <span className="ml-2 min-w-[18px] h-[18px] bg-primary-600 text-white text-[10px] font-bold rounded-full flex items-center justify-center px-1 flex-shrink-0">
                      {room.unread}
                    </span>
                  )}
                </div>
              </div>
            </button>
          ))}
        </div>
      </div>

      {/* Chat Area */}
      <div className="flex-1 flex flex-col">
        {/* Chat Header */}
        <div className="px-5 py-3 border-b border-gray-200 flex items-center gap-3">
          <div className="w-9 h-9 rounded-full bg-gradient-to-br from-primary-100 to-indigo-200 flex items-center justify-center">
            <span className="text-sm font-semibold text-primary-700">{activeRoom.name[0]}</span>
          </div>
          <div>
            <h3 className="font-semibold text-gray-900 text-sm">{activeRoom.name}</h3>
            <p className="text-xs text-gray-400">{activeRoom.participants}명 참여</p>
          </div>
          <div className="ml-auto">
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-gray-100 text-gray-500">
              <MoreVertical size={16} />
            </button>
          </div>
        </div>

        {/* Messages */}
        <div className="flex-1 overflow-y-auto px-5 py-4 space-y-4">
          {/* Date separator */}
          <div className="flex items-center gap-3 my-4">
            <div className="flex-1 h-px bg-gray-100" />
            <span className="text-xs text-gray-400">2024년 4월 10일</span>
            <div className="flex-1 h-px bg-gray-100" />
          </div>

          {messages.map(msg => (
            <div key={msg.id} className={clsx('flex items-end gap-2', msg.isMine && 'flex-row-reverse')}>
              {!msg.isMine && (
                <div className="w-7 h-7 rounded-full bg-gray-100 flex items-center justify-center flex-shrink-0">
                  <span className="text-xs font-semibold text-gray-600">{msg.senderInitial}</span>
                </div>
              )}
              <div className={clsx('max-w-xs lg:max-w-md', msg.isMine && 'items-end flex flex-col')}>
                {!msg.isMine && (
                  <p className="text-xs text-gray-500 mb-1 ml-1">{msg.sender}</p>
                )}
                <div className={clsx(
                  'px-4 py-2.5 rounded-2xl text-sm leading-relaxed',
                  msg.isMine
                    ? 'bg-primary-600 text-white rounded-br-sm'
                    : 'bg-gray-100 text-gray-900 rounded-bl-sm'
                )}>
                  {msg.content}
                </div>
                <p className={clsx('text-[11px] text-gray-400 mt-1', msg.isMine ? 'text-right' : 'ml-1')}>
                  {msg.time} {msg.isMine && <span>{msg.read ? '읽음' : '안읽음'}</span>}
                </p>
              </div>
            </div>
          ))}
        </div>

        {/* Input */}
        <div className="px-4 py-3 border-t border-gray-200">
          <div className="flex items-center gap-2 bg-gray-50 rounded-xl px-3 py-2 border border-gray-200 focus-within:border-primary-400 focus-within:ring-2 focus-within:ring-primary-100 transition-all">
            <button className="text-gray-400 hover:text-gray-600">
              <Paperclip size={16} />
            </button>
            <input
              type="text"
              value={input}
              onChange={e => setInput(e.target.value)}
              onKeyDown={e => e.key === 'Enter' && handleSend()}
              placeholder="메시지를 입력하세요..."
              className="flex-1 bg-transparent text-sm focus:outline-none text-gray-700 placeholder-gray-400"
            />
            <button className="text-gray-400 hover:text-gray-600">
              <Smile size={16} />
            </button>
            <button
              onClick={handleSend}
              disabled={!input.trim()}
              className="w-7 h-7 bg-primary-600 rounded-lg flex items-center justify-center hover:bg-primary-700 disabled:opacity-40 transition-colors"
            >
              <Send size={13} className="text-white" />
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}
