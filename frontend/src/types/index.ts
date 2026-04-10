export interface User {
  userNo: number;
  name: string;
  email: string;
  phoneNumber: string;
  address: string;
  imageUrl?: string;
  isOpen: boolean;
  role: { roleNo: number; name: string };
  position: { positionNo: number; name: string };
  userStatus: { userStatusNo: number; name: string };
  company?: Company;
}

export interface Company {
  companyNo: number;
  name: string;
  phoneNumber: string;
  address: string;
  isDeleted: boolean;
  affiliation?: { affiliationNo: number; name: string };
  branch?: { branchNo: number; name: string };
}

export interface Product {
  productNo: number;
  name: string;
  content: string;
  price: number;
  imageUrl?: string;
  isDeleted: boolean;
  unit: { unitNo: number; name: string };
  category: { categoryNo: number; name: string };
  manufacturer: { manufacturerNo: number; name: string };
  productStatus: { productStatusNo: number; name: string };
}

export interface Order {
  orderNo: number;
  status: 'ACTIVE' | 'ANSWERED' | 'CLOSED';
  user: Pick<User, 'userNo' | 'name' | 'email'>;
  company: Pick<Company, 'companyNo' | 'name'>;
  orderItems: OrderItem[];
  createdAt: string;
}

export interface OrderItem {
  orderItemNo: number;
  quantity: number;
  product: Pick<Product, 'productNo' | 'name' | 'price'>;
}

export interface Alarm {
  alarmNo: number;
  title: string;
  content: string;
  alarmType: 'COMPANY_REGISTER_REQUEST' | 'COMPANY_CONNECT_REQUEST' | 'FRIEND_REQUEST' | 'ORDER_REQUEST' | 'GENERAL';
  referenceNo: number;
  isRead: boolean;
  createdAt: string;
}

export interface FriendRequest {
  friendRequestNo: number;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  sender: Pick<User, 'userNo' | 'name' | 'email' | 'imageUrl'>;
  receiver: Pick<User, 'userNo' | 'name' | 'email' | 'imageUrl'>;
  createdAt: string;
}

export interface Message {
  messageNo: number;
  content: string;
  createdAt: string;
  user: Pick<User, 'userNo' | 'name' | 'imageUrl'>;
}

export interface Chatroom {
  chatroomNo: number;
  name: string;
  status: string;
  createdAt: string;
  lastMessage?: Message;
  unreadCount: number;
}

export interface Inquiry {
  inquiryNo: number;
  title: string;
  content: string;
  status: 'PENDING' | 'ANSWERED' | 'CLOSED';
  createdAt: string;
  user: Pick<User, 'userNo' | 'name'>;
  company: Pick<Company, 'companyNo' | 'name'>;
  inquiryCategory: { inquiryCategoryNo: number; name: string };
}

export interface CompanyJoinRequest {
  companyRequestNo: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  user: Pick<User, 'userNo' | 'name' | 'email'>;
  company: Pick<Company, 'companyNo' | 'name'>;
  createdAt: string;
}
