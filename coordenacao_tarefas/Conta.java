public class Conta {

    static double saldo = 0;

    static void deposito(double valor, int id) {
        saldo = saldo + valor;
        System.out.printf("[%d]seu saldo é %f\n", id, saldo);
    }

    static final int nThreads = 100;
    static int i;

    static class DepositoThread extends Thread {
        int id;
        double valor;

        DepositoThread(int id, double valor) {
            this.id = id;
            this.valor = valor;
        }

        @Override
        public void run() {
            deposito(this.valor, this.id);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Simulando vários terminais.
        Thread[] threads = new Thread[nThreads];
        for (i = 0; i < nThreads; i++) {
            threads[i] = new DepositoThread(i, i);
        }

        for (i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        for (i = 0; i < nThreads; i++) {
            threads[i].join();
        }

        // Inicia operações (simulando requisições sendo atendidas no servidor).
        System.out.printf("Depois da função %f\n", saldo);
    }
}
