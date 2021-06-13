package main

import (
	"fmt"
	"log"
	"os"
	"syscall"
)

func main() {
	fmt.Printf("Olá, sou o processo pai %d. Meu pai é %d\n", os.Getpid(), os.Getppid())

	// Configurando parte da TCB do processo filho.
	// Restante das informações são configurados pelo ambiente
	// de execução Go.
	atributos := &syscall.ProcAttr{
		Dir:   ".",
		Env:   os.Environ(),
		Files: []uintptr{os.Stdin.Fd(), os.Stdout.Fd(), os.Stderr.Fd()},
	}

	// Criando processo filho e carregando binário.
	pid, err := syscall.ForkExec("../proc-filho/proc-filho", os.Args, atributos)
	if err != nil {
		log.Fatalf("Erro fazendo fork:%v", err)
	}

	// Pegando referência do processo filho.
	filho, err := os.FindProcess(pid)
	if err != nil {
		log.Fatalf("Erro conseguindo processo filho:%v", err)
	}

	// Com a referência para o processo filho podemos
	// executar diversas operações
	filho.Wait()
}
