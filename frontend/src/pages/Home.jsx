import { useState } from 'react';

export default function Home() {
  const [showSuccess, setShowSuccess] = useState(false);

  const handleEntrar = () => {
    setShowSuccess(true);
    setTimeout(() => setShowSuccess(false), 2000);
  };

  const handleVerMais = () => {
    alert('Navegando para mais informações...');
  };

  const styles = {
    container: {
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%)',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif'
    },
    nav: {
      display: 'grid',
      gridTemplateColumns: 'auto 1fr auto',
      alignItems: 'center',
      padding: '1rem 1.5rem',
      background: 'white',
      boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)'
    },
    navLogo: {
      display: 'flex',
      alignItems: 'center'
    },
    logoCircle: {
      width: '32px',
      height: '32px',
      background: '#ef4444',
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    },
    navCenter: {
      display: 'flex',
      justifyContent: 'center',
      gap: '2rem'
    },
    navLink: {
      color: '#6b7280',
      textDecoration: 'none',
      transition: 'color 0.3s ease'
    },
    navActions: {
      display: 'grid',
      gridTemplateColumns: 'auto auto',
      gap: '0.75rem',
      alignItems: 'center'
    },
    btnLogin: {
      background: '#f0fdf4',
      color: '#166534',
      padding: '0.5rem 1rem',
      borderRadius: '0.5rem',
      border: 'none',
      cursor: 'pointer',
      transition: 'background 0.3s ease',
      fontWeight: '500'
    },
    btnRegister: {
      background: '#374151',
      color: 'white',
      padding: '0.5rem 1rem',
      borderRadius: '0.5rem',
      border: 'none',
      cursor: 'pointer',
      transition: 'background 0.3s ease',
      fontWeight: '500'
    },
    mainContent: {
      display: 'grid',
      placeItems: 'center',
      minHeight: 'calc(100vh - 80px)',
      padding: '2rem',
      position: 'relative',
      overflow: 'hidden'
    },
    contentWrapper: {
      display: 'grid',
      gridTemplateRows: 'auto auto auto',
      gap: '3rem',
      textAlign: 'center',
      maxWidth: '32rem',
      zIndex: 10
    },
    titleSection: {
      display: 'grid',
      gap: '1.5rem'
    },
    title: {
      fontSize: '4rem',
      fontWeight: '700',
      color: '#ef4444',
      lineHeight: '1.1',
      margin: 0
    },
    subtitle: {
      fontSize: '1.25rem',
      color: '#6b7280',
      lineHeight: '1.6',
      margin: 0
    },
    buttonActions: {
      display: 'grid',
      gridTemplateColumns: 'auto auto',
      gap: '1rem',
      justifyContent: 'center'
    },
    btnPrimary: {
      background: '#ef4444',
      color: 'white',
      fontWeight: '600',
      padding: '0.75rem 2rem',
      borderRadius: '0.5rem',
      border: 'none',
      cursor: 'pointer',
      transition: 'all 0.3s ease',
      transformOrigin: 'center'
    },
    btnSecondary: {
      background: '#374151',
      color: 'white',
      fontWeight: '600',
      padding: '0.75rem 1.5rem',
      borderRadius: '0.5rem',
      border: 'none',
      cursor: 'pointer',
      transition: 'all 0.3s ease',
      display: 'flex',
      alignItems: 'center',
      gap: '0.5rem',
      transformOrigin: 'center'
    },
    decoration1: {
      position: 'absolute',
      top: '5rem',
      left: '5rem',
      width: '8rem',
      height: '8rem',
      background: '#bbf7d0',
      borderRadius: '50%',
      opacity: 0.3,
      animation: 'pulse 3s ease-in-out infinite'
    },
    decoration2: {
      position: 'absolute',
      bottom: '5rem',
      right: '5rem',
      width: '6rem',
      height: '6rem',
      background: '#fecaca',
      borderRadius: '50%',
      opacity: 0.3,
      animation: 'pulse 3s ease-in-out infinite',
      animationDelay: '1s'
    },
    decoration3: {
      position: 'absolute',
      top: '50%',
      left: '2.5rem',
      width: '4rem',
      height: '4rem',
      background: '#bfdbfe',
      borderRadius: '50%',
      opacity: 0.3,
      animation: 'pulse 3s ease-in-out infinite',
      animationDelay: '2s'
    },
    successMessage: {
      position: 'fixed',
      top: '1rem',
      right: '1rem',
      background: '#10b981',
      color: 'white',
      padding: '0.75rem 1.5rem',
      borderRadius: '0.5rem',
      boxShadow: '0 10px 25px rgba(0, 0, 0, 0.1)',
      zIndex: 50
    }
  };

  return (
    <>
      <style>{`
        @keyframes pulse {
          0%, 100% {
            transform: scale(1);
            opacity: 0.3;
          }
          50% {
            transform: scale(1.1);
            opacity: 0.2;
          }
        }
        
        .btn-primary:hover {
          background: #dc2626 !important;
          transform: scale(1.05);
        }
        
        .btn-secondary:hover {
          background: #111827 !important;
          transform: scale(1.05);
        }
        
        .nav-link:hover {
          color: #111827 !important;
        }
        
        .btn-login:hover {
          background: #dcfce7 !important;
        }
        
        .btn-register:hover {
          background: #111827 !important;
        }

        @media (max-width: 768px) {
          .nav-mobile {
            grid-template-columns: auto 1fr !important;
            gap: 1rem;
          }
          
          .nav-center-mobile {
            display: none !important;
          }
          
          .nav-actions-mobile {
            grid-template-columns: auto !important;
            gap: 0.5rem;
          }
          
          .title-mobile {
            font-size: 3rem !important;
          }
          
          .button-actions-mobile {
            grid-template-columns: 1fr !important;
            gap: 0.75rem;
          }
          
          .btn-full {
            width: 100%;
            justify-content: center;
          }
        }

        @media (max-width: 480px) {
          .title-small {
            font-size: 2.5rem !important;
          }
          
          .subtitle-small {
            font-size: 1.1rem !important;
          }
          
          .decoration-hidden {
            display: none !important;
          }
        }
      `}</style>

      <div style={styles.container}>
        {/* Navigation */}
        <nav style={styles.nav} className="nav-mobile">
          <div style={styles.navLogo}>
            <div style={styles.logoCircle}>
              <svg style={{width: '16px', height: '16px', color: 'white'}} fill="currentColor" viewBox="0 0 20 20">
                <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
                <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
              </svg>
            </div>
          </div>
          
          <div style={styles.navCenter} className="nav-center-mobile">
            <a href="#" style={styles.navLink} className="nav-link">Produtos</a>
            <a href="#" style={styles.navLink} className="nav-link">Preços</a>
            <a href="#" style={styles.navLink} className="nav-link">Contact</a>
          </div>
          
          <div style={styles.navActions} className="nav-actions-mobile">
            <button style={styles.btnLogin} className="btn-login">Login</button>
            <button style={styles.btnRegister} className="btn-register">Registrar</button>
          </div>
        </nav>

        {/* Main Content */}
        <div style={styles.mainContent}>
          <div style={styles.contentWrapper}>
            {/* Title Section */}
            <div style={styles.titleSection}>
              <h1 style={styles.title} className="title-mobile title-small">
                NewsLetter
              </h1>
              
              <p style={styles.subtitle} className="subtitle-small">
                Tenha os acontecimentos mais<br />
                importantes no seu email
              </p>
            </div>
            
            {/* Button Actions */}
            <div style={styles.buttonActions} className="button-actions-mobile">
              <button 
                onClick={handleEntrar}
                style={styles.btnPrimary}
                className="btn-primary btn-full"
              >
                Entrar
              </button>
              
              <button 
                onClick={handleVerMais}
                style={styles.btnSecondary}
                className="btn-secondary btn-full"
              >
                Ver mais
                <svg style={{width: '16px', height: '16px'}} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                </svg>
              </button>
            </div>
          </div>

          {/* Decorative Elements */}
          <div style={styles.decoration1} className="decoration-hidden"></div>
          <div style={styles.decoration2} className="decoration-hidden"></div>
          <div style={styles.decoration3} className="decoration-hidden"></div>

          {/* Success Message */}
          {showSuccess && (
            <div style={styles.successMessage}>
              Redirecionando para cadastro...
            </div>
          )}
        </div>
      </div>
    </>
  );
}