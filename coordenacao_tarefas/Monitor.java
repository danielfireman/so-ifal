public class Monitor {
    static final int nThreads = 100;
    static final int nSteps = 10000;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];
        Conta conta = new Conta();

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                // Corpo da thread.
                for (int j = 0; j < nSteps; j++) {
                    conta.deposito(1);
                }
            });
        }

        // Inicializa as thread.
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            threads[i].join();
        }

        System.out.println(conta.saldo);
    }

    // Note a palavra reservada synchronized. Ela indica um comportamento análogo a ter um
    // em torno da execução do método.
    static class Conta {
        double saldo = 0;
        synchronized void deposito(double valor) {
            saldo = saldo + valor;
        }
        // Poderia haver outros métodos aqui, os quais seriam protegidos
        // de acesso concorrente
    }
}
