#include <errno.h>
#include <fcntl.h> /* For O_* constants */
#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h> /* For mode constants */
#include <time.h>

#define PRIORIDADE 0

// mensagem
// tem que ser um char a mais por que os strings tem que ser null-terminated.
typedef struct {
  char nome[101];
  char telefone[11];
} item;

int main() {
  // https://man7.org/linux/man-pages/man2/mq_open.2.htm
  // A função mq_open gera uma chave de comunicação entre processos baseado
  // um caminho e indentificador de projeto.
  // Mais informações no Cap.4 do livro texto (Mazziero).
  struct mq_attr attr;
  attr.mq_maxmsg = 1;             // número máximo de mensagens na fila.
  attr.mq_msgsize = sizeof(item); // tamanho de cada mensagem
  attr.mq_flags = 0;

  // Notem que a fila está sendo criada em modo somente escrita e
  // não-bloqueante.
  // S_IRWXU - escrita, leitura e execução pelo grupo (vindo da stat.h)
  // https://pubs.opengroup.org/onlinepubs/007904875/basedefs/sys/stat.h.html
  mqd_t fila = mq_open("/so-ifal-queue", O_CREAT | O_WRONLY | O_NONBLOCK,
                       S_IRWXU, &attr);
  if (fila < 0) {
    perror("Erro criando fila de mensagens");
    exit(1);
  }

  printf("O descritor %#x esta associado a fila\n", fila);

  time_t now;
  time(&now);

  item i;
  strcpy(i.nome, "Marcos Soares");
  strcpy(i.telefone, "8212345678");
  printf("[Escritora] Escrevendo item %s-%s na lista. Agora são: %s\n", i.nome,
         i.telefone, ctime(&now));

  int status = mq_send(fila, (void *)&i, sizeof(item), PRIORIDADE);
  if (status < 0) {
    perror("Erro enviando mensagem.");
    exit(1);
  }

  time(&now);
  printf("[Escritora] Item %s-%s escrito na lista. Agora são: %s\n.", i.nome,
         i.telefone, ctime(&now));
  return 0;
}