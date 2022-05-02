package main

import (
	"fmt"
	"log"

	"github.com/tklauser/go-sysconf"
)

func main() {
	// Padrão POSIX para quantidade de ticks de relógio em 1 segundo.
	clktck, err := sysconf.Sysconf(sysconf.SC_CLK_TCK)
	if err != nil {
		log.Fatalf("Sysconf: %v", err)
	}
	fmt.Printf("sysconf(SC_CLK_TCK) = %v\n", clktck)

	ps, err := sysconf.Sysconf(sysconf.SC_PRIORITY_SCHEDULING)
	if err != nil {
		log.Fatalf("Sysconf: %v", err)
	}
	fmt.Printf("sysconf(SC_PRIORITY_SCHEDULING) = %v\n", ps)
}
