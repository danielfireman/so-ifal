public class Problema {
    static final int nThreads = 100;
    static final int nSteps = 10000;
    static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        // Alocando array de threads.
        Thread[] threads = new Thread[nThreads];

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                // Corpo da thread.
                for (int j = 0; j < nSteps; j++) {
                    sum = sum + 1; // região crítica (existe condição de corrida)
                }
            });
        }
        // Inicializa todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }
        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            threads[i].join();
        }
        // Imprime resultado.
        System.out.println(sum);
    }
}