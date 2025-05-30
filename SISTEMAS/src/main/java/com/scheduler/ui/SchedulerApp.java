package com.scheduler.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.scheduler.core.Process;
import com.scheduler.core.ProcessResult;
import com.scheduler.core.Scheduler;
import com.scheduler.core.SJFScheduler;
import com.scheduler.core.SRTScheduler;
import java.util.ArrayList;
import java.util.List;

public class SchedulerApp extends Application {
    private ComboBox<String> algorithmComboBox;
    private TextField processNameField;
    private TextField arrivalTimeField;
    private TextField burstTimeField;
    private TableView<Process> processTable;
    private ResultsView resultsView;
    private List<Process> processes;
    private Scheduler currentScheduler;

    @Override
    public void start(Stage primaryStage) {
        processes = new ArrayList<>();
        
        // Layout principal
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Seção de entrada
        GridPane inputGrid = createInputSection();
        
        // Tabela de processos
        processTable = createProcessTable();
        
        // Visualização de resultados
        resultsView = new ResultsView();
        
        // Botões
        HBox buttonBox = createButtonBox();

        mainLayout.getChildren().addAll(
            inputGrid,
            new Separator(),
            processTable,
            buttonBox,
            new Separator(),
            resultsView
        );

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Escalonador de Processos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createInputSection() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);

        // ComboBox para algoritmos
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll("SJF", "SRT");
        algorithmComboBox.setValue("SJF");

        // Campos de texto
        processNameField = new TextField();
        arrivalTimeField = new TextField();
        burstTimeField = new TextField();

        // Labels
        grid.add(new Label("Algoritmo:"), 0, 0);
        grid.add(algorithmComboBox, 1, 0);
        grid.add(new Label("Nome do Processo:"), 0, 1);
        grid.add(processNameField, 1, 1);
        grid.add(new Label("Tempo de Chegada:"), 0, 2);
        grid.add(arrivalTimeField, 1, 2);
        grid.add(new Label("Tempo de Execução:"), 0, 3);
        grid.add(burstTimeField, 1, 3);

        return grid;
    }

    private TableView<Process> createProcessTable() {
        TableView<Process> table = new TableView<>();
        
        TableColumn<Process, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Process, Number> arrivalCol = new TableColumn<>("Chegada");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        
        TableColumn<Process, Number> burstCol = new TableColumn<>("Execução");
        burstCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        table.getColumns().addAll(nameCol, arrivalCol, burstCol);
        return table;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);

        Button addButton = new Button("Adicionar Processo");
        addButton.setOnAction(e -> addProcess());

        Button executeButton = new Button("Executar");
        executeButton.setOnAction(e -> executeScheduler());

        Button clearButton = new Button("Limpar");
        clearButton.setOnAction(e -> clearAll());

        buttonBox.getChildren().addAll(addButton, executeButton, clearButton);
        return buttonBox;
    }

    private void addProcess() {
        try {
            String name = processNameField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());

            if (name.isEmpty() || arrivalTime < 0 || burstTime <= 0) {
                showError("Valores inválidos");
                return;
            }

            Process process = new Process(name, arrivalTime, burstTime);
            processes.add(process);
            processTable.getItems().add(process);

            // Limpa os campos
            processNameField.clear();
            arrivalTimeField.clear();
            burstTimeField.clear();

        } catch (NumberFormatException e) {
            showError("Por favor, insira números válidos para os tempos");
        }
    }

    private void executeScheduler() {
        if (processes.isEmpty()) {
            showError("Adicione pelo menos um processo");
            return;
        }

        // Cria o escalonador apropriado
        currentScheduler = algorithmComboBox.getValue().equals("SJF") ?
            new SJFScheduler() : new SRTScheduler();

        // Executa o escalonamento
        List<ProcessResult> results = currentScheduler.schedule(new ArrayList<>(processes));
        
        // Atualiza a visualização dos resultados
        resultsView.updateResults(results);
    }

    private void clearAll() {
        processes.clear();
        processTable.getItems().clear();
        processNameField.clear();
        arrivalTimeField.clear();
        burstTimeField.clear();
        resultsView.updateResults(new ArrayList<>());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 