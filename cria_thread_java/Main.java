import java.util.Arrays;

public class Main {

    private static final int numThreads = 10;
    private static final int memSize = 10;

    // Código da linha de execução principal, que é iniciada quando
    // o processo é iniciado.
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        // memoria é uma variável do processo, acessível por
        // todas as threads. Ela fica armazenada na área de
        // memória do processo, que é comum a todas as linhas
        // de execução criadas por ele.
        int[] memoria = new int[memSize];
        for (int i=0; i<memSize; i++) {
            memoria[i] = i;
        }

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            System.out.printf("Main: criando thread: %02d\n", i);
            threads[i] = new Sleeper(i, numThreads, memoria);
            //threads[i]    .setDaemon(true); // evita que o programa principal espere pelas threads.
            threads[i].start();  // inicia thread
        }

    //    Thread.sleep(3000);
    //    System.out.println("Thread main esperou 3s");   
        esperaThreads(threads);  // espera as threads restantes terminarem.

        long finishTime = System.currentTimeMillis();
        double execTime = (double)(finishTime - startTime)/(double)1000;
        System.out.printf("Main: Tchau! Executou em: %f segundos\n", execTime);
        // Note que o processo foi finalizado sem esperar que
        // as threads terminem.
        // Podemos utilizar mecanismos de sincronização para
        // aguardar as threads terminarem.
    }
    
    public static void esperaThreads(Thread[] threads) throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }
    }

    public static class Sleeper extends Thread {
        int tid, numThreads;
        int[] memoria; // guarda um ponteiro para o estado do processo (HEAP).

        // Parâmetro chegam via pilha e permanecem na pilha.
        public Sleeper(int tid, int numThreads, int[] memoria) {
            this.tid = tid;
            this.numThreads = numThreads;
            this.memoria = memoria;
        }

        @Override
        public void run() {
            System.out.printf("Olá da thread %02d de %02d. A área de memória compartilhada: %s.\n", tid,
                    numThreads, Arrays.toString(memoria));
            try {
                // Thread.sleep espera o tempo em milissegundos.
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            System.out.printf("Tchau da thread %02d de %02d\n", tid, numThreads);
        }
    }
}
