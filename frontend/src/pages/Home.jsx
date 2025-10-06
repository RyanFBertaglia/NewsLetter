import { useState } from 'react';
import styles from '../styles/home.module.css';
import { Link, useNavigate } from "react-router-dom";
import Navbar from '../components/Navbar';

export default function Home() {
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();

  const handleEntrar = () => {
    setShowSuccess(true);
    setTimeout(() => {
      setShowSuccess(false);
      navigate("/cadastro");
    }, 2000);
  };

  const handleVerMais = () => {
    alert('Navegando para mais informações...');
  };

  return (
    <>
      <style>{`

        @media (max-width: 480px) {
          .titleSmall {
            font-size: 2.5rem !important;
          }
          
          .subtitleSmall {
            font-size: 1.1rem !important;
          }
          
          .decorationHidden {
            display: none !important;
          }
        }
      `}</style>

      <div className={styles.container}>
          <Navbar />
{/*         <nav className={`${styles.nav} navMobile`}> */}
{/*           <Link to="/"> */}
{/*                     <div className={styles.navLogo} > */}
{/*                       <div className={styles.logoCircle}> */}
{/*                         <svg style={{width: '16px', height: '16px', color: 'white'}} fill="currentColor" viewBox="0 0 20 20"> */}
{/*                           <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/> */}
{/*                           <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/> */}
{/*                         </svg> */}
{/*                       </div> */}
{/*                     </div> */}
{/*           </Link>   */}
{/*            */}
{/*           <div className={`${styles.navCenter} navCenterMobile`}> */}
{/*             <a href="/produto" className={`${styles.navLink} navLink`}>Produtos</a> */}
{/*             <a href="#" className={`${styles.navLink} navLink`}>Preços</a> */}
{/*             <a href="#" className={`${styles.navLink} navLink`}>Contact</a> */}
{/*           </div> */}
{/*            */}
{/*           <div className={`${styles.navActions} navActionsMobile`}> */}
{/*             <Link to="/cadastro" className={`${styles.btnLogin} btnLogin`}>Login</Link> */}
{/*             <Link to="/cadastro" className={`${styles.btnRegister} btnRegister`}>Registrar</Link> */}
{/*           </div> */}
{/*         </nav> */}

        <div className={styles.mainContent}>
          <div className={styles.contentWrapper}>
            <div className={styles.titleSection}>
              <h1 className={`${styles.title} titleMobile titleSmall`}>
                NewsLetter
              </h1>
              
              <p className={`${styles.subtitle} subtitleSmall`}>
                Tenha os acontecimentos mais<br />
                importantes no seu email
              </p>
            </div>
            
            <div className={`${styles.buttonActions} buttonActionsMobile`}>
              <button 
                onClick={handleEntrar}
                className={`${styles.btnPrimary} btnPrimary btnFull`}
              >
                Entrar
              </button>
              
              <button 
                onClick={handleVerMais}
                className={`${styles.btnSecondary} btnSecondary btnFull`}
              >
                Ver mais
                <svg style={{width: '16px', height: '16px'}} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                </svg>
              </button>
            </div>
          </div>

          {showSuccess && (
            <div className={styles.successMessage}>
              Redirecionando para cadastro...
            </div>
          )}
        </div>
      </div>
    </>
  );
}