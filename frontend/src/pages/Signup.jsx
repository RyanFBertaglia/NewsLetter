// src/pages/Signup.jsx
import { useState } from 'react';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';

export default function Signup() {
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [nameError, setNameError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  // Função para registrar/login via sua API Java
  const handleGoogleAuth = async (idToken) => {
    setIsLoading(true);
    try {
      // Primeiro tenta registrar
      const registerResponse = await api.post('/auth/google-register', {
        idToken: idToken,
        nome: name
      });

      if (registerResponse.status === 200) {
        localStorage.setItem('jwtToken', registerResponse.data);
        navigate('/pagamento');
        return;
      }
    } catch (registerError) {
      // Se erro 409 (usuário já existe), tenta fazer login
      if (registerError.response?.status === 409) {
        try {
          const loginResponse = await api.post('/auth/google-login', {
            idToken: idToken
          });
          
          if (loginResponse.status === 200) {
            localStorage.setItem('jwtToken', loginResponse.data);
            navigate('/pagamento');
            return;
          }
        } catch (loginError) {
          console.error('Erro no login:', loginError);
          alert('Falha no login: ' + (loginError.response?.data || loginError.message));
        }
      } else {
        console.error('Erro no registro:', registerError);
        alert('Falha no registro: ' + (registerError.response?.data || registerError.message));
      }
    } finally {
      setIsLoading(false);
    }
  };

  const validateName = () => {
    if (!name.trim()) {
      setNameError('Por favor, digite seu nome completo');
      return false;
    }
    if (name.length < 3) {
      setNameError('O nome deve ter pelo menos 3 caracteres');
      return false;
    }
    setNameError('');
    return true;
  };

  const handleSubmit = async (credentialResponse) => {
    if (!validateName()) return;
    
    try {
      await handleGoogleAuth(credentialResponse.credential);
    } catch (error) {
      console.error('Erro no processo de autenticação:', error);
      alert('Ocorreu um erro durante a autenticação');
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-4 bg-gray-50">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h1 className="text-2xl font-bold mb-6 text-center">Crie sua conta</h1>
        
        <div className="mb-6">
          <label htmlFor="name" className="block text-gray-700 font-medium mb-2">
            Nome Completo
          </label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            onBlur={validateName}
            className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 ${
              nameError ? 'border-red-500 focus:ring-red-200' : 'border-gray-300 focus:ring-blue-200'
            }`}
            placeholder="Digite seu nome completo"
            disabled={isLoading}
          />
          {nameError && <p className="mt-1 text-red-500 text-sm">{nameError}</p>}
        </div>

        <div className="flex flex-col items-center">
          <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
            <GoogleLogin
              onSuccess={handleSubmit}
              onError={() => alert('Falha na autenticação com Google')}
              useOneTap
              text="signup_with"
              size="large"
              width="350"
              disabled={isLoading || !name.trim()}
              locale="pt_BR"
            />
          </GoogleOAuthProvider>
          
          {isLoading && (
            <div className="mt-4 flex items-center">
              <div className="loader ease-linear rounded-full border-t-2 border-b-2 border-blue-500 h-5 w-5 animate-spin"></div>
              <span className="ml-2 text-gray-600">Processando...</span>
            </div>
          )}
          
          <p className="mt-4 text-sm text-gray-600 text-center">
            Ao se cadastrar, você concorda com nossos Termos de Serviço e Política de Privacidade
          </p>
        </div>
      </div>
      
      <style jsx>{`
        .loader {
          animation: spin 1s linear infinite;
        }
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
      `}</style>
    </div>
  );
}