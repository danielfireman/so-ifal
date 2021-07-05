package main

import (
	"fmt"
	"time"
)

type item struct {
	nome     string
	telefone string
}

// Thread escritora escreve o item na lista.
func escritora(lista chan item, i item) {
	fmt.Printf("[Escritora] Escrevendo item %v na lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
	// coloca o item passado como parâmetro no canal.
	lista <- i
	fmt.Printf("[Escritora] Item item %v escrito na lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
}

// Thread leitora aguarda um item ser adicionado a lista e imprime
// este item no terminal.
func leitora(lista chan item) {
	fmt.Printf("[Leitora] Aguardando item ser adicionado a lista. Agora são: %v\n", time.Now().Format("15:04:05"))

	// retira um elemento do canal (o que foi adicionado primeiro)
	i := <-lista

	// Imprime no terminal o item adicionado.
	fmt.Printf("[Leitora] Item %v adicionado a lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
}

func main() {
	// Canal compartilhado. Simulando uma lista com telefones.
	var lista = make(chan item)

	// Esta primeira parte ilustra a sincronicidade na leitura dos dados.
	// Ou seja, a thread bloqueia retirando mensagens do canal.
	fmt.Printf("Parte 1: bloqueio de leitura.\n\n")

	// A função leitora vai bloquear enquanto não houver item
	// na lista.
	go leitora(lista)

	time.Sleep(2 * time.Second)

	// A função escritora vai ser executada numa outra linha de
	// execução (thread) recebendo como parâmetro da execução
	// o item da lista a ser inserido (marcos)
	go escritora(lista, item{"Marcos Soares", "8212345678"})

	// Espera que a escrita tenha ocorrido nesse tempo.
	time.Sleep(2 * time.Second)

	// Esta segunda parte ilustra a sincronicidade na escrita dos dados.
	// Ou seja, a thread bloqueia adicionado mensagens do canal.
	fmt.Printf("\n\nParte 2: bloqueio de escrita.\n\n")

	// A função escritora vai ser executada numa outra linha de
	// execução (thread) recebendo como parâmetro da execução
	// o item da lista a ser inserido (marcos)
	go escritora(lista, item{"Júlio César", "8240987430932"})

	// Esperamos quea thread escritoria fique esperando que a thread leitora
	// esteja ativa e lendo dados do canal para desbloquear.
	time.Sleep(2 * time.Second)

	// Deve desbloquear a thread escritora.
	go leitora(lista)

	// Vamos esperar que as outras threads executem.
	// isso é uma forma de coordenação. A thread principal
	// espera pelas threads criadas.
	time.Sleep(2 * time.Second)

	close(lista)
}
