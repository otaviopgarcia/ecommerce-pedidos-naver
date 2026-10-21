// src/components/Navbar.jsx
import { Link } from 'react-router-dom';
import { useCart } from '../contexts/CartContext';

export function Navbar() {
  const { carrinho } = useCart();
  const totalItens = carrinho.reduce((acc, item) => acc + item.quantidade, 0);

  return (
    <nav className="bg-orange-600 text-white p-4 shadow-md">
      <div className="max-w-6xl mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-black tracking-wide text-white flex items-center gap-2">
          <span>🟧</span> ECommerce-Naver
        </Link>
        <div className="flex gap-6 items-center font-medium">
          <Link to="/" className="hover:text-orange-200 transition">Catálogo</Link>
          <Link to="/admin/produtos" className="hover:text-orange-200 transition">Gestão Produtos</Link>
          <Link to="/pedidos" className="hover:text-orange-200 transition">Meus Pedidos</Link>
          <Link to="/carrinho" className="bg-white text-orange-600 px-4 py-1.5 rounded-lg font-bold hover:bg-orange-100 transition shadow">
            Carrinho <span>({totalItens})</span>
          </Link>
        </div>
      </div>
    </nav>
  );
}