# Comunicação Assíncrona

## Usando filas POSIX (mqueue)

**Compilar**
```
gcc leitora_posix.c -o leitora -lrt
gcc escritora_posix.c -o escritora -lrt
```

**Executar**
```
./escritora
./leitura
```