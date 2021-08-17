package main

const tamanho = 1024 * 100 // declaração de constate vai para a área TEXT (código binário)

var buffer = [tamanho][tamanho]byte{} // declaração vai para a área BSS

func main() {
	for i := 0; i < tamanho; i++ {
		for j := 0; j < tamanho; j++ {
			buffer[i][j] = byte((i + j) % 256)
		}
	}
}
