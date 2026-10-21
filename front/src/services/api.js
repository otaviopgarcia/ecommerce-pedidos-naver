// src/services/api.js

export const PRODUTOS_MOCK = [
  {
    id: 1,
    nome: "Teclado Mecânico RGB",
    descricao: "Teclado mecânico com switches azuis e retroiluminação RGB.",
    preco: 299.90,
    categoria: "Periféricos",
    estoque: 15,
    imagemUrl: "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&auto=format&fit=crop"
  },
  {
    id: 2,
    nome: "Mouse Gamer 16000 DPI",
    descricao: "Mouse ergonômico com 6 botões programáveis.",
    preco: 149.90,
    categoria: "Periféricos",
    estoque: 8,
    imagemUrl: "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=500&auto=format&fit=crop"
  },
  {
    id: 3,
    nome: "Monitor 24'' Full HD 144Hz",
    descricao: "Monitor gamer IPS com tempo de resposta de 1ms.",
    preco: 999.00,
    categoria: "Monitores",
    estoque: 5,
    imagemUrl: "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=500&auto=format&fit=crop"
  }
];

export const PEDIDOS_MOCK = [
  {
    id: "PED-1001",
    data: "2026-10-20",
    cliente: "João Silva",
    total: 449.80,
    formaPagamento: "PIX",
    status: "Pendente",
    itens: [
      { id: 1, nome: "Teclado Mecânico RGB", quantidade: 1, preco: 299.90 },
      { id: 2, nome: "Mouse Gamer 16000 DPI", quantidade: 1, preco: 149.90 }
    ]
  }
];

let produtosLocal = [...PRODUTOS_MOCK];
let pedidosLocal = [...PEDIDOS_MOCK];

export const api = {
  getProdutos: async () => [...produtosLocal],

  salvarProduto: async (produto) => {
    if (produto.id) {
      produtosLocal = produtosLocal.map(p => (p.id === produto.id ? produto : p));
    } else {
      const novo = { ...produto, id: Date.now() };
      produtosLocal.push(novo);
    }
    return true;
  },

  excluirProduto: async (id) => {
    produtosLocal = produtosLocal.filter(p => p.id !== id);
    return true;
  },

  getPedidos: async () => [...pedidosLocal],

  criarPedido: async (dadosCheckout, itens, total) => {
    const novoPedido = {
      id: `PED-${Math.floor(1000 + Math.random() * 9000)}`,
      data: new Date().toISOString().split('T')[0],
      cliente: dadosCheckout.nome,
      total,
      formaPagamento: dadosCheckout.pagamento,
      status: "Pendente",
      itens
    };
    pedidosLocal.unshift(novoPedido);
    return novoPedido;
  }
};