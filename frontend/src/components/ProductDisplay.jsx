import React from 'react';

const ProductDisplay = ({ onCheckout, isLoading }) => (
  <section className="product-display">
    <div className="product">
      <img
        src="https://i.imgur.com/EHyR2nP.png"
        alt="The cover of Stubborn Attachments"
      />
      <div className="description">
        <h3>Stubborn Attachments</h3>
        <h5>R$ 97,00/ano</h5>
      </div>
    </div>
    <button 
      onClick={onCheckout} 
      disabled={isLoading}
      className="checkout-button"
    >
      {isLoading ? 'Processando pagamento...' : 'Pagar com Cartão'}
    </button>
  </section>
);

export default ProductDisplay;