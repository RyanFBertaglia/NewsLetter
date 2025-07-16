import { api } from './api';


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
      localStorage.setItem('jwtToken', res.data);
      return res.data;
    }
  } catch (err) {
    if (err.response?.status === 409) {
      const loginRes = await login(idToken);
      localStorage.setItem('jwtToken', loginRes.data);
      return loginRes;
    }
    throw err;
  }
}

export async function login(idToken) {
  const res = await api.post('/auth/google-login', { idToken: idToken });
  if (res.status !== 200) throw new Error("Não foi possível fazer login");
  return res.data;
}
