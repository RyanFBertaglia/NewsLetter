import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Payment from './pages/Payment';
import ProductDisplay from './components/ProductDisplay';
import ThankYou from './pages/ThankYou';
import AboutMe from './pages/AboutMe';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/cadastro" element={<Signup />} />
        <Route path="/pagamento" element={<Payment />} />
        <Route path="/produto" element={<ProductDisplay />} />
        <Route path="/pagamento/success" element={<ThankYou />} />
        <Route path="/sobre" element={<AboutMe />} />
        <Route path="/pagamento/canceled" element={<ThankYou />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;