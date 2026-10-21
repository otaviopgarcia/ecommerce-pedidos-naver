// src/pages/Checkout.jsx
import { useState } from 'react';
import { useCart } from '../contexts/CartContext';
import { api } from '../services/api';
import { useNavigate } from 'react-router-dom';

export function Checkout() {
  const { carrinho, valorTotal, limparCarrinho } = useCart();
  const navigate = useNavigate();
  const [form, setForm] = useState({ nome: '', email: '', cpf: '', cep: '', endereco: '', pagamento: 'PIX' });
  const [processando, setProcessando] = useState(false);

  if (carrinho.length === 0) {
    return (
      <div className="p-8 text-center text-orange-950 bg-white min-h-screen">
        <p>Seu carrinho está vazio.</p>
      </div>
    );
  }

  async function handleFinalizar(e) {
    e.preventDefault();
    setProcessando(true);

    setTimeout(async () => {
      await api.criarPedido(form, carrinho, valorTotal);
      limparCarrinho();
      setProcessando(false);
      alert("Pagamento Aprovado com Sucesso!");
      navigate('/pedidos');
    }, 1500);
  }

  return (
    <div className="p-4 space-y-6 max-w-4xl mx-auto bg-white min-h-screen">
      <h1 className="text-3xl font-bold text-orange-950 border-l-4 border-orange-500 pl-3">
        Checkout e Pagamento
      </h1>

      <form onSubmit={handleFinalizar} className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-orange-200 space-y-4">
          <h2 className="text-xl font-bold text-orange-950 border-b border-orange-200 pb-2">Dados do Cliente</h2>
          <input
            type="text" 
            placeholder="Nome Completo *" 
            required
            value={form.nome} 
            onChange={e => setForm({...form, nome: e.target.value})}
            className="w-full p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
          />
          <input
            type="email" 
            placeholder="E-mail *" 
            required
            value={form.email} 
            onChange={e => setForm({...form, email: e.target.value})}
            className="w-full p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
          />
          <div className="grid grid-cols-2 gap-4">
            <input
              type="text" 
              placeholder="CPF *" 
              required
              value={form.cpf} 
              onChange={e => setForm({...form, cpf: e.target.value})}
              className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
            />
            <input
              type="text" 
              placeholder="CEP *" 
              required
              value={form.cep} 
              onChange={e => setForm({...form, cep: e.target.value})}
              className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
            />
          </div>
          <input
            type="text" 
            placeholder="Endereço Completo *" 
            required
            value={form.endereco} 
            onChange={e => setForm({...form, endereco: e.target.value})}
            className="w-full p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
          />
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-orange-200 space-y-6 flex flex-col justify-between">
          <div className="space-y-4">
            <h2 className="text-xl font-bold text-orange-950 border-b border-orange-200 pb-2">Forma de Pagamento</h2>
            <div className="space-y-2">
              {['PIX', 'Cartão de Crédito', 'Boleto Bancário'].map(metodo => (
                <label key={metodo} className="flex items-center gap-3 p-3 border border-orange-200 rounded-lg cursor-pointer hover:bg-orange-50 transition">
                  <input
                    type="radio"
                    name="pagamento"
                    value={metodo}
                    checked={form.pagamento === metodo}
                    onChange={e => setForm({...form, pagamento: e.target.value})}
                    className="accent-orange-500"
                  />
                  <span className="font-semibold text-orange-950">{metodo}</span>
                </label>
              ))}
            </div>
          </div>

          <div className="border-t border-orange-200 pt-4 space-y-4">
            <div className="flex justify-between text-xl font-bold text-orange-950">
              <span>Total a Pagar:</span>
              <span className="text-orange-600">R$ {valorTotal.toFixed(2)}</span>
            </div>

            <button
              type="submit"
              disabled={processando}
              className="w-full bg-orange-500 text-white font-bold py-3 rounded-lg hover:bg-orange-600 transition shadow disabled:opacity-50"
            >
              {processando ? "Processando Pagamento..." : "Simular e Confirmar Pagamento"}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}