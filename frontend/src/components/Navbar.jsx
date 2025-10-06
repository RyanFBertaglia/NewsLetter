import React from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "../styles/home.module.css";

export default function Navbar() {
  const navigate = useNavigate();

  // Verifica se o usuário está logado (exemplo: token JWT no localStorage)
  const isLoggedIn = Boolean(localStorage.getItem("token"));

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/"); // redireciona para home
  };

  return (
    <nav className={`${styles.nav} navMobile`}>
      <Link to="/">
        <div className={styles.navLogo}>
          <div className={styles.logoCircle}>
            <svg
              style={{ width: "16px", height: "16px", color: "white" }}
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
              <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
            </svg>
          </div>
        </div>
      </Link>

      <div className={`${styles.navCenter} navCenterMobile`}>
        <Link to="/produto" className={`${styles.navLink} navLink`}>
          Produtos
        </Link>
        <a href="#" className={`${styles.navLink} navLink`}>
          Preços
        </a>
        <a href="#" className={`${styles.navLink} navLink`}>
          Contato
        </a>
      </div>

      {/* Se o usuário estiver logado, mostra botão de sair; senão, mostra login/registro */}
      <div className={`${styles.navActions} navActionsMobile`}>
        {isLoggedIn ? (
          <>
            <button className={styles.btnSecondary} onClick={handleLogout}>
              Sair
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className={styles.btnLogin}>
              Login
            </Link>
            <Link to="/cadastro" className={styles.btnRegister}>
              Registrar
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
