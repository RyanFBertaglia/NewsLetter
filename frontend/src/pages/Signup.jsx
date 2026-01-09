import { useState } from 'react';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { googleAuth } from '../services/authService';
import styles from '../styles/home.module.css';
import login from '../styles/auth.module.css';
import { Link } from "react-router-dom";



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

    try {
        console.log(idToken, name);
        const tokenJwt = await googleAuth(name, idToken);
        localStorage.setItem('jwtToken', tokenJwt);
        localStorage.setItem('username', name);
        navigate('/pagamento');
    } catch (e) {
        alert('Falha no registro: ' + (e.response?.data || e.message));
        console.log(e.response?.data);
    } finally {
        setIsLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <nav className={`${styles.nav} navMobile`}>
        <Link to="/">
          <div className={styles.navLogo} >
            <div className={styles.logoCircle}>
              <svg style={{width: '16px', height: '16px', color: 'white'}} fill="currentColor" viewBox="0 0 20 20">
                <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
                <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
              </svg>
            </div>
          </div>
        </Link>  
          <div className={`${styles.navCenter} navCenterMobile`}>
            <a href="#" className={`${styles.navLink} navLink`}>Produtos</a>
            <a href="#" className={`${styles.navLink} navLink`}>Preços</a>
            <a href="#" className={`${styles.navLink} navLink`}>Contact</a>
          </div>
          
        </nav>
      <div className={login.formLogin}>
    <form className={login.formContent}>
      <div className={login.inputGroup}>
        <label htmlFor="name">Nome Completo</label>
        <input
          id="name"
          value={name}
          onChange={e => setName(e.target.value)}
          onBlur={validateName}
          disabled={isLoading}
          placeholder="Digite seu nome completo"
        />
        {nameError && <p className={login.error}>{nameError}</p>}
      </div>

      <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
        <GoogleLogin
          onSuccess={({ credential }) => handleGoogleAuth(credential)}
          onError={() => alert('Falha na autenticação com Google')}
          useOneTap
          text="signup_with"
          size="large"
          width="100%"
          locale="pt_BR"
          disabled={isLoading || !name.trim()}
        />
      </GoogleOAuthProvider>

      {isLoading && (
        <div className={login.loading}>
          <div className={login.loader}></div>
          <span>Processando...</span>
        </div>
      )}

    <p className={login.terms}>
      Ao se cadastrar, você concorda com nossos <a href="#">Termos de Serviço</a> e <a href="#">Política de Privacidade</a>
    </p>
  </form>
</div>


      <style jsx>{`
        .loader { animation: spin 1s linear infinite; }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
      `}</style>
    </div>
  );
}