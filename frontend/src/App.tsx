import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Layout from './components/Layout'
import Login from './pages/Login'
import SignUp from './pages/SignUp'
import Dashboard from './pages/Dashboard'
import Products from './pages/Products'
import Companies from './pages/Companies'
import Orders from './pages/Orders'
import Profile from './pages/Profile'
import Chat from './pages/Chat'
import Notifications from './pages/Notifications'
import Inquiries from './pages/Inquiries'
import Friends from './pages/Friends'
import CompanyRequests from './pages/CompanyRequests'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Navigate to="/dashboard" replace />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="products" element={<Products />} />
          <Route path="companies" element={<Companies />} />
          <Route path="orders" element={<Orders />} />
          <Route path="profile" element={<Profile />} />
          <Route path="chat" element={<Chat />} />
          <Route path="notifications" element={<Notifications />} />
          <Route path="inquiries" element={<Inquiries />} />
          <Route path="friends" element={<Friends />} />
          <Route path="company-requests" element={<CompanyRequests />} />
        </Route>
        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </BrowserRouter>
  )
}
