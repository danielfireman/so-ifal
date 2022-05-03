package main

import "fmt"

const tamanho = 1024 // declaração de constate vai para a área TEXT (código binário)

const string = "dljshajlkjlkdasjkldskljsalkjdsakldaskldkjdaskldjkldaskjdsakljdsakjldasjkl"

var buffer = [tamanho][tamanho]byte{} // declaração vai para a área BSS

func main() {
	for i := 0; i < tamanho; i++ {
		for j := 0; j < tamanho; j++ {
			buffer[i][j] = byte((i + j) % 256)
		}
	}
	fmt.Scanln()
}
