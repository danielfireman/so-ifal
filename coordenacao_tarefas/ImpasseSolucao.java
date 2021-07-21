import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImpasseSolucao {
    static final int nThreads = 10;
    static final int nSteps = 10000;

    public static void main(String[] args) throws InterruptedException {
        Conta conta1 = new Conta(1);
        conta1.deposito(100000);
        Conta conta2 = new Conta(2);

        // Cria todas as threads.
        Thread[] transf12 = new Thread[nThreads];
        Thread[] transf21 = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            transf12[i] = new TransferThread("12-" + i, conta1, conta2);
            transf21[i] = new TransferThread("21-" + i, conta2, conta1);
        }

        // Inicializa as threads.
        for (int i = 0; i < nThreads; i++) {
            transf12[i].start();
            transf21[i].start();
        }

        // new Thread(()->{
        //    try {
        //        Thread.sleep(100);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    statusDasThreads();
        //}).start();

        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            transf12[i].join();
            transf21[i].join();
        }

        System.out.println(conta1.saldo);
        System.out.println(conta2.saldo);
    }

    static class Conta {
        double saldo = 0;
        int num;
        Lock mutex = new ReentrantLock();
        
        Conta(int num) {
            this.num = num;
        }

        void deposito(double valor) {
            saldo = saldo + valor;
        }

        void saque(double valor) {
            saldo = saldo - valor;
        }
    }

    static class TransferThread extends Thread {
        String id;
        Conta conta1, conta2;

        TransferThread(String id, Conta c1, Conta c2) {
            super(id);
            this.id = id;
            if (c1.num > c2.num) {
                this.conta1 = c1;
                this.conta2 = c2;
            } else {
                this.conta1 = c2;
                this.conta2 = c1;
            }
        }

        @Override
        public void run() {
            // Corpo da thread.
            for (int j = 0; j < nSteps; j++) {
                conta1.mutex.lock();
                System.out.printf("[%s]lock\n", id);
                conta2.mutex.lock();
                System.out.printf("[%s]lock\n", id);

                conta1.saque(1);
                conta2.deposito(1);

                conta1.mutex.unlock();
                conta2.mutex.unlock();
            }
        }
    }

    static void statusDasThreads() {
        // Creating set object to hold all the threads where
        // Thread.getAllStackTraces().keySet() returns
        // all threads including application threads and
        // system threads
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        // Now, for loop is used to iterate through the
        // threadset
        for (Thread t : threadSet) {
            // Printing the thread status using getState()
            // method
            System.out.println("Thread :" + t + ":" + "Thread status : " + t.getState());
        }
        System.out.printf("\n\n\n");
    }
}
