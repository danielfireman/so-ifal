package main

import (
	"fmt"
	"time"
)

func main() {
	fmt.Println("Irei dormir por 30 segundos")
	time.Sleep(30 * time.Second)
	fmt.Println("Acordando")
}
