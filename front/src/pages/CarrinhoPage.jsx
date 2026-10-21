// src/pages/CarrinhoPage.jsx
import { useCart } from '../contexts/CartContext';
import { Link } from 'react-router-dom';

export function CarrinhoPage() {
  const { carrinho, removerDoCarrinho, atualizarQuantidade, limparCarrinho, valorTotal } = useCart();

  if (carrinho.length === 0) {
    return (
      <div className="p-8 text-center space-y-4 bg-white min-h-screen">
        <h1 className="text-2xl font-bold text-orange-950">Seu carrinho está vazio</h1>
        <p className="text-orange-800">Navegue pelo catálogo e adicione produtos incríveis!</p>
        <Link to="/" className="inline-block bg-orange-500 text-white font-bold px-6 py-2.5 rounded-lg hover:bg-orange-600 transition shadow">
          Voltar ao Catálogo
        </Link>
      </div>
    );
  }

  return (
    <div className="p-4 space-y-6 bg-white min-h-screen">
      <div className="flex justify-between items-center border-b border-orange-200 pb-4">
        <h1 className="text-3xl font-bold text-orange-950 border-l-4 border-orange-500 pl-3">
          Carrinho de Compras
        </h1>
        <button onClick={limparCarrinho} className="text-sm text-orange-800 font-bold hover:underline">
          Esvaziar Carrinho
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 space-y-4">
          {carrinho.map(item => (
            <div key={item.id} className="bg-white p-4 rounded-xl shadow-sm border border-orange-200 flex flex-col sm:flex-row items-center justify-between gap-4">
              <div className="flex items-center gap-4">
                <img src={item.imagemUrl} alt={item.nome} className="w-16 h-16 object-cover rounded-lg bg-orange-50" />
                <div>
                  <h3 className="font-bold text-orange-950">{item.nome}</h3>
                  <p className="text-sm text-orange-800">R$ {item.preco.toFixed(2)} un.</p>
                </div>
              </div>

              <div className="flex items-center gap-6">
                <div className="flex items-center border border-orange-300 rounded-lg bg-orange-50">
                  <button onClick={() => atualizarQuantidade(item.id, item.quantidade - 1)} className="px-3 py-1 font-bold text-orange-950 hover:bg-orange-100">-</button>
                  <span className="px-4 font-semibold text-orange-950">{item.quantidade}</span>
                  <button onClick={() => atualizarQuantidade(item.id, item.quantidade + 1)} className="px-3 py-1 font-bold text-orange-950 hover:bg-orange-100">+</button>
                </div>

                <p className="font-bold text-orange-950 w-24 text-right">
                  R$ {(item.preco * item.quantidade).toFixed(2)}
                </p>

                <button onClick={() => removerDoCarrinho(item.id)} className="text-orange-800 hover:text-orange-950 font-bold">
                  ✕
                </button>
              </div>
            </div>
          ))}
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-orange-200 h-fit space-y-4">
          <h2 className="text-xl font-bold text-orange-950 border-b border-orange-200 pb-2">Resumo do Pedido</h2>
          <div className="flex justify-between text-orange-900">
            <span>Subtotal</span>
            <span>R$ {valorTotal.toFixed(2)}</span>
          </div>
          <div className="flex justify-between text-orange-900">
            <span>Frete</span>
            <span className="text-orange-600 font-bold">Grátis</span>
          </div>
          <div className="flex justify-between text-xl font-bold text-orange-950 border-t border-orange-200 pt-2">
            <span>Total</span>
            <span className="text-orange-600">R$ {valorTotal.toFixed(2)}</span>
          </div>

          <Link to="/checkout" className="block text-center w-full bg-orange-500 text-white font-bold py-3 rounded-lg hover:bg-orange-600 transition shadow">
            Avançar para o Checkout
          </Link>
        </div>
      </div>
    </div>
  );
}