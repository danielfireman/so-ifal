#include <pthread.h>
#include <sched.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <unistd.h>

const int NUM_THREADS = 5;
const int NUM_REPETICOES = 10;

typedef struct {
  int dado_thread;
  pid_t thread_mae;
} thread_info;

// Função que define comportamento das threads
void *threads(void *dado) {
  // Obtém identificadores da thread e processo
  pid_t tid = syscall(SYS_gettid);
  pid_t pid = syscall(SYS_getpid);
  thread_info *infot =
      (thread_info *)dado; // converte de volta para thread_info

  // Loop principal da thread.
  for (int i = 0; i < NUM_REPETICOES; i++) {
    printf("Olá, sou a thread %d, tenho o identificador %d e estou no "
           "processo %d. Minha mãe é a thread %d.\n",
           (*infot).dado_thread, tid, pid, (*infot).thread_mae);
    sleep(2);
  }
}

int main() {
  // Obtém identificadores da thread e processo.
  pid_t tid = syscall(SYS_gettid);
  pid_t pid = syscall(SYS_getpid);
  printf("Sou a thread principal, tenho o identificador %d e estou no processo "
         "%d \n",
         tid, pid);

  pthread_t thread[NUM_THREADS];
  for (int i = 0; i < NUM_THREADS; i++) {

    // Cria threads.
    thread_info infot;
    infot.dado_thread = i;
    infot.thread_mae = tid;
    pthread_create(&thread[i], NULL, threads, (void *)&infot);
  }

  // Aguarda as threads.
  for (long i = 0; i <= NUM_THREADS; i++) {
    pthread_join(thread[i], NULL);
  }

  pthread_exit(NULL); // encerrando a thread principal
  return 0;
}