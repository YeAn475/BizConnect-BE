import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

// Layouts
import AdminLayout from './layouts/AdminLayout'
import UserLayout from './layouts/UserLayout'

// Auth pages
import Login from './pages/auth/Login'
import SignUp from './pages/auth/SignUp'

// Admin pages
import AdminDashboard from './pages/admin/Dashboard'
import AdminCompanies from './pages/admin/Companies'
import AdminCompanyRequests from './pages/admin/CompanyRequests'
import AdminUsers from './pages/admin/Users'
import AdminProducts from './pages/admin/Products'
import AdminOrders from './pages/admin/Orders'
import AdminInquiries from './pages/admin/Inquiries'
import AdminNotices from './pages/admin/Notices'

// User pages
import UserDashboard from './pages/user/Dashboard'
import UserProducts from './pages/user/Products'
import UserOrders from './pages/user/Orders'
import UserMyCompany from './pages/user/MyCompany'
import UserFriends from './pages/user/Friends'
import UserChat from './pages/user/Chat'
import UserNotifications from './pages/user/Notifications'
import UserInquiries from './pages/user/Inquiries'
import UserProfile from './pages/user/Profile'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />

        {/* Admin Routes */}
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<Navigate to="/admin/dashboard" replace />} />
          <Route path="dashboard" element={<AdminDashboard />} />
          <Route path="companies" element={<AdminCompanies />} />
          <Route path="company-requests" element={<AdminCompanyRequests />} />
          <Route path="users" element={<AdminUsers />} />
          <Route path="products" element={<AdminProducts />} />
          <Route path="orders" element={<AdminOrders />} />
          <Route path="inquiries" element={<AdminInquiries />} />
          <Route path="notices" element={<AdminNotices />} />
        </Route>

        {/* User Routes */}
        <Route path="/" element={<UserLayout />}>
          <Route index element={<Navigate to="/dashboard" replace />} />
          <Route path="dashboard" element={<UserDashboard />} />
          <Route path="products" element={<UserProducts />} />
          <Route path="orders" element={<UserOrders />} />
          <Route path="my-company" element={<UserMyCompany />} />
          <Route path="friends" element={<UserFriends />} />
          <Route path="chat" element={<UserChat />} />
          <Route path="notifications" element={<UserNotifications />} />
          <Route path="inquiries" element={<UserInquiries />} />
          <Route path="profile" element={<UserProfile />} />
        </Route>

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  )
}
