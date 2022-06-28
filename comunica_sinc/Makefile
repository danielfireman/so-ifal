build: le_fila.c escreve_fila.c cria_fila.c encerra_fila.c
	gcc le_fila.c -o le_fila -lrt
	gcc escreve_fila.c -o escreve_fila -lrt
	gcc cria_fila.c -o cria_fila -lrt
	gcc encerra_fila.c -o encerra_fila -lrt

cria_fila: build
	./cria_fila

encerra_fila: build
	./encerra_fila

clean:
	rm -f cria_fila escreve_fila cria_fila encerra_fila