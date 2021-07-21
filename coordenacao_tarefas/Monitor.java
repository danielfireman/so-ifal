public class Monitor {
    static final int nThreads = 10;
    static final int nSteps = 10;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];
        Incrementador inc = new Incrementador();

        // Cria todas as threads.
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new IncrementaThread(i, inc);
        }

        // Inicializa as thread.
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        // Espera todas as threads acabarem o processamento.
        for (int i = 0; i < nThreads; i++) {
            threads[i].join();
        }

        System.out.println(inc.soma);
    }

    static class IncrementaThread extends Thread {
        int id;
        Incrementador inc;

        IncrementaThread(int id, Incrementador inc) {
            this.id = id;
            this.inc = inc;
        }

        @Override
        public void run() {
            for (int j = 0; j < nSteps; j++) {
                System.out.printf("[%d] pedindo para entrar na região crítica.\n", id);
                inc.incremento(this.id); // região crítica (existe condição de corrida)
                System.out.printf("[%d] fora da região crítica.\n", id);
            }
        }
    }

    static class Incrementador {
        int soma = 0;

        // Note a palavra reservada synchronized. Ela indica um comportamento análogo a
        // ter um em torno da execução do método.
        synchronized void incremento(int id) {
            System.out.printf("[%d] dentro região crítica.\n", id);
            soma += 1;
        }

        // Poderia haver outros métodos aqui, os quais seriam protegidos
        // de acesso concorrente
    }
}
