import java.util.concurrent.Semaphore;

public class Semaforo {
    static final int nThreads = 100;
    static final int nSteps = 10000;
    static int sum = 0;
    static final Semaphore sem = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                // Corpo da thread.
                for (int j = 0; j < nSteps; j++) {
                    try {
                        sem.acquire();
                        // Refletir sobre o overhead gerado pela região onde está a região
                        // crítica.
                        sum = sum + 1; // região crítica (existe condição de corrida)
                        sem.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.exit(-1);
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
