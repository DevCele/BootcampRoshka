import React, { createContext, useContext, useEffect, useState } from "react";
import { login as loginApi, register as registerApi } from "../services/auth";
import type { AuthRequest, RegisterRequest } from "../services/auth";

export type Usuario = {
  nombre: string;
  apellido: string;
  correo: string;
  telefono?: string;
  fechaIngreso?: string;
  rol?: { nombre: string };
  cargo?: { nombre: string };
  equipo?: { nombre: string };
  dias_vacaciones?: number;
  dias_vacaciones_restante?: number;
};

type AuthContextType = {
  token: string | null;
  user: Usuario | null;
  isAuthenticated: boolean;
  login: (req: AuthRequest) => Promise<void>;
  register: (req: RegisterRequest) => Promise<void>;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<Usuario | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("auth_token");
    if (storedToken) {
      setToken(storedToken);
      decodeAndSetUser(storedToken);
    }
  }, []);

  const decodeAndSetUser = (token: string) => {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      console.log("Payload decodificado:", payload);

      const usuario: Usuario = {
        nombre: payload.nombre ?? "", 
        apellido: payload.apellido ?? "", 
        correo: payload.sub,
        rol: { nombre: payload.rol },
        cargo: payload.cargo ? { nombre: payload.cargo } : undefined,
        equipo: payload.equipo ? { nombre: payload.equipo } : undefined,
        dias_vacaciones: payload.diasVacaciones,
        dias_vacaciones_restante: payload.diasVacacionesRestante,
        telefono: payload.telefono,
        fechaIngreso: payload.fechaIngreso
      };

      setUser(usuario);
    } catch (e) {
      console.error("Error al decodificar el token:", e);
    }
  };

  const login = async (req: AuthRequest) => {
    const tk = await loginApi(req);
    localStorage.setItem("auth_token", tk);
    setToken(tk);
    decodeAndSetUser(tk);
  };

  const register = async (req: RegisterRequest) => {
    const tk = await registerApi(req);
    localStorage.setItem("auth_token", tk);
    setToken(tk);
    decodeAndSetUser(tk);
  };

  const logout = () => {
    localStorage.removeItem("auth_token");
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ token, user, isAuthenticated: !!token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth debe usarse dentro de <AuthProvider>");
  return ctx;
}
