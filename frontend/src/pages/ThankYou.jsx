import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../services/api';
import styles from '../styles/home.module.css';
import elements from '../styles/elements.module.css';

const ThankYou = () => {
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { status } = useParams();
  const classes = [elements.btnSecondary, elements.center];


  useEffect(() => {
    const processPayment = async () => {


      if (status == 'success') {
          setMessage('Pagamento realizado com sucesso! O serviço já foi adquirido, a entrega será feita no seu email.');
      }
      if (status == 'canceled') {
        setMessage('Pagamento cancelado. Você pode tentar novamente quando quiser.');
      }
    };

    processPayment();
  }, [navigate]);

  return (
    <div className={elements.centerContainer}>
              <div className={elements.thankYouCard}>

        <h2 className={elements.thankYouTitle}>Obrigado!</h2>
      {error && <p className="error-message">Erro: {error}</p>}
      <p>{message}</p>
        <div className={elements.center}>
            <button onClick={() => navigate('/')} className={elements.btnSecondary}>
                Voltar para a página inicial
            </button>
        </div>
      </div>
    </div>
  );
};

export default ThankYou;