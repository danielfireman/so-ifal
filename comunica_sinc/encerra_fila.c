#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include "comunica_sinc_posix.h"

int main()
{
  printf("Encerrando fila %s\n", QUEUE_NAME);
  int status = mq_unlink(QUEUE_NAME);
  if (status < 0)
  {
    perror("Erro encerrando fila de mensagens");
    exit(1);
  }
  printf("Fila %s encerrada com sucesso!\n", QUEUE_NAME);
  return 0;
}