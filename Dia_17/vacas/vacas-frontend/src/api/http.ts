import axios from "axios";
import type { InternalAxiosRequestConfig } from "axios";

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE,
});

// Adjunta el token a TODAS las requests (excepto login)
api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = localStorage.getItem("auth_token");
  const isLogin = config.url?.includes("/api/auth/login");

  if (token && !isLogin) {
    const h = config.headers as any;
    if (h?.set && typeof h.set === "function") {
      h.set("Authorization", `Bearer ${token}`);
    } else {
      config.headers = { ...(config.headers as any), Authorization: `Bearer ${token}` };
    }
  }
  return config;
});

// Si el backend responde 401, limpiamos y redirigimos a /login
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err?.response?.status === 401) {
      localStorage.removeItem("auth_token");
      window.location.href = "/login";
    }
    return Promise.reject(err);
  }
);

export default api;