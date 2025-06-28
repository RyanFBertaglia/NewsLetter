// src/App.js
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Payment from './pages/Payment';
import ThankYou from './pages/ThankYou';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/cadastro" element={<Signup />} />
        <Route path="/pagamento" element={<Payment />} />
        <Route path="/obrigado" element={<ThankYou />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;