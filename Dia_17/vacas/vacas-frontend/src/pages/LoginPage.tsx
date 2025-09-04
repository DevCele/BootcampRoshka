import { useState } from "react";
import type { FormEvent } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
  const { login } = useAuth();
  const nav = useNavigate();

  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await login({ correo, contrasena });
      nav("/"); 
    } catch (err: any) {
      setError(err?.response?.data?.message ?? "Credenciales inválidas");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 380, margin: "64px auto" }}>
      <h1>Iniciar sesión</h1>
      <form onSubmit={onSubmit}>
        <div>
          <label>Correo</label>
          <input
            type="email"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
            required
          />
        </div>
        <div style={{ marginTop: 8 }}>
          <label>Contraseña</label>
          <input
            type="password"
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
            required
          />
        </div>

        {error && (
          <p style={{ color: "crimson", marginTop: 8 }}>{error}</p>
        )}

        <button type="submit" disabled={loading} style={{ marginTop: 12 }}>
          {loading ? "Ingresando..." : "Ingresar"}
        </button>
      </form>

      <p style={{ marginTop: 12 }}>
        ¿No tienes cuenta? <Link to="/register">Crear cuenta</Link>
      </p>
    </div>
  );
}