import { useState, useEffect, useRef } from 'react'
import './App.css'

function App() {

  interface Endereco {
    cep: string;
    logradouro: string;
    complemento: string;
    bairro: string;
    localidade: string;
    uf: string;
    ibge?: string;
    gia?: string;
    ddd?: string;
    siafi?: string;
  }


  const [cep, setCep] = useState('');
  const [adress, setAdress] = useState<Endereco | null>(null);
  const [expanded, setExpanded] = useState(false);
  const [number, setNumber] = useState('');
  const inputRef = useRef<HTMLInputElement>(null);
  const [showCursor, setShowCursor] = useState(true);
  const [editableComplement, setEditableComplement] = useState(adress?.complemento || '');

  useEffect(() => {
    const interval = setInterval(() => {
      setShowCursor(prev => !prev);
    }, 500);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
  if (adress) {
    setEditableComplement(''); 
  }
}, [adress]);

  const searchCep = async () => {
    const cleanCep = cep.trim();

    if (!cleanCep) {
      console.log("CEP vazio");
      return;
    }

    try {
      const answer = await fetch(`http://localhost:8080/api/cep/${cleanCep}`);

      if (!answer.ok) {
        const dataError = await answer.json();
        console.log("Erro do backend:", dataError.message || answer.status);
        setAdress(null);
        setExpanded(false);
        return;
      }

      const data = await answer.json();
      setAdress(data);
      setExpanded(true);
    } catch (error) {
      console.log("Erro na requisição", error);
      setAdress(null);
      setExpanded(false);
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      searchCep();
    }
  };

  return (
    <div className='min-h-screen w-full flex flex-col items-center bg-gradient-to-br from-cyan-100 via-blue-300 to-cyan-200'>
      <div className='pt-36 relative'>
        <h1 className='mb-6 text-2xl font-bold'>ENCONTRE O ENDEREÇO</h1>
        <input
          ref={inputRef}
          autoFocus
          className='border pl-8 pr-4 py-2 rounded'
          value={cep}
          onChange={(e) => setCep(e.target.value)}
          placeholder="Digite o CEP"
          onKeyDown={handleKeyDown}
        />
        <button className='ml-6 px-4 py-2 bg-yellow-200 text-black rounded hover:shadow-xl transition-shadow duration-900 ease-in-out' onClick={searchCep}>
          Buscar
        </button>
      </div>

      {expanded && adress && (
        <div className='mt-8 rounded-lg bg-yellow-200 text-gray-900 hover:rounded-lg hover:shadow-xl transition-shadow duration-900 ease-in-out '>
          <div className='max-w-md w-full p-6 border rounded-lg shadow-md'>
            <div className='grid grid-cols-2 gap-4'>
              {/* conteúdo do card */}

              <div className='border-none p-3 rounded'>CEP:</div>
              <input className='border p-3 rounded' value={adress.cep} readOnly placeholder="CEP" />

              <div className='border-none p-3 rounded'>Logradouro:</div>
              <input className='border p-3 rounded' value={adress.logradouro} readOnly placeholder="Logradouro" />

              <div className='border-none p-3 rounded'>Complemento:</div>
              <input className='border p-3 rounded' value={editableComplement} onChange={(e) => setEditableComplement(e.target.value)}/>

              <div className='border-none p-3 rounded'>Bairro:</div>
              <input className='border p-3 rounded' value={adress.bairro} readOnly placeholder="Bairro" />

              <div className='border-none p-3 rounded'>Cidade:</div>
              <input className='border p-3 rounded' value={adress.localidade} readOnly placeholder="Cidade" />

              <div className='border-none p-3 rounded'>UF:</div>
              <input className='border p-3 rounded' value={adress.uf} readOnly placeholder="UF" />

              <div className='border-none p-3 rounded'>DDD:</div>
              <input className='border p-3 rounded' value={adress.ddd} readOnly placeholder="DDD" />
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
export default App;
