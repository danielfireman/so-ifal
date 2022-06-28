#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "comunica_fila.h"

int main()
{
  mqd_t fila = mq_open(QUEUE_NAME, O_WRONLY | O_NONBLOCK);
  if (fila < 0)
  {
    perror("Erro criando fila de mensagens");
    exit(1);
  }

  time_t now;
  time(&now);

  item i;
  strcpy(i.nome, "Marcos Soares");
  strcpy(i.telefone, "8212345678");
  printf("[Escritora] Escrevendo item %s-%s na lista. Agora são: %s\n", i.nome,
         i.telefone, ctime(&now));

  if (mq_send(fila, (void *)&i, sizeof(item), PRIORIDADE) < 0)
  {
    perror("Erro enviando mensagem.");
    exit(1);
  }

  time(&now);
  printf("[Escritora] Item %s-%s escrito na lista. Agora são: %s\n", i.nome,
         i.telefone, ctime(&now));

  if (mq_close(fila) < 0)
  {
    perror("Erro fechando descritor da fila");
    exit(1);
  }
  return 0;
}