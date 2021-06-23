package main

import (
	"fmt"
	"time"
)

type item struct {
	nome     string
	telefone string
}

// canal compartilhado.
var lista = make(chan item)

func escritora(i item) {
	// coloca o item passado como parâmetro no canal.
	lista <- i
}

func leitora() {
	// retira um elemento do canal (o que foi adicionado primeiro)
	i := <-lista
	fmt.Println(i)
}

func main() {
	marcos := item{"Marcos Soares", "8212345678"}
	// A função escritora vai ser executada numa outra linha de
	// execução (thread) recebendo como parâmetro da execução
	// o item da lista a ser inserido (marcos)
	go escritora(marcos)

	// a função leitora vai imprimir o item da lista preveamente
	// adicionado
	go leitora()

	thiago := item{"Thiago Gazaroli", "8256781234"}
	go escritora(thiago)

	go leitora()

	// vamos esperar que as outras threads executem.
	// isso é uma forma de coordenação. A thread principal
	// espera pelas threads criadas.
	time.Sleep(10 * time.Millisecond)

	close(lista)
}
