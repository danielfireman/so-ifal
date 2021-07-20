import java.util.concurrent.Semaphore;

public class Semaforo {
    static final int nThreads = 10;
    static final int nSteps = 10;
    static int sum = 0;
    static final Semaphore sem = new Semaphore(1);

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
            for (int j = 0; j < nSteps; j++) {
                try {
                    System.out.printf("[%d] pedindo para entrar na região crítica.\n", id);
                    sem.acquire();
                    System.out.printf("[%d] dentro região crítica.\n", id);
                    // Refletir sobre o overhead gerado pela região onde está a região
                    // crítica.
                    sum = sum + 1; // região crítica (existe condição de corrida)
                    sem.release();
                    System.out.printf("[%d] fora da região crítica.\n", id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }
}
