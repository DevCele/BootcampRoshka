import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  console.log("Usuario en Dashboard:", user);

  const isThOrGth = user?.rol?.nombre === "TH" || user?.rol?.nombre === "GTH";

const isFuncionario = [
  "FUNCIONARIO_FABRICA",
  "FUNCIONARIO_TERCERIZADO",
  "LIDER",
  "OPERACIONES",
  "DIRECTORIO"
].includes(user?.rol?.nombre || "");

  return (
    <div style={{ padding: 24 }}>
      <h2>Dashboard</h2>

      {!user ? (
        <p>Cargando información del usuario...</p>
      ) : (
        <>
          <p>Bienvenido, <strong>{user.nombre} {user.apellido}</strong> 👋</p>
          <p>Correo: {user.correo}</p>
          <p>Rol: <code>{user.rol?.nombre}</code></p>

          {isThOrGth && (
            <div style={{ marginTop: 24 }}>
              <h3>Panel de gestión de usuarios</h3>
              <p>Aquí podrás ver y editar los perfiles incompletos.</p>
              <button onClick={() => navigate("/usuarios")}>Ir a lista de usuarios</button>
            </div>
          )}

          {isFuncionario && (
            <div style={{ marginTop: 24 }}>
              <h3>Tu perfil</h3>
              <p>Cargo: {user.cargo?.nombre ?? "No asignado"}</p>
              <p>Equipo: {user.equipo?.nombre ?? "No asignado"}</p>
              <p>Dias de vacaciones: {user.dias_vacaciones ?? "No disponible"}</p>
              <p>Días de vacaciones restantes: {user.dias_vacaciones_restante ?? "No disponible"}</p>
            </div>
          )}

          <button onClick={logout} style={{ marginTop: 32 }}>
            Cerrar sesión
          </button>
        </>
      )}
    </div>
  );
}
