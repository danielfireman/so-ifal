import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mutex {
    static final int nThreads = 10;
    static final int nSteps = 10;
    static int sum = 0;
    static final Lock mutex = new ReentrantLock(); // criando o lock, que é um semáforo mais simples.

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new IncrementaThread(i);
        }

        // Inicializa as thread.
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            threads[i].join();
        }

        System.out.println(sum);
    }

    static class IncrementaThread extends Thread {
        int id;

        IncrementaThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            // Corpo da thread.
            System.out.printf("[%d] pedindo para entrar na região crítica.\n", id);
            mutex.lock();
            System.out.printf("[%d] dentro região crítica.\n", id);
            for (int j = 0; j < nSteps; j++) {
                // Refletir sobre o overhead gerado pela região onde está a região crítica.
                sum = sum + 1; // região crítica (existe condição de corrida)
            }
            mutex.unlock();
            System.out.printf("[%d] fora da região crítica.\n", id);
        }
    }
}
