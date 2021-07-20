import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mutex {
    static final int nThreads = 100;
    static final int nSteps = 10000;
    static int sum = 0;
    static final Lock mutex = new ReentrantLock(); // criando o lock, que é um semáforo mais simples.

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                // Corpo da thread.
                for (int j = 0; j < nSteps; j++) {
                    mutex.lock();
                    try {
                        sum = sum + 1; // região crítica (existe condição de corrida)
                    } finally {
                        mutex.unlock();
                    }
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

        System.out.println(sum);
    }
}
