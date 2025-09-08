import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import RegisterPage from "./pages/RegisterPage";
import UsuariosPage from "./pages/UsuariosPage";
import PerfilPage from "./pages/PerfilPage";

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<RegisterPage />} />

      <Route element={<ProtectedRoute />}>
        <Route path="/" element={<Dashboard />} />
        <Route path="/usuarios" element={<UsuariosPage />} />         
        <Route path="/usuarios/:id" element={<PerfilPage />} /> 
      </Route>

      <Route path="*" element={<Login />} />
    </Routes>
  );
}
