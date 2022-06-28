#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h> /* For mode constants */
#include "comunica_fila.h"

int main()
{
    // https://man7.org/linux/man-pages/man2/mq_open.2.htm
    // A função mq_open gera uma chave de comunicação entre processos baseado
    // um caminho e indentificador de projeto.
    // Mais informações no Cap.4 do livro texto (Mazziero).
    struct mq_attr attr;
    attr.mq_maxmsg = 1;             // número máximo de mensagens na fila.
    attr.mq_msgsize = sizeof(item); // tamanho de cada mensagem
    attr.mq_flags = 0;

    printf("Criando fila %s\n", QUEUE_NAME);
    // Notem que a fila está sendo criada em modo não-bloqueante.
    // S_IRWXU - escrita, leitura e execução pelo grupo (vindo da stat.h)
    // https://pubs.opengroup.org/onlinepubs/007904875/basedefs/sys/stat.h.html
    mqd_t fila = mq_open(QUEUE_NAME, O_CREAT, S_IRWXU, &attr);
    if (fila < 0)
    {
        perror("Erro criando fila de mensagens");
        exit(1);
    }
    printf("Fila %s criada com sucesso!\n", QUEUE_NAME);

    if (mq_close(fila) < 0)
    {
        perror("Erro fechando descritor da fila");
        exit(1);
    }
    return 0;
}