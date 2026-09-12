
import com.ecommerce.pedidos.naver.modelo.*;
import com.ecommerce.pedidos.naver.modelo.pagamento.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Aplicacao {

    public static void main(String[] args) {

        Funcionario func = new Funcionario(
            "Carlos Silva",
            "987.654.321-11",
            "M-4589",
            "Gerente Comercial"
        );

        System.out.println(func.getIdentificacao());

        FormaPagamento[] pagamentos = {

            new Pix(
                new BigDecimal("150.00"),
                "vendas@loja.com",
                "E-mail"
            ),

            new Boleto(
                new BigDecimal("300.00"),
                "34191.79001 01043.510047 91020.150008 5 90000000030000",
                LocalDate.now().plusDays(3)
            ),

            new CartaoCredito(
                new BigDecimal("899.90"),
                "**** **** **** 4321",
                "Mastercard",
                3
            )
        };

        for (FormaPagamento pagamento : pagamentos) {

            System.out.println(pagamento.getResumo());

            pagamento.processar();
        }
    }
}
