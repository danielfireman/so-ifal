package main

import (
	"fmt"
	"time"
)

// numThreads é uma variável global, acessível por
// todas as threads. Ela fica armazenada na área de
// memória do processo, que é comum a todas as linhas
// de execução criadas por ele.
const numThreads = 8

// Código da linha de execução principal, que é iniciada quando
// o processo é iniciado.
func main() {
	for i := 0; i < numThreads; i++ {
		fmt.Printf("Main: criando thread :%02d\n", i)
		go corpoThread(i)
	}
	fmt.Println("Main: Tchau!")

	// Note que o processo foi finalizado sem esperar que
	// as threads terminem.
	// Podemos utilizar mecanismos de sincronização para
	// aguardar as threads terminarem.
}

// Função associada a cada linha de execução.
// tid é uma variável local a thread.
func corpoThread(tid int) {
	// O printf consegue acessar tanto a variável local
	// quanto a global.
	fmt.Printf("Olá da thread %d de %d\n", tid, numThreads)
	time.Sleep(3 * time.Second)
	fmt.Printf("Tchau da thread %d de %d\n", tid, numThreads)
}
