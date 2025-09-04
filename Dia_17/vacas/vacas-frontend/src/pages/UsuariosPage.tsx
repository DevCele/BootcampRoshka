import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

interface UsuarioItem {
  id: number;
  nombre: string;
  apellido: string;
  correo: string;
  rolId?: number;
  equipoId?: number;
  cargoId?: number;
  estado?: boolean;
  antiguedad?: string;
  antiguedadPretty?: string;
}

interface CatalogItem {
  id: number;
  nombre: string;
}

export default function UsuariosPage() {
  const { token, user } = useAuth();
  const navigate = useNavigate();

  const [usuarios, setUsuarios] = useState<UsuarioItem[]>([]);
  const [roles, setRoles] = useState<CatalogItem[]>([]);
  const [equipos, setEquipos] = useState<CatalogItem[]>([]);
  const [cargos, setCargos] = useState<CatalogItem[]>([]);

  const [rolId, setRolId] = useState<string>("");
  const [equipoId, setEquipoId] = useState<string>("");
  const [cargoId, setCargoId] = useState<string>("");
  const [page, setPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(1);

  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const puedeVer = user?.rol?.nombre === "TH" || user?.rol?.nombre === "GTH";

  useEffect(() => {
    if (!token || !puedeVer) return;

    Promise.all([
      fetch("/api/catalogos/roles").then((r) => r.json()),
      fetch("/api/catalogos/cargos").then((r) => r.json()),
      fetch("/api/catalogos/equipos").then((r) => r.json()),
    ]).then(([r, c, e]) => {
      setRoles(r);
      setCargos(c);
      setEquipos(e);
    });
  }, [token, puedeVer]);

  useEffect(() => {
    if (!token || !puedeVer) return;

    setLoading(true);
    setUsuarios([]);

    const params = new URLSearchParams();
    if (rolId) params.append("rolId", rolId);
    if (equipoId) params.append("equipoId", equipoId);
    if (cargoId) params.append("cargoId", cargoId);
    params.append("page", page.toString());
    params.append("size", "10");

    fetch(`/api/usuarios?${params.toString()}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(async (res) => {
        if (!res.ok) throw new Error(await res.text());
        return res.json();
      })
      .then((data) => {
        setUsuarios(data.content);
        setTotalPages(data.totalPages);
      })
      .catch((err) => {
        setError("Error al cargar usuarios: " + err.message);
      })
      .finally(() => setLoading(false));
  }, [token, puedeVer, rolId, equipoId, cargoId, page]);

  const limpiarFiltros = () => {
    setRolId("");
    setEquipoId("");
    setCargoId("");
    setUsuarios([]);
    setPage(0);
  };

  if (!puedeVer) return <p>No tenés permisos para ver esta página.</p>;
  if (loading) return <p>Cargando usuarios...</p>;

  return (
    <div style={{ padding: 24 }}>
      <h2>Listado de usuarios</h2>

      {error && <p style={{ color: "crimson" }}>{error}</p>}

      <div style={{ marginBottom: 16 }}>
        <button onClick={() => navigate("/usuarios/nuevo")}>
          ➕ Crear Usuario
        </button>
      </div>

      <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
        <select value={rolId} onChange={(e) => setRolId(e.target.value)}>
          <option value="">Filtrar por Rol</option>
          {roles.map((r) => (
            <option key={r.id} value={r.id}>{r.nombre}</option>
          ))}
        </select>

        <select value={equipoId} onChange={(e) => setEquipoId(e.target.value)}>
          <option value="">Filtrar por Equipo</option>
          {equipos.map((e) => (
            <option key={e.id} value={e.id}>{e.nombre}</option>
          ))}
        </select>

        <select value={cargoId} onChange={(e) => setCargoId(e.target.value)}>
          <option value="">Filtrar por Cargo</option>
          {cargos.map((c) => (
            <option key={c.id} value={c.id}>{c.nombre}</option>
          ))}
        </select>

        <button onClick={limpiarFiltros}>Limpiar filtros</button>
      </div>

      <table border={1} cellPadding={8} cellSpacing={0} style={{ width: "100%", marginTop: 16 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Antigüedad</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.nombre} {u.apellido}</td>
              <td>{u.correo}</td>
              <td>{u.antiguedadPretty ?? "-"}</td>
              <td>{u.estado === true ? "Activo" : "Inactivo"}</td>
              <td>
                <button onClick={() => navigate(`/usuarios/${u.id}`)}>Editar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div style={{ marginTop: 16 }}>
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            onClick={() => setPage(i)}
            disabled={i === page}
            style={{ marginRight: 4 }}
          >
            {i + 1}
          </button>
        ))}
        <button
            type="button"
            onClick={() => navigate(-1)}
            style={{ background: "lightgray" }}
            >
            Cancelar
        </button>
      </div>
    </div>
  );
}
