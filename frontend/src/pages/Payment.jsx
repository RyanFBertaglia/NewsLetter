import React, { useState } from 'react';
import ProductDisplay from '../components/ProductDisplay';
import { api } from '../services/api';

const Payment = ({ userToken }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleCheckout = async () => {
    setIsLoading(true);
    setError('');
    
    try {
      const response = await api.post('/auth/pagamento', {}, {
        headers: {
          'Authorization': `Bearer ${userToken}`
        }
      });
      
      const sessionUrl = response.data;
      window.location.href = sessionUrl;
    } catch (err) {
      setError(err.response?.data || err.message || 'Erro ao processar pagamento');
      setIsLoading(false);
    }
  };

  return (
    <div className="payment-container">
      <h2>Finalizar Pagamento</h2>
      <ProductDisplay 
        onCheckout={handleCheckout}
        isLoading={isLoading}
      />
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default Payment;