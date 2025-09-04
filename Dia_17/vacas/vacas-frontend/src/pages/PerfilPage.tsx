import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate, useParams } from "react-router-dom";

interface CatalogItem {
  id: number;
  nombre: string;
}

interface PerfilForm {
  id?: number;
  nombre: string;
  apellido: string;
  nroCedula?: number;
  correo: string;
  telefono?: string;
  equipoId?: string;
  rolId?: string;
  cargoId?: string;
  fechaIngreso?: string;
  fechaNacimiento?: string;
  diasVacaciones?: number;
  diasVacacionesRestante?: number;
  estado?: boolean;
  requiereCambioContrasena?: boolean;
}

export default function PerfilPage() {
  const { token, user } = useAuth();
  const navigate = useNavigate();
  const { id } = useParams();

  const puedeEditar = user?.rol?.nombre === "TH" || user?.rol?.nombre === "GTH";
  const isNew = id === "nuevo";
  const isOwnProfile = !id; // /perfil → sin id

  const [perfil, setPerfil] = useState<PerfilForm | null>(null);
  const [roles, setRoles] = useState<CatalogItem[]>([]);
  const [cargos, setCargos] = useState<CatalogItem[]>([]);
  const [equipos, setEquipos] = useState<CatalogItem[]>([]);
  const [mensaje, setMensaje] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!token) return;
    if (!isOwnProfile && !isNew && !puedeEditar) return;

    async function cargarDatos() {
      try {
        const [rolesRes, cargosRes, equiposRes] = await Promise.all([
          fetch("/api/catalogos/roles"),
          fetch("/api/catalogos/cargos"),
          fetch("/api/catalogos/equipos"),
        ]);
        setRoles(await rolesRes.json());
        setCargos(await cargosRes.json());
        setEquipos(await equiposRes.json());

        if (isNew) {
          setPerfil({
            nombre: "",
            apellido: "",
            correo: "",
            nroCedula: undefined,
            telefono: "",
            rolId: "",
            cargoId: "",
            equipoId: "",
            fechaIngreso: "",
            fechaNacimiento: "",
            estado: true,
            requiereCambioContrasena: true,
          });
          return;
        }

        const perfilRes = await fetch(
          isOwnProfile ? "/api/usuarios/me" : `/api/usuarios/${id}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        if (!perfilRes.ok) throw new Error("No se pudo cargar el perfil.");
        const perfilData = await perfilRes.json();

        setPerfil({
          ...perfilData,
          rolId: perfilData.rolId?.toString() ?? "",
          equipoId: perfilData.equipoId?.toString() ?? "",
          cargoId: perfilData.cargoId?.toString() ?? "",
        });
      } catch (e: any) {
        setError(e.message || "Error al cargar los datos");
      }
    }

    cargarDatos();
  }, [token, puedeEditar, id, isOwnProfile, isNew]);

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, type, value, checked } = e.target as HTMLInputElement;
    setPerfil((prev) =>
      prev
        ? {
            ...prev,
            [name]:
              type === "checkbox" ? checked : type === "number" ? Number(value) : value,
          }
        : prev
    );
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setMensaje(null);

    if (!perfil || !token) return;

    const payload = {
      ...perfil,
      rolId: perfil.rolId ? Number(perfil.rolId) : null,
      cargoId: perfil.cargoId ? Number(perfil.cargoId) : null,
      equipoId: perfil.equipoId ? Number(perfil.equipoId) : null,
    };

    try {
      let res: Response;

      if (isNew) {
        res = await fetch("/api/usuarios", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        });
      } else {
        res = await fetch(`/api/usuarios/${perfil.id}`, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        });
      }

      if (!res.ok) throw new Error(await res.text());
      setMensaje(isNew ? "Usuario creado correctamente ✅" : "Perfil actualizado ✅");

      if (isNew) navigate("/usuarios");
    } catch (err: any) {
      setError(err.message || "Error al guardar");
    }
  };

  if (!token || (!isOwnProfile && !isNew && !puedeEditar)) {
    return <p>No tenés permisos para ver esta página.</p>;
  }

  if (!perfil) return <p>Cargando perfil...</p>;

  return (
    <div className="container" style={{ maxWidth: 600, margin: "40px auto" }}>
      <h2>
        {isNew
          ? "Crear nuevo usuario"
          : isOwnProfile
          ? "Mi perfil"
          : `Editar perfil de usuario #${perfil.id}`}
      </h2>

      {mensaje && <p style={{ color: "green" }}>{mensaje}</p>}
      {error && <p style={{ color: "crimson" }}>{error}</p>}

      <form onSubmit={onSubmit} style={{ display: "grid", gap: "1rem" }}>
        <label>
          Nombre
          <input name="nombre" value={perfil.nombre} onChange={onChange} required />
        </label>

        <label>
          Apellido
          <input name="apellido" value={perfil.apellido} onChange={onChange} required />
        </label>

        <label>
          Número de cédula
          <input
            name="nroCedula"
            type="number"
            value={perfil.nroCedula ?? ""}
            onChange={onChange}
          />
        </label>

        <label>
          Correo electrónico
          <input
            name="correo"
            type="email"
            value={perfil.correo}
            onChange={onChange}
            required
          />
        </label>

        <label>
          Teléfono
          <input
            name="telefono"
            value={perfil.telefono ?? ""}
            onChange={onChange}
          />
        </label>

        <label>
          Fecha de ingreso
          <input
            name="fechaIngreso"
            type="date"
            value={perfil.fechaIngreso ?? ""}
            onChange={onChange}
          />
        </label>

        <label>
          Fecha de nacimiento
          <input
            name="fechaNacimiento"
            type="date"
            value={perfil.fechaNacimiento ?? ""}
            onChange={onChange}
          />
        </label>

        {/* Solo en edición/ver, no en creación */}
        {!isNew && (
          <>
            <label>
              Días de vacaciones
              <input
                name="diasVacaciones"
                type="number"
                value={perfil.diasVacaciones ?? ""}
                onChange={onChange}
              />
            </label>

            <label>
              Días de vacaciones restantes
              <input
                name="diasVacacionesRestante"
                type="number"
                value={perfil.diasVacacionesRestante ?? ""}
                onChange={onChange}
              />
            </label>
          </>
        )}

        <label>
          <input
            type="checkbox"
            name="estado"
            checked={perfil.estado ?? false}
            onChange={onChange}
          />
          Activo
        </label>

        <label>
          <input
            type="checkbox"
            name="requiereCambioContrasena"
            checked={perfil.requiereCambioContrasena ?? false}
            onChange={onChange}
          />
          Requiere cambio de contraseña
        </label>

        <label>
          Rol
          <select name="rolId" value={perfil.rolId} onChange={onChange} required>
            <option value="">Selecciona un Rol</option>
            {roles.map((r) => (
              <option key={r.id} value={r.id}>
                {r.nombre}
              </option>
            ))}
          </select>
        </label>

        <label>
          Cargo
          <select name="cargoId" value={perfil.cargoId} onChange={onChange} required>
            <option value="">Selecciona un Cargo</option>
            {cargos.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nombre}
              </option>
            ))}
          </select>
        </label>

        <label>
          Equipo
          <select name="equipoId" value={perfil.equipoId} onChange={onChange} required>
            <option value="">Selecciona un Equipo</option>
            {equipos.map((e) => (
              <option key={e.id} value={e.id}>
                {e.nombre}
              </option>
            ))}
          </select>
        </label>

        <div style={{ display: "flex", gap: "1rem" }}>
          <button type="submit">
            {isNew ? "Crear usuario" : "Guardar cambios"}
          </button>
          <button
            type="button"
            onClick={() => navigate(-1)}
            style={{ background: "lightgray" }}
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}
