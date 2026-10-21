// src/App.jsx
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { CartProvider } from './contexts/CartContext';
import { Navbar } from './components/NavBar';
import { Catalogo } from './pages/Catalogo';
import { GestaoProdutos } from './pages/GestaoProdutos';
import { CarrinhoPage } from './pages/CarrinhoPage';
import { Checkout } from './pages/Checkout';
import { Pedidos } from './pages/Pedidos';

export default function App() {
  return (
    <CartProvider>
      <BrowserRouter>
        <div className="min-h-screen bg-slate-50 text-slate-800">
          <Navbar />
          <main className="max-w-6xl mx-auto p-4">
            <Routes>
              <Route path="/" element={<Catalogo />} />
              <Route path="/admin/produtos" element={<GestaoProdutos />} />
              <Route path="/carrinho" element={<CarrinhoPage />} />
              <Route path="/checkout" element={<Checkout />} />
              <Route path="/pedidos" element={<Pedidos />} />
              <Route 
                path="*" 
                element={
                  <div className="p-8 text-center">
                    <h1 className="text-3xl font-bold">404 - Página Não Encontrada</h1>
                  </div>
                } 
              />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </CartProvider>
  );
}