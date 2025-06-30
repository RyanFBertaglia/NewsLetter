import { useState } from 'react';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';

export default function Signup() {
  const navigate = useNavigate();
  const [name, setName] = useState(''), [nameError, setNameError] = useState(''), [isLoading, setIsLoading] = useState(false);

  const validateName = () => {
    const trimmed = name.trim();
    if (!trimmed || trimmed.split(/\s+/).length < 2) {
      setNameError('Digite seu nome completo');
      return false;
    }
    setNameError('');
    return true;
  };

  const handleGoogleAuth = async (idToken) => {
    if (!validateName()) return;
    setIsLoading(true);
    const saveTokenAndRedirect = (token) => {
      localStorage.setItem('jwtToken', token);
      navigate('/pagamento');
    };

    try {
      const res = await api.post('/auth/google-register', { idToken, nome: name });
      if (res.status === 200) return saveTokenAndRedirect(res.data);
    } catch (err) {
      if (err.response?.status === 409) {
        try {
          const loginRes = await api.post('/auth/google-login', { idToken });
          if (loginRes.status === 200) return saveTokenAndRedirect(loginRes.data);
        } catch (e) {
          alert('Falha no login: ' + (e.response?.data || e.message));
        }
      } else {
        alert('Falha no registro: ' + (err.response?.data || err.message));
        console.log(err.response?.data);
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4 bg-gray-50">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md text-center">
        <h1 className="text-2xl font-bold mb-6">Crie sua conta</h1>
        <div className="mb-6 text-left">
          <label htmlFor="name" className="block mb-2 text-gray-700 font-medium">Nome Completo</label>
          <input
            id="name" value={name}
            onChange={e => setName(e.target.value)}
            onBlur={validateName}
            disabled={isLoading}
            placeholder="Digite seu nome completo"
            className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 ${
              nameError ? 'border-red-500 focus:ring-red-200' : 'border-gray-300 focus:ring-blue-200'
            }`}
          />
          {nameError && <p className="mt-1 text-red-500 text-sm">{nameError}</p>}
        </div>

        <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
          <GoogleLogin
            onSuccess={({ credential }) => handleGoogleAuth(credential)}
            onError={() => alert('Falha na autenticação com Google')}
            useOneTap text="signup_with" size="large" width="350"
            locale="pt_BR" disabled={isLoading || !name.trim()}
          />
        </GoogleOAuthProvider>

        {isLoading && (
          <div className="mt-4 flex items-center justify-center">
            <div className="loader border-t-2 border-b-2 border-blue-500 h-5 w-5 rounded-full animate-spin"></div>
            <span className="ml-2 text-gray-600">Processando...</span>
          </div>
        )}

        <p className="mt-4 text-sm text-gray-600">
          Ao se cadastrar, você concorda com nossos Termos de Serviço e Política de Privacidade
        </p>
      </div>

      <style jsx>{`
        .loader { animation: spin 1s linear infinite; }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
      `}</style>
    </div>
  );
}
