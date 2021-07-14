public class EsperaOcupada {
    static boolean ocupada = false;

    static void enter() {
        while (ocupada) {
        }
        ocupada = true;
    }

    static void leave() {
        ocupada = false;
    }

    static final int nThreads = 100;
    static final int nSteps = 10000;
    static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                // Corpo da thread.
                for (int j = 0; j < nSteps; j++) {
                    enter();
                    sum = sum + 1; // região crítica (existe condição de corrida)
                    leave();
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