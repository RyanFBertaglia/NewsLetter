import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ThankYou = () => {
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const query = new URLSearchParams(window.location.search);
    
    if (query.get('success')) {
      setMessage('Pagamento realizado com sucesso! Você receberá um e-mail de confirmação.');
    } else if (query.get('canceled')) {
      setMessage('Pagamento cancelado. Você pode tentar novamente quando quiser.');
    } else {
      // Redirect to home if no status
      navigate('/');
    }
  }, [navigate]);

  return (
    <div className="thank-you-container">
      <h2>Obrigado!</h2>
      <p>{message}</p>
      <button onClick={() => navigate('/')} className="home-button">
        Voltar para a página inicial
      </button>
    </div>
  );
};

export default ThankYou;