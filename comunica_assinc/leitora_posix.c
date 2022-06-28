#include <errno.h>
#include <fcntl.h> /* For O_* constants */
#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h> /* For mode constants */
#include <time.h>

#define MODO 0666
#define PRIORIDADE 0

// mensagem
// tem que ser um char a mais por que os strings tem que ser null-terminated.
typedef struct {
  char nome[101];
  char telefone[11];
} item;

int main() {
  mqd_t fila = mq_open("/so-ifal-queue", O_RDONLY);
  if (fila < 0) {
    perror("Erro abrindo fila de mensagens");
    exit(1);
  }

  item i;
  time_t now;
  // Lendo item da fila.
  time(&now);
  printf("[Leitora] Lendo item. Agora são: %s\n", ctime(&now));
  int status = mq_receive(fila, (void *)&i, sizeof(item), PRIORIDADE);
  if (status < 0) {
    perror("Erro recebendo mensagem.");
    exit(1);
  }
  time(&now);
  printf("[Leitora] Item %s-%s lido na lista. Agora são: %s\n.", i.nome,
         i.telefone, ctime(&now));

  // Fechando a fila.
  mq_close(fila);
  mq_unlink("/so-ifal-queue");
  return 0;
}