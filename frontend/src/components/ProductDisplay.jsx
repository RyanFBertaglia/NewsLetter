import React from 'react';
import { Link } from "react-router-dom";
import styles from '../styles/product.module.css';
import navs from '../styles/home.module.css';

const ProductDisplay = ({ onCheckout, isLoading }) => (
  <>
    <nav className={`${navs.nav} navMobile`}>
      <Link to="/">
        <div className={navs.navLogo}>
          <div className={navs.logoCircle}>
            <svg style={{width: '16px', height: '16px', color: 'white'}} fill="currentColor" viewBox="0 0 20 20">
              <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
              <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
            </svg>
          </div>
        </div>
      </Link>
      <div className={`${navs.navCenter} navCenterMobile`}>
        <a href="#" className={`${navs.navLink}`}>Produtos</a>
        <a href="#" className={`${navs.navLink}`}>Preços</a>
        <a href="#" className={`${navs.navLink}`}>Contact</a>
      </div>
    </nav>

    <main>
      <section className={styles.card}>
        <div className={styles.imagem}>
          <img src="../../newsletter.png" alt="Imagem Newsletter" />
        </div>
        <div className={styles.conteudo}>
          <h3>Acesso a Newsletter</h3>
          <span className={styles.tag}>Tag</span>
          <div className={styles.preco}>$97</div>
          <p>Descrição: Com essa aquisição você terá acesso a um mês de acesso à minha Newsletter. Ao final do período, você receberá um email para renovação.</p>
          <button 
            onClick={onCheckout} 
            disabled={isLoading}
            className={styles.btnComprar}>
            {isLoading ? 'Processando pagamento...' : 'Pagar com Cartão'}
          </button>
        </div>
      </section>
    </main>
  </>
);

export default ProductDisplay;
