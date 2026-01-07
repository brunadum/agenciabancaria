package Exception;

public class Conta {
    private int numeroConta;
    private double saldo;
    private Pessoa pessoa;

    public Conta(int numeroConta, Pessoa pessoa) {
        this.numeroConta = numeroConta;
        this.pessoa = pessoa;
        this.saldo = 0;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSlaod() {
        return saldo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void depositar(double valor) {
        if(valor <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
        saldo += valor;
    }

    public void sacar(double valor) {
        if(valor <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }

        if(saldo < valor) {
            throw new IllegalArgumentException(String.format("Saldo R$%.2f insuficiente para saque de R$%.2f.", saldo, valor));
        }

        saldo -= valor;
    }

    public void transferir(Conta destino, double valor) {
        if(valor <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo.");
        }

        if(valor > saldo) {
            throw new IllegalArgumentException(String.format("Saldo R$%.2f insuficiente para transferência de R$%.2f.", saldo, valor));
        }

        this.saldo -= valor;
        destino.saldo += valor;
    }

    @Override
    public String toString() {
        return pessoa +
                "\nNúmero da conta: " + numeroConta
                + "\nSaldo: " + saldo;
    }
}
