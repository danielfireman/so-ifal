#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "comunica_fila.h"

int main() {
  mqd_t fila = mq_open(QUEUE_NAME, O_RDONLY | O_NONBLOCK);
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
  printf("[Leitora] Item %s-%s lido na lista. Agora são: %s\n", i.nome,
         i.telefone, ctime(&now));

  // Fechando a fila.
  mq_close(fila);
  return 0;
}