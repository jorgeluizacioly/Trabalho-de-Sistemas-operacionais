# Guia de Testes - Escalonador de Processos

Este guia fornece exemplos práticos para testar o escalonador de processos, incluindo valores de entrada e resultados esperados para diferentes cenários.

## Cenários de Teste

### 1. Teste Básico - SJF (Shortest Job First)

**Entrada:**
```
P1: Nome = "Word"
    Chegada = 0
    Execução = 5

P2: Nome = "Chrome"
    Chegada = 1
    Execução = 3

P3: Nome = "Excel"
    Chegada = 2
    Execução = 1
```

**Sequência Esperada:**
1. P1 executa de t=0 até t=5 (primeiro a chegar)
2. P3 executa de t=5 até t=6 (menor tempo entre P2 e P3)
3. P2 executa de t=6 até t=9

**Métricas Esperadas:**
- P1: Espera = 0, Turnaround = 5
- P2: Espera = 5, Turnaround = 8
- P3: Espera = 3, Turnaround = 4
- Tempo Médio de Espera = 2.67
- Tempo Médio de Turnaround = 5.67

### 2. Teste de Preempção - SRT (Shortest Remaining Time)

**Entrada:**
```
P1: Nome = "Firefox"
    Chegada = 0
    Execução = 6

P2: Nome = "Notepad"
    Chegada = 2
    Execução = 2

P3: Nome = "Calculator"
    Chegada = 3
    Execução = 1
```

**Sequência Esperada:**
1. P1 inicia em t=0
2. P2 chega em t=2 (não preempta, tempo maior)
3. P3 chega e preempta em t=3 (menor tempo = 1)
4. P3 termina em t=4
5. P2 executa de t=4 a t=6
6. P1 retoma e termina de t=6 a t=12

**Métricas Esperadas:**
- P1: Espera = 3, Turnaround = 9
- P2: Espera = 2, Turnaround = 4
- P3: Espera = 0, Turnaround = 1
- Tempo Médio de Espera = 1.67
- Tempo Médio de Turnaround = 4.67

### 3. Teste de Concorrência - SJF

**Entrada:**
```
P1: Nome = "Processo1"
    Chegada = 0
    Execução = 4

P2: Nome = "Processo2"
    Chegada = 0
    Execução = 2

P3: Nome = "Processo3"
    Chegada = 0
    Execução = 3
```

**Sequência Esperada:**
1. P2 executa primeiro (t=0 a t=2)
2. P3 executa em seguida (t=2 a t=5)
3. P1 executa por último (t=5 a t=9)

**Métricas Esperadas:**
- P1: Espera = 5, Turnaround = 9
- P2: Espera = 0, Turnaround = 2
- P3: Espera = 2, Turnaround = 5
- Tempo Médio de Espera = 2.33
- Tempo Médio de Turnaround = 5.33

### 4. Teste de Preempção Múltipla - SRT

**Entrada:**
```
P1: Nome = "Video"
    Chegada = 0
    Execução = 8

P2: Nome = "Audio"
    Chegada = 1
    Execução = 4

P3: Nome = "Download"
    Chegada = 2
    Execução = 2

P4: Nome = "Update"
    Chegada = 3
    Execução = 1
```

**Sequência Esperada:**
1. P1 inicia em t=0
2. P2 preempta em t=1
3. P3 preempta em t=2
4. P4 preempta em t=3, executa até t=4
5. P3 retoma e termina (t=4 a t=6)
6. P2 retoma e termina (t=6 a t=10)
7. P1 termina (t=10 a t=18)

**Métricas Esperadas:**
- P1: Espera = 10, Turnaround = 18
- P2: Espera = 5, Turnaround = 9
- P3: Espera = 2, Turnaround = 4
- P4: Espera = 0, Turnaround = 1
- Tempo Médio de Espera = 4.25
- Tempo Médio de Turnaround = 8

## Como Executar os Testes

1. Inicie o programa:
```bash
./run.sh
```

2. Para cada cenário de teste:
   - Selecione o algoritmo apropriado (SJF ou SRT)
   - Adicione os processos na ordem especificada
   - Clique em "Executar"
   - Compare os resultados com os valores esperados

## O que Observar

### 1. Log de Execução
- Ordem correta dos processos
- Momentos de preempção (SRT)
- Tempos de início e fim de cada processo

### 2. Tabela de Resultados
- Tempos de espera
- Tempos de turnaround
- Ordem de conclusão dos processos

### 3. Comportamentos Específicos

**Para SJF:**
- Processos não devem ser interrompidos
- Sempre escolhe o menor job disponível
- Respeita tempos de chegada

**Para SRT:**
- Preempção ocorre corretamente
- Processo com menor tempo restante tem prioridade
- Cálculo correto do tempo restante

## Casos Especiais para Testar

1. **Processos Idênticos:**
```
P1: Chegada = 0, Execução = 3
P2: Chegada = 0, Execução = 3
```
- Verificar ordem FIFO para tempos iguais

2. **Chegadas Simultâneas:**
```
P1: Chegada = 2, Execução = 4
P2: Chegada = 2, Execução = 2
```
- Verificar escolha do menor tempo

3. **Processo Longo com Interrupções:**
```
P1: Chegada = 0, Execução = 10
P2: Chegada = 1, Execução = 2
P3: Chegada = 3, Execução = 1
```
- Verificar múltiplas preempções no SRT

## Verificação de Erros

1. **Validação de Entrada:**
- Tentar valores negativos
- Tentar campos vazios
- Tentar caracteres inválidos

2. **Casos Limite:**
- Tempo de execução = 0
- Muitos processos simultâneos
- Tempos muito longos

3. **Robustez:**
- Adicionar processos durante execução
- Alternar entre algoritmos
- Reiniciar simulação 