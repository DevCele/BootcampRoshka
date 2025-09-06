import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const isThOrGth = user?.rol?.nombre === "TH" || user?.rol?.nombre === "GTH";

  const isFuncionario = [
    "FUNCIONARIO_FABRICA",
    "FUNCIONARIO_TERCERIZADO",
    "LIDER",
    "OPERACIONES",
    "DIRECTORIO",
  ].includes(user?.rol?.nombre || "");

  return (
    <div className="relative min-h-screen flex items-center justify-center overflow-hidden">
      {/* Fondo azul con ilustración */}
      <div
        className="absolute inset-0 bg-brand-blue"
        style={{
          backgroundImage: "url('/ilustracion-herov3.svg')",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      >
        <div className="absolute inset-0 bg-brand-blue/40" />
      </div>

      {/* Contenedor principal */}
      <div className="relative z-10 w-full max-w-4xl bg-white/95 backdrop-blur-sm rounded-3xl shadow-2xl p-10">
        {!user ? (
          <p className="text-center text-lg text-gray-700">
            Cargando información del usuario...
          </p>
        ) : (
          <>
            {/* Bienvenida */}
            <div className="text-center mb-10">
              <h2 className="text-3xl font-bold text-brand-blue mb-2">
                Bienvenido 👋
              </h2>
              <p className="text-lg text-gray-600">
                <strong>{user.nombre} {user.apellido}</strong>
              </p>
              <p className="text-gray-500">{user.correo}</p>
              <p className="mt-2 text-sm text-gray-600">
                Rol: <span className="font-semibold">{user.rol?.nombre}</span>
              </p>
            </div>

            {/* Panel de gestión TH/GTH */}
            {isThOrGth && (
              <div className="mb-8 p-6 bg-blue-50 border border-blue-200 rounded-xl">
                <h3 className="text-xl font-semibold text-blue-700 mb-2">
                  Panel de gestión de usuarios
                </h3>
                <p className="text-gray-700 mb-4">
                  Aquí podrás ver y editar los perfiles incompletos.
                </p>
                <button
                  onClick={() => navigate("/usuarios")}
                  className="px-6 py-3 bg-blue-600 text-white rounded-full font-medium hover:bg-blue-700 transition"
                >
                  Ir a lista de usuarios
                </button>
              </div>
            )}

            {/* Información de funcionario */}
            {isFuncionario && (
              <div className="mb-8 p-6 bg-yellow-50 border border-yellow-200 rounded-xl">
                <h3 className="text-xl font-semibold text-yellow-700 mb-2">
                  Tu perfil
                </h3>
                <p className="text-gray-700">
                  <span className="font-medium">Cargo:</span>{" "}
                  {user.cargo?.nombre ?? "No asignado"}
                </p>
                <p className="text-gray-700">
                  <span className="font-medium">Equipo:</span>{" "}
                  {user.equipo?.nombre ?? "No asignado"}
                </p>
                <p className="text-gray-700">
                  <span className="font-medium">Días de vacaciones:</span>{" "}
                  {user.dias_vacaciones ?? "No disponible"}
                </p>
                <p className="text-gray-700">
                  <span className="font-medium">Vacaciones restantes:</span>{" "}
                  {user.dias_vacaciones_restante ?? "No disponible"}
                </p>
              </div>
            )}

            {/* Botón de logout */}
            <div className="text-center">
              <button
                onClick={logout}
                className="px-6 py-3 bg-red-500 text-white rounded-full font-semibold hover:bg-red-600 transition"
              >
                Cerrar sesión
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
