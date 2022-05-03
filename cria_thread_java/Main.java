import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final int numThreads = 8;

    // Código da linha de execução principal, que é iniciada quando
    // o processo é iniciado.
    public static void main(String[] args) throws InterruptedException {
        // memoriaCompartilhada é uma variável do processo, acessível por
        // todas as threads. Ela fica armazenada na área de
        // memória do processo, que é comum a todas as linhas
        // de execução criadas por ele.
        List memoriaCompartilhada = new ArrayList();
        memoriaCompartilhada.add(1);
        memoriaCompartilhada.add(2);

        for (int i = 0; i < numThreads; i++) {
            System.out.printf("Main: criando thread: %02d\n", i);
            Thread t = new Thread(new Sleeper(i, numThreads, memoriaCompartilhada));
            t.setDaemon(true); // evita que o programa principal espere pelas threads.
            t.start();
        }
        System.out.println("Main: Tchau!");
        // Note que o processo foi finalizado sem esperar que
        // as threads terminem.
        // Podemos utilizar mecanismos de sincronização para
        // aguardar as threads terminarem.
    }

    public static class Sleeper implements Runnable {
        int tid, numThreads;
        List memoriaCompartilhada; // guarda um ponteiro para o estado do processo (HEAP).

        // Parâmetro chegam via pilha e permanecem na pilha.
        public Sleeper(int tid, int numThreads, List memoriaCompartilhada) {
            this.tid = tid;
            this.numThreads = numThreads;
            this.memoriaCompartilhada = memoriaCompartilhada;
        }

        @Override
        public void run() {
            System.out.printf("Olá da thread %02d de %02d. A área de memória compartilhada tem %d elementos.\n", tid,
                    numThreads, memoriaCompartilhada.size());
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
