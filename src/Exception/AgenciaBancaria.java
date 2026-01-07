package Exception;

import javax.swing.*;
import java.util.ArrayList;

public class AgenciaBancaria {

    static ArrayList<Conta> contasbancarias = new ArrayList<>();
    static int geradorConta = 1000;

    public static void main(String[] args) {
        while(true) {
            String opcao = JOptionPane.showInputDialog(
                    "AGÊNCIA BANCÁRIA\n\n"
                    + "1 - Gerente (Criar conta)\n"
                    + "2 - Pessoa física (Acessar conta)\n"
                    + "3 - Sair");

            if(opcao == null || opcao.equals("3")) {
                JOptionPane.showMessageDialog(null, "Encerrando sistema...");
                break;
            }

            switch(opcao) {
                case "1":
                    criarConta();
                    break;
                case "2":
                    operacoes();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void criarConta() {
        String nome = JOptionPane.showInputDialog("Nome: ");
        String cpf = JOptionPane.showInputDialog("CPF: ");
        String email = JOptionPane.showInputDialog("E-mail: ");
        String endereco = JOptionPane.showInputDialog("Endereco completo: ");
        int senha = Integer.parseInt(JOptionPane.showInputDialog("Crie uma senha numérica: "));

        Pessoa pessoa = new Pessoa(nome, cpf, email, endereco, senha);
        Conta conta = new Conta(geradorConta++, pessoa);
        contasbancarias.add(conta);

        JOptionPane.showMessageDialog(null, "Conta criada com sucesso!\nNúmero da conta: " + conta.getNumeroConta());
    }

    public static void operacoes() {
        String menu = JOptionPane.showInputDialog(
                "1 - Depositar\n"
                + "2 - Sacar\n"
                + "3 - Transferir\n"
                + "4 - Listar conta\n"
                + "5 - Sair da conta");

        switch(menu) {
            case "1":
                depositar();
                break;
            case "2":
                sacar();
                break;
            case "3":
                transferir();
                break;
            case "4":
                listar();
                break;
            case "5":
                return;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida!");

            operacoes();
        }
    }

    private static Conta encontrarConta(int numeroConta) {
        for(Conta c : contasbancarias) {
            if(c.getNumeroConta() == numeroConta) {
                return c;
            }
        }
        return null;
    }

    public static void depositar() {
        try {
            int nc = Integer.parseInt(JOptionPane.showInputDialog("Número da conta: "));
            Conta conta = encontrarConta(nc);
            if(conta == null) {
                throw new Exception("Conta não encontrada.");
            }

            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do depósito: "));
            conta.depositar(valor);
            JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        operacoes();
    }

    public static void sacar() {
        try {
            int nc = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));
            Conta conta = encontrarConta(nc);
            if (conta == null) throw new Exception("Conta não encontrada");


            int senha = Integer.parseInt(JOptionPane.showInputDialog("Digite sua senha:"));
            if (senha != conta.getPessoa().getSenha()) throw new Exception("Senha incorreta");


            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do saque:"));
            conta.sacar(valor);
            JOptionPane.showMessageDialog(null, "Saque realizado com sucesso");
        } catch (SaldoInsuficienteException e) {
            JOptionPane.showMessageDialog(null, "Erro de negócio: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        operacoes();
    }


    public static void transferir() {
        try {
            int origem = Integer.parseInt(JOptionPane.showInputDialog("Conta de origem:"));
            int destino = Integer.parseInt(JOptionPane.showInputDialog("Conta de destino:"));


            Conta contaOrigem = encontrarConta(origem);
            Conta contaDestino = encontrarConta(destino);


            if (contaOrigem == null || contaDestino == null)
                throw new Exception("Conta origem ou destino não encontrada");
            int senha = Integer.parseInt(JOptionPane.showInputDialog("Digite sua senha:"));
            if (senha != contaOrigem.getPessoa().getSenha())
                throw new Exception("Senha incorreta");


            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor da transferência:"));
            contaOrigem.transferir(contaDestino, valor);


            JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        operacoes();
    }

    public static void listar() {
        int nc = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));
        Conta conta = encontrarConta(nc);
        if (conta != null) {
            JOptionPane.showMessageDialog(null, conta);
        } else {
            JOptionPane.showMessageDialog(null, "Conta não encontrada");
        }
        operacoes();
    }
}
