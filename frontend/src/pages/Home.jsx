import { useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h1 className="text-3xl font-bold mb-8">Bem-vindo à Nossa Plataforma</h1>
      
      <button
        onClick={() => navigate('/cadastro')}
        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg transition duration-300"
      >
        Cadastrar com Google
      </button>
    </div>
  );
}