#ifndef __COMUNICA_ASSINC_POSIX_H
#define __COMUNICA_ASSINC_POSIX_H

#define PRIORIDADE 0
#define QUEUE_NAME "/so-ifal-queue"

// item
// tem que ser um char a mais por que os strings tem que ser null-terminated.
typedef struct {
  char nome[101];
  char telefone[11];
} item;

#endif