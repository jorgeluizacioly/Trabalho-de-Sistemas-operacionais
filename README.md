# Escalonador de Processos - Instruções de Instalação

Este é um projeto Java que implementa um escalonador de processos usando JavaFX para a interface gráfica.

## Pré-requisitos

### Para todos os sistemas operacionais
- Java Development Kit (JDK) 17 ou superior
- Apache Maven 3.8.1 ou superior

## Instalação por Sistema Operacional

### Windows

1. Instalar o JDK:
   - Baixe o JDK 17 do site oficial da Oracle ou use o OpenJDK
   - Execute o instalador .exe
   - Adicione o JAVA_HOME às variáveis de ambiente:
     - Painel de Controle > Sistema > Configurações avançadas do sistema > Variáveis de ambiente
     - Adicione JAVA_HOME apontando para o diretório de instalação do JDK
     - Adicione %JAVA_HOME%\bin ao PATH

2. Instalar o Maven:
   - Baixe o Maven do site oficial: https://maven.apache.org/download.cgi
   - Extraia o arquivo zip para C:\Program Files\Apache\maven
   - Adicione às variáveis de ambiente:
     - Adicione MAVEN_HOME apontando para C:\Program Files\Apache\maven
     - Adicione %MAVEN_HOME%\bin ao PATH

3. Verificar a instalação:
   ```cmd
   java --version
   mvn --version
   ```

### macOS

1. Instalar o Homebrew (se não tiver):
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. Instalar o JDK:
   ```bash
   brew install openjdk@17
   ```

3. Instalar o Maven:
   ```bash
   brew install maven
   ```

4. Verificar a instalação:
   ```bash
   java --version
   mvn --version
   ```

### Linux (Ubuntu/Debian)

1. Atualizar o sistema:
   ```bash
   sudo apt update
   sudo apt upgrade
   ```

2. Instalar o JDK:
   ```bash
   sudo apt install openjdk-17-jdk
   ```

3. Instalar o Maven:
   ```bash
   sudo apt install maven
   ```

4. Verificar a instalação:
   ```bash
   java --version
   mvn --version
   ```

## Executando o Projeto

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   cd <nome-do-diretorio>
   ```

2. Compile e execute o projeto:
   ```bash
   # Dê permissão de execução ao script (Linux/macOS)
   chmod +x run.sh
   
   # Execute o script
   ./run.sh
   ```

   No Windows, use:
   ```cmd
   mvn clean compile exec:java
   ```

## Solução de Problemas

### Windows
- Se o comando `java` ou `mvn` não for reconhecido, verifique as variáveis de ambiente
- Certifique-se de que não há espaços nos caminhos das variáveis de ambiente

### macOS
- Se estiver usando Apple Silicon (M1/M2), o JavaFX já está configurado com o classificador correto (mac-aarch64)
- Se estiver usando Intel, altere o classificador no pom.xml para mac-x64

### Linux
- Se houver problemas com o JavaFX, instale as dependências adicionais:
  ```bash
  sudo apt install libgl1-mesa-dev
  sudo apt install libx11-dev
  sudo apt install libxt-dev
  sudo apt install libxext-dev
  sudo apt install libxrender-dev
  sudo apt install libxtst-dev
  ```

## IDEs Recomendadas

Para uma melhor experiência de desenvolvimento, recomendamos usar uma das seguintes IDEs:
- IntelliJ IDEA (Community ou Ultimate)
- Eclipse com o plugin e(fx)clipse
- Visual Studio Code com as extensões Java e JavaFX

Estas IDEs oferecem melhor suporte para desenvolvimento JavaFX e podem facilitar a execução do projeto.

# Escalonador de Processos

Este projeto implementa um simulador de escalonamento de processos com interface gráfica, utilizando os algoritmos SJF (Shortest Job First) e SRT (Shortest Remaining Time).

## Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- JavaFX (incluído automaticamente via Maven)

## Como Executar

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd POO
```

2. Execute o projeto usando o script:
```bash
./run.sh
```

Ou execute manualmente com Maven:
```bash
mvn clean javafx:run
```

## Como Usar

### Interface Principal

A interface do escalonador possui os seguintes componentes:

1. **Seleção do Algoritmo**
   - Escolha entre "Shortest Job First (SJF)" ou "Shortest Remaining Time (SRT)"
   - O algoritmo pode ser alterado a qualquer momento

2. **Adição de Processos**
   - Nome do Processo: Identificador único do processo
   - Tempo de Chegada: Momento em que o processo chega ao sistema
   - Tempo de Execução: Tempo total necessário para executar o processo

3. **Controles**
   - Botão "Adicionar Processo": Adiciona um novo processo à fila
   - Botão "Executar": Inicia a simulação do escalonamento

4. **Visualização**
   - Log de Execução: Mostra em tempo real o que está acontecendo
   - Tabela de Resultados: Exibe métricas por processo após a execução

### Algoritmos Implementados

#### SJF (Shortest Job First)
- Escalonamento não-preemptivo
- Seleciona o processo com menor tempo de execução
- Executa o processo até o fim sem interrupções
- Ideal para processos em lote

#### SRT (Shortest Remaining Time)
- Escalonamento preemptivo
- Versão preemptiva do SJF
- Interrompe o processo atual se chegar um processo com tempo restante menor
- Melhor para sistemas interativos

## Como Testar

1. **Teste Básico (SJF)**
   - Adicione 3 processos:
     ```
     P1: Chegada = 0, Execução = 6
     P2: Chegada = 2, Execução = 4
     P3: Chegada = 4, Execução = 2
     ```
   - Resultado esperado: Execução na ordem P1 -> P2 -> P3

2. **Teste de Preempção (SRT)**
   - Adicione 3 processos:
     ```
     P1: Chegada = 0, Execução = 8
     P2: Chegada = 1, Execução = 4
     P3: Chegada = 2, Execução = 2
     ```
   - Resultado esperado: P1 será interrompido para executar P2 e P3

3. **Teste de Concorrência**
   - Adicione vários processos com mesmo tempo de chegada
   - Verifique se o escalonador escolhe corretamente o processo mais curto

## Métricas Calculadas

1. **Tempo de Espera**
   - Tempo que o processo aguarda na fila antes de começar/continuar execução
   - Quanto menor, melhor

2. **Tempo de Turnaround**
   - Tempo total desde a chegada até a conclusão do processo
   - Inclui tempo de espera e tempo de execução

3. **Utilização de CPU**
   - Porcentagem de tempo em que a CPU está ocupada
   - Ideal: próximo a 100%

## Estrutura do Projeto

```
src/main/java/scheduler/
├── algorithms/
│   ├── SJFScheduler.java
│   └── SRTScheduler.java
├── core/
│   ├── Process.java
│   ├── Scheduler.java
│   └── Tarefa.java
└── ui/
    ├── Main.java
    └── SchedulerApp.java
```

## Limitações Conhecidas

1. A interface gráfica não permite pausar a execução em andamento
2. Não há persistência de dados entre execuções
3. A visualização do gráfico de execução é simplificada

## Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Crie um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes. 
