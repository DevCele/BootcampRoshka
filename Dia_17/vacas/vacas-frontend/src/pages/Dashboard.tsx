import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Dashboard() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [activeSection, setActiveSection] = useState('inicio');

  const isThOrGth = user?.rol?.nombre === "TH" || user?.rol?.nombre === "GTH";

  const isFuncionario = [
    "FUNCIONARIO_FABRICA",
    "FUNCIONARIO_TERCERIZADO",
    "LIDER",
    "OPERACIONES",
    "DIRECTORIO",
  ].includes(user?.rol?.nombre || "");

  // Opciones del menú basadas en el rol
  const menuOptions = [
    { id: 'inicio', label: 'Inicio', icon: '🏠', available: true },
    { id: 'perfil', label: 'Mi Perfil', icon: '👤', available: true },
    { id: 'usuarios', label: 'Gestión de Usuarios', icon: '👥', available: isThOrGth },
    { id: 'reportes', label: 'Reportes', icon: '📊', available: isThOrGth },
    { id: 'vacaciones', label: 'Vacaciones', icon: '🏖️', available: isFuncionario },
    { id: 'configuracion', label: 'Configuración', icon: '⚙️', available: true },
  ].filter(option => option.available);

  const renderContent = () => {
    switch(activeSection) {
      case 'inicio':
        return (
          <div className="space-y-6">
            <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white p-8 rounded-2xl">
              <h2 className="text-3xl font-bold mb-2">¡Bienvenido de nuevo! 👋</h2>
              <p className="text-blue-100 text-lg">
                {user?.nombre} {user?.apellido}
              </p>
              <p className="text-blue-200 text-sm mt-1">{user?.correo}</p>
            </div>

            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
              {/* Acceso rápido para TH/GTH */}
              {isThOrGth && (
                <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-blue-500 hover:shadow-lg transition-shadow">
                  <div className="flex items-center mb-3">
                    <span className="text-2xl mr-3">👥</span>
                    <h3 className="text-lg font-semibold text-gray-800">Gestión de Usuarios</h3>
                  </div>
                  <p className="text-gray-600 text-sm mb-4">Administra perfiles y usuarios del sistema</p>
                  <button
                    onClick={() => navigate("/usuarios")}
                    className="w-full bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition-colors"
                  >
                    Ir a usuarios
                  </button>
                </div>
              )}

              {/* Acceso rápido para Funcionarios */}
              {isFuncionario && (
                <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-green-500 hover:shadow-lg transition-shadow">
                  <div className="flex items-center mb-3">
                    <span className="text-2xl mr-3">🏖️</span>
                    <h3 className="text-lg font-semibold text-gray-800">Mis Vacaciones</h3>
                  </div>
                  <p className="text-gray-600 text-sm mb-2">
                    Disponibles: <span className="font-semibold text-green-600">{user?.dias_vacaciones_restante ?? 0} días</span>
                  </p>
                  <p className="text-gray-600 text-sm mb-4">
                    Total asignados: {user?.dias_vacaciones ?? 0} días
                  </p>
                  <button
                    onClick={() => setActiveSection('vacaciones')}
                    className="w-full bg-green-500 text-white py-2 px-4 rounded-lg hover:bg-green-600 transition-colors"
                  >
                    Ver detalles
                  </button>
                </div>
              )}

              {/* Tarjeta de perfil */}
              <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-purple-500 hover:shadow-lg transition-shadow">
                <div className="flex items-center mb-3">
                  <span className="text-2xl mr-3">👤</span>
                  <h3 className="text-lg font-semibold text-gray-800">Mi Perfil</h3>
                </div>
                <p className="text-gray-600 text-sm mb-4">
                  Rol: <span className="font-semibold">{user?.rol?.nombre}</span>
                </p>
                <button
                  onClick={() => setActiveSection('perfil')}
                  className="w-full bg-purple-500 text-white py-2 px-4 rounded-lg hover:bg-purple-600 transition-colors"
                >
                  Ver perfil
                </button>
              </div>
            </div>
          </div>
        );

      case 'perfil':
        return (
          <div className="space-y-6">
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                <span className="mr-3">👤</span>
                Información Personal
              </h2>
              
              <div className="grid md:grid-cols-2 gap-6">
                <div className="space-y-4">
                  <div className="p-4 bg-gray-50 rounded-lg">
                    <label className="text-sm font-medium text-gray-600">Nombre completo</label>
                    <p className="text-lg font-semibold text-gray-800">{user?.nombre} {user?.apellido}</p>
                  </div>
                  
                  <div className="p-4 bg-gray-50 rounded-lg">
                    <label className="text-sm font-medium text-gray-600">Correo electrónico</label>
                    <p className="text-lg font-semibold text-gray-800">{user?.correo}</p>
                  </div>
                  
                  <div className="p-4 bg-gray-50 rounded-lg">
                    <label className="text-sm font-medium text-gray-600">Rol en el sistema</label>
                    <p className="text-lg font-semibold text-gray-800">{user?.rol?.nombre}</p>
                  </div>
                </div>

                {/* Información adicional para funcionarios Y también para TH/GTH */}
                {(isFuncionario || isThOrGth) && (
                  <div className="space-y-4">
                    <div className="p-4 bg-blue-50 rounded-lg border border-blue-200">
                      <label className="text-sm font-medium text-blue-600">Cargo</label>
                      <p className="text-lg font-semibold text-gray-800">{user?.cargo?.nombre ?? "No asignado"}</p>
                    </div>
                    
                    <div className="p-4 bg-blue-50 rounded-lg border border-blue-200">
                      <label className="text-sm font-medium text-blue-600">Equipo</label>
                      <p className="text-lg font-semibold text-gray-800">{user?.equipo?.nombre ?? "No asignado"}</p>
                    </div>
                    
                    <div className="p-4 bg-green-50 rounded-lg border border-green-200">
                      <label className="text-sm font-medium text-green-600">Días de vacaciones</label>
                      <p className="text-lg font-semibold text-gray-800">{user?.dias_vacaciones ?? "No disponible"} días totales</p>
                      <p className="text-sm text-green-600 mt-1">Restantes: {user?.dias_vacaciones_restante ?? "No disponible"} días</p>
                    </div>
                  </div>
                )}
              </div>
            </div>
          </div>
        );

      case 'usuarios':
        return isThOrGth ? (
          <div className="space-y-6">
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                <span className="mr-3">👥</span>
                Gestión de Usuarios
              </h2>
              <p className="text-gray-600 mb-6">Administra los usuarios y sus perfiles en el sistema.</p>
              
              <div className="text-center">
                <button
                  onClick={() => navigate("/usuarios")}
                  className="bg-blue-600 text-white px-8 py-3 rounded-xl font-semibold hover:bg-blue-700 transition-colors shadow-md"
                >
                  Ir a lista de usuarios
                </button>
              </div>
            </div>
          </div>
        ) : null;

      case 'vacaciones':
        return (isFuncionario || isThOrGth) ? (
          <div className="space-y-6">
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                <span className="mr-3">🏖️</span>
                Gestión de Vacaciones
              </h2>
              
              <div className="grid md:grid-cols-2 gap-6">
                <div className="p-6 bg-green-50 rounded-xl border border-green-200">
                  <h3 className="text-lg font-semibold text-green-700 mb-4">Días disponibles</h3>
                  <p className="text-3xl font-bold text-green-600 mb-2">{user?.dias_vacaciones_restante ?? 0}</p>
                  <p className="text-green-600 text-sm">días restantes</p>
                </div>
                
                <div className="p-6 bg-blue-50 rounded-xl border border-blue-200">
                  <h3 className="text-lg font-semibold text-blue-700 mb-4">Días totales asignados</h3>
                  <p className="text-3xl font-bold text-blue-600 mb-2">{user?.dias_vacaciones ?? 0}</p>
                  <p className="text-blue-600 text-sm">días por año</p>
                </div>
              </div>
              
              <div className="mt-6 text-center">
                <button className="bg-green-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-green-700 transition-colors">
                  Solicitar vacaciones
                </button>
              </div>
            </div>
          </div>
        ) : null;

      case 'reportes':
        return isThOrGth ? (
          <div className="space-y-6">
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                <span className="mr-3">📊</span>
                Reportes y Estadísticas
              </h2>
              <p className="text-gray-600 mb-6">Visualiza reportes detallados del sistema.</p>
              
              <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div className="p-4 bg-blue-50 rounded-lg border border-blue-200 text-center">
                  <h4 className="font-semibold text-blue-700">Usuarios Activos</h4>
                  <p className="text-2xl font-bold text-blue-600 mt-2">142</p>
                </div>
                <div className="p-4 bg-green-50 rounded-lg border border-green-200 text-center">
                  <h4 className="font-semibold text-green-700">Perfiles Completos</h4>
                  <p className="text-2xl font-bold text-green-600 mt-2">89%</p>
                </div>
                <div className="p-4 bg-yellow-50 rounded-lg border border-yellow-200 text-center">
                  <h4 className="font-semibold text-yellow-700">Solicitudes Pendientes</h4>
                  <p className="text-2xl font-bold text-yellow-600 mt-2">7</p>
                </div>
              </div>
            </div>
          </div>
        ) : null;

      case 'configuracion':
        return (
          <div className="space-y-6">
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                <span className="mr-3">⚙️</span>
                Configuración
              </h2>
              <p className="text-gray-600 mb-6">Ajusta las preferencias de tu cuenta.</p>
              
              <div className="space-y-4">
                <div className="p-4 bg-gray-50 rounded-lg">
                  <h4 className="font-semibold text-gray-700 mb-2">Notificaciones</h4>
                  <label className="flex items-center space-x-3">
                    <input type="checkbox" className="rounded" defaultChecked />
                    <span className="text-gray-600">Recibir notificaciones por correo</span>
                  </label>
                </div>
                
                <div className="p-4 bg-gray-50 rounded-lg">
                  <h4 className="font-semibold text-gray-700 mb-2">Tema</h4>
                  <select className="w-full p-2 border border-gray-300 rounded-lg">
                    <option>Tema claro</option>
                    <option>Tema oscuro</option>
                    <option>Automático</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        );

      default:
        return (
          <div className="text-center py-12">
            <h2 className="text-xl text-gray-600">Sección en desarrollo</h2>
            <p className="text-gray-500 mt-2">Esta funcionalidad estará disponible próximamente.</p>
          </div>
        );
    }
  };

  if (!user) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-lg text-gray-700">Cargando información del usuario...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Menú lateral */}
      <div className="w-64 bg-white shadow-xl border-r border-gray-200">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center space-x-3">
            <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
              {user.nombre?.[0]}{user.apellido?.[0]}
            </div>
            <div>
              <p className="font-semibold text-gray-800">{user.nombre}</p>
              <p className="text-sm text-gray-600">{user.rol?.nombre}</p>
            </div>
          </div>
        </div>

        <nav className="mt-6">
          {menuOptions.map((option) => (
            <button
              key={option.id}
              onClick={() => setActiveSection(option.id)}
              className={`w-full text-left px-6 py-3 flex items-center space-x-3 hover:bg-gray-50 transition-colors ${
                activeSection === option.id ? 'bg-blue-50 border-r-2 border-blue-500 text-blue-700' : 'text-gray-700'
              }`}
            >
              <span className="text-xl">{option.icon}</span>
              <span className="font-medium">{option.label}</span>
            </button>
          ))}
        </nav>

        <div className="absolute bottom-0 w-64 p-6 border-t border-gray-200">
          <button
            onClick={logout}
            className="w-full flex items-center space-x-3 px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
          >
            <span className="text-xl">🚪</span>
            <span className="font-medium">Cerrar sesión</span>
          </button>
        </div>
      </div>

      {/* Contenido principal */}
      <div className="flex-1 overflow-auto">
        <div className="p-8">
          {renderContent()}
        </div>
      </div>
    </div>
  );
}