# Comunicação Assíncrona

## Usando filas POSIX (mqueue)

**Compilar**
```
gcc leitora.c -o leitora -lrt
gcc escritora.c -o escritora -lrt
```

**Executar**
```
./escritora
./leitura
```