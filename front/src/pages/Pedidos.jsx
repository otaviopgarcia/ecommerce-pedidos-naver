// src/pages/Pedidos.jsx
import { useState, useEffect } from 'react';
import { api } from '../services/api';

export function Pedidos() {
  const [pedidos, setPedidos] = useState([]);

  useEffect(() => {
    async function carregar() {
      const dados = await api.getPedidos();
      setPedidos(dados);
    }
    carregar();
  }, []);

  return (
    <div className="p-4 space-y-6 bg-white min-h-screen">
      <h1 className="text-3xl font-bold text-orange-950 border-l-4 border-orange-500 pl-3">
        Acompanhamento de Pedidos
      </h1>

      {pedidos.length === 0 ? (
        <p className="text-orange-800">Nenhum pedido realizado ainda.</p>
      ) : (
        <div className="space-y-4">
          {pedidos.map(p => (
            <div key={p.id} className="bg-white p-6 rounded-xl shadow-sm border border-orange-200 space-y-4">
              <div className="flex flex-wrap justify-between items-center border-b border-orange-200 pb-3 gap-2">
                <div>
                  <span className="font-bold text-lg text-orange-950">{p.id}</span>
                  <span className="text-sm text-orange-800 ml-4">Data: {p.data}</span>
                </div>
                <span className="px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider bg-orange-100 text-orange-950">
                  Status: {p.status}
                </span>
              </div>

              <div className="flex justify-between items-center text-sm text-orange-900">
                <p><strong>Cliente:</strong> {p.cliente}</p>
                <p><strong>Pagamento:</strong> {p.formaPagamento}</p>
                <p className="text-lg font-bold text-orange-950">Total: <span className="text-orange-600">R$ {p.total.toFixed(2)}</span></p>
              </div>

              <div className="bg-orange-50 p-3 rounded-lg text-xs space-y-1 border border-orange-100">
                <p className="font-bold text-orange-950">Itens:</p>
                {p.itens.map((item, idx) => (
                  <p key={idx} className="text-orange-900">
                    {item.quantidade}x {item.nome} - R$ {(item.preco * item.quantidade).toFixed(2)}
                  </p>
                ))}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}