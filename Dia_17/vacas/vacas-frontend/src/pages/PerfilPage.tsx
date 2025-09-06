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
    <div className="relative min-h-screen flex items-center justify-center overflow-hidden">
      {/* Fondo azul */}
      <div
        className="absolute inset-0 bg-brand-blue"
        style={{
          backgroundImage: "url('/ilustracion-herov3.svg')",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      >
        <div className="absolute inset-0 bg-brand-blue/40"></div>
      </div>

      {/* Card */}
      <div className="relative z-10 w-full max-w-3xl bg-white/95 backdrop-blur-sm rounded-3xl shadow-2xl p-10">
        <h2 className="text-2xl font-bold text-brand-blue mb-6">
          {isNew
            ? "Crear nuevo usuario"
            : isOwnProfile
            ? "Mi perfil"
            : `Editar perfil de usuario #${perfil.id}`}
        </h2>

        {mensaje && <p className="text-green-600 mb-4">{mensaje}</p>}
        {error && <p className="text-red-600 mb-4">{error}</p>}

        <form onSubmit={onSubmit} className="grid gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">Nombre</label>
            <input
              name="nombre"
              value={perfil.nombre}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Apellido</label>
            <input
              name="apellido"
              value={perfil.apellido}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Número de cédula</label>
            <input
              name="nroCedula"
              type="number"
              value={perfil.nroCedula ?? ""}
              onChange={onChange}
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Correo electrónico</label>
            <input
              name="correo"
              type="email"
              value={perfil.correo}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Teléfono</label>
            <input
              name="telefono"
              value={perfil.telefono ?? ""}
              onChange={onChange}
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Fecha de ingreso</label>
            <input
              name="fechaIngreso"
              type="date"
              value={perfil.fechaIngreso ?? ""}
              onChange={onChange}
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Fecha de nacimiento</label>
            <input
              name="fechaNacimiento"
              type="date"
              value={perfil.fechaNacimiento ?? ""}
              onChange={onChange}
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          {!isNew && (
            <>
              <div>
                <label className="block text-sm font-medium mb-1">Días de vacaciones</label>
                <input
                  name="diasVacaciones"
                  type="number"
                  value={perfil.diasVacaciones ?? ""}
                  onChange={onChange}
                  className="w-full border rounded-lg px-3 py-2"
                />
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">Días de vacaciones restantes</label>
                <input
                  name="diasVacacionesRestante"
                  type="number"
                  value={perfil.diasVacacionesRestante ?? ""}
                  onChange={onChange}
                  className="w-full border rounded-lg px-3 py-2"
                />
              </div>
            </>
          )}

          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              name="estado"
              checked={perfil.estado ?? false}
              onChange={onChange}
            />
            Activo
          </label>

          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              name="requiereCambioContrasena"
              checked={perfil.requiereCambioContrasena ?? false}
              onChange={onChange}
            />
            Requiere cambio de contraseña
          </label>

          <div>
            <label className="block text-sm font-medium mb-1">Rol</label>
            <select
              name="rolId"
              value={perfil.rolId}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            >
              <option value="">Selecciona un Rol</option>
              {roles.map((r) => (
                <option key={r.id} value={r.id}>
                  {r.nombre}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Cargo</label>
            <select
              name="cargoId"
              value={perfil.cargoId}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            >
              <option value="">Selecciona un Cargo</option>
              {cargos.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.nombre}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Equipo</label>
            <select
              name="equipoId"
              value={perfil.equipoId}
              onChange={onChange}
              required
              className="w-full border rounded-lg px-3 py-2"
            >
              <option value="">Selecciona un Equipo</option>
              {equipos.map((e) => (
                <option key={e.id} value={e.id}>
                  {e.nombre}
                </option>
              ))}
            </select>
          </div>

          <div className="flex gap-4 mt-4">
            <button
              type="submit"
              onClick={() => navigate(-1)}
              className="flex-1 bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition"
            >
              {isNew ? "Crear usuario" : "Guardar cambios"}
            </button>
            <button
              type="button"
              onClick={() => navigate(-1)}
              className="flex-1 bg-gray-300 py-3 rounded-lg font-semibold hover:bg-gray-400 transition"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}