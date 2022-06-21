#include <pthread.h>
#include <sched.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <unistd.h>

const int NUM_THREADS = 5;    // número de threads criadas.
const int NUM_REPETICOES = 5; // número de repetições do laço de cada thread.

typedef struct
{
  int id;
  int *comp;
} dado_thread;

// Função que define comportamento das threads
void *threads(void *arg)
{
  // Obtém identificadores da thread e processo
  pid_t tid = syscall(SYS_gettid);
  pid_t pid = syscall(SYS_getpid);
  dado_thread *dado = arg;

  printf("Olá, sou a thread %d, escrevo na posição %d e estou no "
         "processo %d.\n",
         tid, dado->id, pid);
  // Loop principal da thread.
  for (int i = 0; i < NUM_REPETICOES; i++)
  {
    printf("Thread %d está na repetição %d\n", dado->id, i);
    sleep(2);
  }

  dado->comp[dado->id] = tid; // atualiza array.
  free(dado);                 // desalocar memória criada para a struct.
  return 0;
}

int main()
{
  // Obtém identificadores da thread e processo.
  pid_t tid = syscall(SYS_gettid);
  pid_t pid = syscall(SYS_getpid);
  printf("Sou a thread principal, tenho o identificador %d e estou no processo "
         "%d \n",
         tid, pid);

  // Cria threads.
  pthread_t thread[NUM_THREADS];
  int comp[NUM_THREADS]; // memória compartilhada entre as threads.
  for (int i = 0; i < NUM_THREADS; i++)
  {
    dado_thread *dado = malloc(sizeof(dado_thread));
    dado->id = i;
    dado->comp = comp;
    pthread_create(&thread[i], NULL, threads, dado);
  }

  // // Aguarda as threads finalizarem a execução.
  for (int i = 0; i < NUM_THREADS; i++)
  {
    pthread_join(thread[i], NULL);
  }

  printf("Valores da memória compartilhada:\n");
  for (int i = 0; i < NUM_THREADS; i++)
  {
    printf("Thread %d valor %d\n", i, comp[i]);
  }

  return 0;
}