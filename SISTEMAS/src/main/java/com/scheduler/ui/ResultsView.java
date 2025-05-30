package com.scheduler.ui;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import com.scheduler.core.ProcessResult;
import java.util.List;

public class ResultsView extends VBox {
    private TableView<ProcessResult> resultsTable;
    private BarChart<String, Number> resultsChart;

    public ResultsView() {
        setupTable();
        setupChart();
        this.setSpacing(10);
        this.getChildren().addAll(resultsTable, resultsChart);
    }

    private void setupTable() {
        resultsTable = new TableView<>();
        
        TableColumn<ProcessResult, String> nameCol = new TableColumn<>("Processo");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("processName"));
        
        TableColumn<ProcessResult, Integer> waitingCol = new TableColumn<>("Tempo de Espera");
        waitingCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        
        TableColumn<ProcessResult, Integer> turnaroundCol = new TableColumn<>("Tempo de Turnaround");
        turnaroundCol.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        
        TableColumn<ProcessResult, Integer> completionCol = new TableColumn<>("Tempo de Conclusão");
        completionCol.setCellValueFactory(new PropertyValueFactory<>("completionTime"));

        resultsTable.getColumns().addAll(nameCol, waitingCol, turnaroundCol, completionCol);
        resultsTable.setMaxHeight(200);
    }

    private void setupChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Processos");
        yAxis.setLabel("Tempo");
        
        resultsChart = new BarChart<>(xAxis, yAxis);
        resultsChart.setTitle("Métricas por Processo");
        resultsChart.setAnimated(false);
    }

    public void updateResults(List<ProcessResult> results) {
        resultsTable.getItems().clear();
        resultsTable.getItems().addAll(results);
        
        // Atualiza o gráfico
        XYChart.Series<String, Number> waitingSeries = new XYChart.Series<>();
        waitingSeries.setName("Tempo de Espera");
        
        XYChart.Series<String, Number> turnaroundSeries = new XYChart.Series<>();
        turnaroundSeries.setName("Tempo de Turnaround");
        
        for (ProcessResult result : results) {
            String processName = result.getProcessName();
            waitingSeries.getData().add(new XYChart.Data<>(processName, result.getWaitingTime()));
            turnaroundSeries.getData().add(new XYChart.Data<>(processName, result.getTurnaroundTime()));
        }
        
        resultsChart.getData().clear();
        resultsChart.getData().addAll(waitingSeries, turnaroundSeries);
        
        // Calcula e exibe médias
        double avgWaiting = results.stream()
            .mapToDouble(ProcessResult::getWaitingTime)
            .average()
            .orElse(0.0);
            
        double avgTurnaround = results.stream()
            .mapToDouble(ProcessResult::getTurnaroundTime)
            .average()
            .orElse(0.0);
            
        // Adiciona série para médias
        XYChart.Series<String, Number> avgSeries = new XYChart.Series<>();
        avgSeries.setName("Médias");
        avgSeries.getData().add(new XYChart.Data<>("Média Espera", avgWaiting));
        avgSeries.getData().add(new XYChart.Data<>("Média Turnaround", avgTurnaround));
        
        resultsChart.getData().add(avgSeries);
    }
} 