package main

import (
	"fmt"
	"os"
	"time"
)

func main() {
	fmt.Printf("Olá, sou processo filho %d. Meu pai é %d\n", os.Getpid(), os.Getppid())
	time.Sleep(10 * time.Second) // Dorme por 10 segundos.
}
