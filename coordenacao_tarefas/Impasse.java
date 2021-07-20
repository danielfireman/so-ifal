import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Impasse {
    static final int nThreads = 10;
    static final int nSteps = 10000;

    public static void main(String[] args) throws InterruptedException {
        Conta conta1 = new Conta();
        conta1.deposito(100000);
        Conta conta2 = new Conta();

        // Cria todas as threads.
        Thread[] transf12 = new Thread[nThreads];
        Thread[] transf21 = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            transf12[i] = new TransferThread(i, conta1, conta2);
            transf21[i] = new TransferThread(i, conta2, conta1);
        }

        // Inicializa as threads.
        for (int i = 0; i < nThreads; i++) {
            transf12[i].start();
            transf21[i].start();
        }

        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            transf12[i].join();
            transf21[i].join();
        }

        System.out.println(conta1.saldo);
        System.out.println(conta2.saldo);
    }

    // Note a palavra reservada synchronized. Ela indica um comportamento análogo a
    // ter um
    // em torno da execução do método.
    static class Conta {
        double saldo = 0;
        Lock mutex = new ReentrantLock();

        void deposito(double valor) {
            saldo = saldo + valor;
        }

        void saque(double valor) {
            saldo = saldo - valor;
        }
    }

    static class TransferThread extends Thread {
        int id;
        Conta conta1, conta2;

        TransferThread(int id, Conta c1, Conta c2) {
            this.id = id;
            this.conta1 = c1;
            this.conta2 = c2;
        }

        @Override
        public void run() {
            // Corpo da thread.
            for (int j = 0; j < nSteps; j++) {
                conta1.mutex.lock();
                System.out.printf("[%d]lock\n", id);
                conta2.mutex.lock();
                System.out.printf("[%d]lock\n", id);

                conta1.saque(1);
                conta2.deposito(1);

                conta1.mutex.unlock();
                conta2.mutex.unlock();
            }
        }
    }
}
