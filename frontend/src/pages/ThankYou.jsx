import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';


const ThankYou = () => {
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const query = new URLSearchParams(window.location.search);
    
    if (query.get('success')) {
      try {
        const response = api.post('/auth/renovar', {}, {
          headers: {
                'Authorization': `Bearer ${userToken}`
          }
        });
        } catch (err) {
          setError(err.response?.data || err.message || 'Erro ao atualizar status');
          setIsLoading(false);
        }
      setMessage('Pagamento realizado com sucesso! O serviço já foi adquirido, a entrega será feita no seu email.');
    } else if (query.get('canceled')) {
      setMessage('Pagamento cancelado. Você pode tentar novamente quando quiser.');
    } else {
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