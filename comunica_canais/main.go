package main

import (
	"fmt"
	"time"
)

// Mensagem. Simula uma entrada de uma lista telefônica.
type item struct {
	nome     string
	telefone string
}

// canal compartilhado.
var lista = make(chan item)

// Tarefa escritora que vai ser executada em segundo plano.
// Escreve o item passado como parâmetro no canal.
func escritora(i item) {
	fmt.Printf("[Escritora] Escrevendo iten %v na lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
	lista <- i
	fmt.Printf("[Escritora] Item %v escrito na lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
}

// Recebe a mensagem do canal (o que foi adicionado primeiro)
// Também executada em segundo plano.
func leitora() {
	fmt.Printf("[Leitora] Aguardando item ser adicionado a lista. Agora são: %v\n", time.Now().Format("15:04:05"))
	i := <-lista
	fmt.Printf("[Leitora] Item adicionado a lista: %v. Agora são: %v\n", i, time.Now().Format("15:04:05"))
}

func main() {
	// A função escritora vai ser executada numa outra linha de
	// execução (thread) recebendo como parâmetro da execução
	// o item da lista a ser inserido (marcos)
	go escritora(item{"Marcos Soares", "8212345678"})

	time.Sleep(1 * time.Second)

	// a função leitora vai imprimir o item da lista preveamente
	// adicionado
	go leitora()

	go escritora(item{"Thiago Gazaroli", "8256781234"})

	go leitora()

	// vamos esperar que as outras threads executem.
	// isso é uma forma de coordenação. A thread principal
	// espera pelas threads criadas.
	time.Sleep(10 * time.Millisecond)

	close(lista)
}
