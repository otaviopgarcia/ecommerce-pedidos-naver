// src/contexts/CartContext.jsx
import { createContext, useContext, useState, useEffect } from 'react';

const CartContext = createContext();

export function CartProvider({ children }) {
  // Inicializa o carrinho tentando recuperar dados salvos no localStorage
  const [carrinho, setCarrinho] = useState(() => {
    const salvos = localStorage.getItem('carrinho_ecommerce');
    return salvos ? JSON.parse(salvos) : [];
  });

  // Salva no localStorage a cada alteração do carrinho
  useEffect(() => {
    localStorage.setItem('carrinho_ecommerce', JSON.stringify(carrinho));
  }, [carrinho]);

  const adicionarAoCarrinho = (produto) => {
    setCarrinho((prev) => {
      const existe = prev.find((item) => item.id === produto.id);
      if (existe) {
        return prev.map((item) =>
          item.id === produto.id
            ? { ...item, quantidade: item.quantidade + 1 }
            : item
        );
      }
      return [...prev, { ...produto, quantidade: 1 }];
    });
  };

  const removerDoCarrinho = (id) => {
    setCarrinho((prev) => prev.filter((item) => item.id !== id));
  };

  const atualizarQuantidade = (id, quantidade) => {
    if (quantidade <= 0) {
      removerDoCarrinho(id);
      return;
    }
    setCarrinho((prev) =>
      prev.map((item) => (item.id === id ? { ...item, quantidade } : item))
    );
  };

  const limparCarrinho = () => setCarrinho([]);

  const valorTotal = carrinho.reduce(
    (acc, item) => acc + item.preco * item.quantidade,
    0
  );

  return (
    <CartContext.Provider
      value={{
        carrinho,
        adicionarAoCarrinho,
        removerDoCarrinho,
        atualizarQuantidade,
        limparCarrinho,
        valorTotal,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export const useCart = () => useContext(CartContext);