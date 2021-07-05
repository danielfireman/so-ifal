package main

import (
	"fmt"
	"time"
)

type item struct {
	nome     string
	telefone string
}

// Thread escritora escreve um ou mais itens na lista.
func escritora(lista chan item, itens ...item) {
	fmt.Printf("[Escritora] Escrevendo iten(s) %v na lista. Agora são: %v\n", itens, time.Now().Format("15:04:05"))
	for _, i := range itens {
		// A diretiva select bloqueia até que um dos casos (eventos) aconteça.
		select {
		case lista <- i:
			fmt.Printf("[Escritora] Item %v escrito na lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
		case <-time.After(1 * time.Second):
			fmt.Printf("[Escritora] Timeout. Agora são: %v\n", time.Now().Format("15:04:05"))
		}
	}
}

// Thread leitora aguarda `n` itens serem adicionado a lista e imprime
// este(s) item no terminal.
func leitora(lista chan item, n int) {
	for i := 1; i <= n; i++ {
		fmt.Printf("[Leitora] Aguardando %d item ser adicionado a lista. Agora são: %v\n", i, time.Now().Format("15:04:05"))
		// A diretiva select bloqueia até que um dos casos (eventos) aconteça.
		select {
		case item := <-lista:
			fmt.Printf("[Leitora] Item %d adicionado a lista: %v. Agora são: %v\n", i, item, time.Now().Format("15:04:05"))
		case <-time.After(1 * time.Second):
			fmt.Printf("[Leitora] Item %d timeout. Agora são: %v\n", i, time.Now().Format("15:04:05"))
		}
	}
}

func main() {
	fmt.Printf("[Principal] Iniciando execução. Agora são: %v\n\n", time.Now().Format("15:04:05"))
	// Canal compartilhado. Simulando uma lista com telefones.
	// Note a capacidade do canal: 1.
	var lista = make(chan item, 1)

	fmt.Printf("Parte 1: escrita semi-síncrona.\n\n")

	// A função escritora vai ser executada numa outra linha de
	// execução (thread). Ela tentará inserir dois elementos e
	// deve dar timeout na tentativa de inserir o segundo pois o
	// canal tem capacidade 1.
	go escritora(lista, item{"Marcos Soares", "8212345678"}, item{"Bezerra da Silva", "820982309813"})

	// Espera para visualizar as tentativas de operação de escrita.
	time.Sleep(2 * time.Second)

	// Esta segunda parte ilustra a semi-sincronicidade na escrita dos dados.
	// Ou seja, a thread bloqueia adicionado mensagens do canal.
	fmt.Printf("\n\nParte 2: leitura semi-síncrona.\n\n")

	// A função leitora também vai ser executada em outra linha
	// de execução e vai tentar obter 2 mensagens do canal.
	// Como a escritora escreveu 1 (o segundo deu timeout), essa thread
	// vai bloquear e dar timeout na tentativa de obter a segunda
	// mensagem.
	go leitora(lista, 2)

	// Vamos esperar que as outras threads executem.
	// isso é uma forma de coordenação. A thread principal
	// espera pelas threads criadas.
	time.Sleep(3 * time.Second)

	close(lista)
	fmt.Printf("\n[Principal] Encerrando execução. Agora são: %v\n", time.Now().Format("15:04:05"))
}
