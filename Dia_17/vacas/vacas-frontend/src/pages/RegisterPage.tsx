import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

type CatalogItem = {
  id: number;
  nombre: string;
};

type RegisterForm = {
  nombre: string;
  apellido: string;
  correo: string;
  contrasena: string;
  telefono: string;
  nroCedula: string;
  rolId: string;
  cargoId: string;
  equipoId: string;
  fechaIngreso: string;
};

export default function RegisterPage() {
  const [form, setForm] = useState<RegisterForm>({
    nombre: "",
    apellido: "",
    correo: "",
    contrasena: "",
    telefono: "",
    nroCedula: "",
    rolId: "",
    cargoId: "",
    equipoId: "",
    fechaIngreso: new Date().toISOString().split('T')[0]
  });

  const [roles, setRoles] = useState<CatalogItem[]>([]);
  const [cargos, setCargos] = useState<CatalogItem[]>([]);
  const [equipos, setEquipos] = useState<CatalogItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");

  const [successMgs, setSuccessMsg] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    async function load() {
      try {
        const [r, c, e] = await Promise.all([
          fetch("/api/catalogos/roles").then(res => res.json()),
          fetch("/api/catalogos/cargos").then(res => res.json()),
          fetch("/api/catalogos/equipos").then(res => res.json())
        ]);
        setRoles(r);
        setCargos(c);
        setEquipos(e);
      } catch (e) {
        setErr("No se pudieron cargar los catálogos.");
      } finally {
        setLoading(false);
      }
    }

    load();
  }, []);

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setForm(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setErr("");

    const payload = {
      ...form,
      nroCedula: parseInt(form.nroCedula, 10),
      rolId: Number(form.rolId),
      cargoId: Number(form.cargoId),
      equipoId: Number(form.equipoId)
    };

  
    if (!payload.rolId || !payload.cargoId || !payload.equipoId) {
      setErr("Debes seleccionar Rol, Cargo y Equipo.");
      return;
    }

    try {
      const res = await fetch("/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const txt = await res.text();
        throw new Error(txt || "Error en el registro");
      }

      const data = await res.json();
      console.log("Registrado OK", data);
      
      setSuccessMsg("Registro exitoso. Redirigiendo al login...");
      setTimeout(() => {
        navigate("/login", { replace: true });
      }, 1000);
    } catch (e) {
      console.error("Error en el registro:", e);
      const msg = e instanceof Error ? e.message : "Ocurrió un error desconocido";
      setErr(msg);
    }
  };

  if (loading) return <p>Cargando catálogos...</p>;

  return (
    <div className="relative min-h-screen flex items-center justify-center overflow-hidden">
      {/* Fondo azul */}
      <div className="absolute inset-0 bg-brand-blue"></div>

      {/* Card */}
      <div className="relative z-10 w-full max-w-md bg-white/95 backdrop-blur-sm rounded-3xl shadow-2xl p-8">
        <h2 className="text-2xl font-bold text-brand-blue mb-6 text-center">
          Crear cuenta
        </h2>

        {err && <p className="text-red-600 mb-4">{err}</p>}
        {successMgs && <p className="text-green-600 mb-4">{successMgs}</p>}

        <form onSubmit={onSubmit} className="grid gap-4">
          <input
            name="nombre"
            placeholder="Nombre"
            value={form.nombre}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            name="apellido"
            placeholder="Apellido"
            value={form.apellido}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            type="email"
            name="correo"
            placeholder="Correo"
            value={form.correo}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            type="password"
            name="contrasena"
            placeholder="Contraseña (mín 6)"
            value={form.contrasena}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            name="telefono"
            placeholder="Teléfono (opcional)"
            value={form.telefono}
            onChange={onChange}
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            name="nroCedula"
            placeholder="Número de Cédula"
            value={form.nroCedula}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />
          <input
            type="date"
            name="fechaIngreso"
            placeholder="Fecha de Ingreso"
            value={form.fechaIngreso}
            onChange={onChange}
            required
            className="w-full border rounded-lg px-3 py-2"
          />

          <select
            name="rolId"
            value={form.rolId}
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

          <select
            name="cargoId"
            value={form.cargoId}
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

          <select
            name="equipoId"
            value={form.equipoId}
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

          <button
            type="submit"
            className="w-full bg-gradient-to-r from-yellow-400 to-yellow-500 text-blue-900 font-bold py-3 rounded-full hover:from-yellow-500 hover:to-yellow-600 transition-all duration-200 shadow-md"
          >
            Crear cuenta
          </button>
        </form>
      </div>
    </div>
  );
}