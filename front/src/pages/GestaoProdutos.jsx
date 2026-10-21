// src/pages/GestaoProdutos.jsx
import { useState, useEffect } from 'react';
import { api } from '../services/api';

export function GestaoProdutos() {
  const [produtos, setProdutos] = useState([]);
  const [form, setForm] = useState({ id: null, nome: '', descricao: '', preco: '', categoria: 'Periféricos', estoque: '', imagemUrl: '' });
  const [editando, setEditando] = useState(false);

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    const dados = await api.getProdutos();
    setProdutos(dados);
  }

  function handleSubmit(e) {
    e.preventDefault();
    if (!form.nome || !form.preco || !form.estoque) return alert("Preencha os campos obrigatórios!");
    
    api.salvarProduto({
      ...form,
      preco: Number(form.preco),
      estoque: Number(form.estoque),
      imagemUrl: form.imagemUrl || "https://via.placeholder.com/200"
    });

    setForm({ id: null, nome: '', descricao: '', preco: '', categoria: 'Periféricos', estoque: '', imagemUrl: '' });
    setEditando(false);
    carregar();
  }

  function handleEditar(p) {
    setForm(p);
    setEditando(true);
  }

  async function handleExcluir(id) {
    if (confirm("Tem certeza que deseja excluir este produto?")) {
      await api.excluirProduto(id);
      carregar();
    }
  }

  return (
    <div className="p-4 space-y-8 bg-white min-h-screen">
      <h1 className="text-3xl font-bold text-orange-950 border-l-4 border-orange-500 pl-3">
        Gestão de Produtos (Admin)
      </h1>

      <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow-sm border border-orange-200 space-y-4">
        <h2 className="text-xl font-bold text-orange-950">{editando ? "Editar Produto" : "Cadastrar Novo Produto"}</h2>
        
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <input
            type="text" 
            placeholder="Nome do produto *" 
            value={form.nome}
            onChange={e => setForm({...form, nome: e.target.value})}
            className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white" 
            required
          />
          <input
            type="text" 
            placeholder="Categoria" 
            value={form.categoria}
            onChange={e => setForm({...form, categoria: e.target.value})}
            className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
          />
          <input
            type="number" 
            step="0.01" 
            placeholder="Preço (R$) *" 
            value={form.preco}
            onChange={e => setForm({...form, preco: e.target.value})}
            className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white" 
            required
          />
          <input
            type="number" 
            placeholder="Estoque (unidades) *" 
            value={form.estoque}
            onChange={e => setForm({...form, estoque: e.target.value})}
            className="p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white" 
            required
          />
        </div>

        <input
          type="url" 
          placeholder="URL da Imagem" 
          value={form.imagemUrl}
          onChange={e => setForm({...form, imagemUrl: e.target.value})}
          className="w-full p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none text-orange-950 placeholder-orange-400 bg-white"
        />

        <textarea
          placeholder="Descrição do produto" 
          value={form.descricao}
          onChange={e => setForm({...form, descricao: e.target.value})}
          className="w-full p-2.5 border border-orange-300 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none h-20 text-orange-950 placeholder-orange-400 bg-white"
        />

        <div className="flex gap-3">
          <button type="submit" className="bg-orange-500 text-white font-bold px-6 py-2 rounded-lg hover:bg-orange-600 transition shadow">
            {editando ? "Salvar Alterações" : "Cadastrar Produto"}
          </button>
          {editando && (
            <button
              type="button"
              onClick={() => { setEditando(false); setForm({ id: null, nome: '', descricao: '', preco: '', categoria: 'Periféricos', estoque: '', imagemUrl: '' }); }}
              className="bg-orange-100 text-orange-900 font-semibold px-4 py-2 rounded-lg hover:bg-orange-200 transition"
            >
              Cancelar
            </button>
          )}
        </div>
      </form>

      <div className="bg-white rounded-xl shadow-sm border border-orange-200 overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-orange-100 text-orange-950 border-b border-orange-200">
              <th className="p-3">ID</th>
              <th className="p-3">Produto</th>
              <th className="p-3">Categoria</th>
              <th className="p-3">Preço</th>
              <th className="p-3">Estoque</th>
              <th className="p-3 text-right">Ações</th>
            </tr>
          </thead>
          <tbody>
            {produtos.map(p => (
              <tr key={p.id} className="border-b border-orange-100 hover:bg-orange-50 transition">
                <td className="p-3 text-orange-800">#{p.id}</td>
                <td className="p-3 font-semibold text-orange-950">{p.nome}</td>
                <td className="p-3 text-orange-900">{p.categoria}</td>
                <td className="p-3 text-orange-950 font-bold">R$ {p.preco.toFixed(2)}</td>
                <td className="p-3 text-orange-900">{p.estoque} un.</td>
                <td className="p-3 text-right space-x-3">
                  <button onClick={() => handleEditar(p)} className="text-orange-600 font-bold hover:underline">Editar</button>
                  <button onClick={() => handleExcluir(p.id)} className="text-orange-900 font-bold hover:underline">Excluir</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}