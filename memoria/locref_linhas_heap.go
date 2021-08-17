package main

const tamanho = 1024 * 100 // declaração de constate vai para a área TEXT (código binário)

func main() {
	buffer := [tamanho][tamanho]byte{} // inicialização vai para o heap.
	for i := 0; i < tamanho; i++ {
		for j := 0; j < tamanho; j++ {
			buffer[i][j] = byte((i + j) % 256)
		}
	}
}
