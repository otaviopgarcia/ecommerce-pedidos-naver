// src/pages/Catalogo.jsx
import { useState, useEffect } from 'react';
import { api } from '../services/api';
import { useCart } from '../contexts/CartContext';

export function Catalogo() {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [busca, setBusca] = useState('');
  const [categoriaFiltro, setCategoriaFiltro] = useState('Todas');
  const { adicionarAoCarrinho } = useCart();

  useEffect(() => {
    async function carregarProdutos() {
      try {
        const dados = await api.getProdutos();
        setProdutos(dados);
      } catch (erro) {
        console.error("Erro ao carregar produtos:", erro);
      } finally {
        setLoading(false);
      }
    }
    carregarProdutos();
  }, []);

  const categorias = ['Todas', ...new Set(produtos.map(p => p.categoria))];

  const produtosFiltrados = produtos.filter(produto => {
    const bateNome = produto.nome.toLowerCase().includes(busca.toLowerCase());
    const bateCategoria = categoriaFiltro === 'Todas' || produto.categoria === categoriaFiltro;
    return bateNome && bateCategoria;
  });

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64 bg-white">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-orange-500"></div>
      </div>
    );
  }

  return (
    <div className="p-4 space-y-6 bg-white min-h-screen">
      <h1 className="text-3xl font-bold text-orange-950 border-l-4 border-orange-500 pl-3">
        Catálogo de Produtos
      </h1>

      <div className="flex flex-col sm:flex-row gap-4 justify-between bg-white p-4 rounded-xl shadow-sm border border-orange-200">
        <input
          type="text"
          placeholder="Buscar produto por nome..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          className="p-2.5 border border-orange-300 rounded-lg w-full sm:w-80 focus:outline-none focus:ring-2 focus:ring-orange-500 text-orange-950 placeholder-orange-400 bg-white"
        />

        <select
          value={categoriaFiltro}
          onChange={(e) => setCategoriaFiltro(e.target.value)}
          className="p-2.5 border border-orange-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-500 text-orange-950 bg-white"
        >
          {categorias.map(cat => (
            <option key={cat} value={cat}>{cat}</option>
          ))}
        </select>
      </div>

      {produtosFiltrados.length === 0 ? (
        <p className="text-orange-800 text-center py-8 font-medium">Nenhum produto encontrado.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {produtosFiltrados.map((produto) => (
            <div key={produto.id} className="bg-white rounded-xl shadow-sm border border-orange-200 overflow-hidden flex flex-col justify-between hover:shadow-md transition">
              <div>
                <img
                  src={produto.imagemUrl}
                  alt={produto.nome}
                  className="w-full h-48 object-cover bg-orange-50"
                />
                <div className="p-4 space-y-2">
                  <span className="text-xs font-bold uppercase tracking-wider text-orange-800 bg-orange-100 px-2 py-1 rounded">
                    {produto.categoria}
                  </span>
                  <h2 className="text-lg font-bold text-orange-950">{produto.nome}</h2>
                  <p className="text-sm text-orange-800 line-clamp-2">{produto.descricao}</p>
                </div>
              </div>

              <div className="p-4 border-t border-orange-100 flex items-center justify-between">
                <div>
                  <p className="text-xl font-bold text-orange-950">
                    R$ {produto.preco.toFixed(2).replace('.', ',')}
                  </p>
                  <p className="text-xs text-orange-700">Estoque: {produto.estoque} un.</p>
                </div>
                <button
                  onClick={() => adicionarAoCarrinho(produto)}
                  className="bg-orange-500 text-white text-sm font-bold px-4 py-2 rounded-lg hover:bg-orange-600 transition shadow-sm"
                >
                  Adicionar
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}