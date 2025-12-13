import { api } from './api';
import { jwtDecode } from 'jwt-decode';


export async function googleAuth(name, idToken) {
  const trimmed = name.trim();
  if (!trimmed || trimmed.split(/\s+/).length < 2) {
    throw new Error('Nome inválido. Digite seu nome completo.');
  }

  try {
    const res = await api.post('/auth/google-register', {
      nome: name,
      idToken: idToken
    });
    if (res.status === 200) {
      return res.data;
    }
  } catch (err) {
    if (err.response?.status === 409) {
      const loginRes = await login(idToken);
      return loginRes;
    }
    throw err;
  }
}

export async function login(idToken) {
  const res = await api.post('/auth/google-login', { idToken: idToken });
  if (res.status !== 200) throw new Error("Não foi possível fazer login");
  const token = res.data;
  return token;
}

export function getUserInfo() {

  try {
    const decoded = jwtDecode(localStorage.getItem('jwtToken'));
    const username = localStorage.getItem('username');

    return {
      id: decoded.userId || decoded.sub,
      email: decoded.email,
      name: username,
    };
  } catch (error) {
    console.error('Erro ao decodificar token:', error);
    return null;
  }
}

export function logout() {
  localStorage.removeItem('jwtToken');
  delete api.defaults.headers.common['Authorization'];
}