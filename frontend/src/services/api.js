// src/services/api.js
import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080', // Sua URL base aqui
  timeout: 10000,
});

// Interceptor para adicionar token se necessário
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});