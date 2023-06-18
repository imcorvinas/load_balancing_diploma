package sevsu.loadbalancingdiploma;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class LoadBalancingController {

    Variable[] arr_variable = new Variable[3];
    @FXML
    private TableView table;
    @FXML
    private ChoiceBox<String> addTerm1Field;
    @FXML
    private ChoiceBox<String> addTerm2Field;
    @FXML
    private ChoiceBox<String> addTerm3Field;
    private final String[] in_terms = {"малая","средняя","высокая"};
    private final String[] out_terms = {"малое","среднее","высокое"};
    @FXML
    private TextField deleteRuleByIdField;
    @FXML
    private TextField getA1Field;
    @FXML
    private TextField getA2Field;
    @FXML
    private TextField getA3Field;
    @FXML
    private TextField getB1Field;
    @FXML
    private TextField getB2Field;
    @FXML
    private TextField getB3Field;
    @FXML
    private TextField getF1Field;
    @FXML
    private TextField getF2Field;
    @FXML
    private TextField getF3Field;
    @FXML
    private TextField getD1Field;
    @FXML
    private TextField getD2Field;
    @FXML
    private TextField getD3Field;
    @FXML
    private TextField getEnd1Field;
    @FXML
    private TextField getEnd2Field;
    @FXML
    private TextField getEnd3Field;
    @FXML
    private TextField getX1Field;
    @FXML
    private TextField getX2Field;
    @FXML
    private TextArea loadBalanceResultArea;

    @FXML
    public void initialize() {

        //столбец id
        TableColumn<Rule, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idColumn.setMaxWidth(50);

        //столбец интенсивности трафика
        TableColumn<Rule, String> trafficColumn = new TableColumn<>("Интенсивность трафика");
        trafficColumn.setCellValueFactory(new PropertyValueFactory<>("in_term_1"));
        trafficColumn.setMinWidth(200);

        //столбец степени заполнения очереди
        TableColumn<Rule, String> queueColumn = new TableColumn<>("Степень заполнения очереди");
        queueColumn.setCellValueFactory(new PropertyValueFactory<>("in_term_2"));
        queueColumn.setMinWidth(220);

        //столбец времени обработки
        TableColumn<Rule, String> timeColumn = new TableColumn<>("Время обработки");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("out_term_1"));
        timeColumn.setMinWidth(140);

        table.getColumns().addAll(idColumn, trafficColumn,queueColumn,timeColumn);

        addTerm1Field.getItems().addAll(in_terms);
        addTerm2Field.getItems().addAll(in_terms);
        addTerm3Field.getItems().addAll(out_terms);
    }

    // добавить запись в таблицу
    public void addRule(){
        // добавляем запись
        table.getItems().add(new Rule(table.getItems().size() + 1, addTerm1Field.getValue(), addTerm2Field.getValue(), addTerm3Field.getValue()));
        table.refresh();
    }

    // удалить запись по id
    public void deleteRuleById(){
        ObservableList<Rule> all = table.getItems();
        for (Rule rl: all) {
            if(rl.getId() == Integer.parseInt(deleteRuleByIdField.getText())){
                all.remove(rl);
                deleteRuleByIdField.clear();
                return;
            }
        }
    }

    // сохранить правила в файл
    public void saveRules(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение правил");
        File file = fileChooser.showSaveDialog(new Stage());
        ObservableList<Rule> rules = table.getItems();
        PrintWriter out = null;
        try{

            out = new PrintWriter(new FileWriter(file.getAbsoluteFile()));
            for (Rule rule:rules) {
                StringBuilder line = new StringBuilder();
                line.append(String.format("%s %s %s %s",rule.getId(), rule.getIn_term_1(), rule.getIn_term_2(), rule.getOut_term_1()));
                out.println(line.toString());
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        finally {
            if(out != null) out.close();
        }

    }

    //загрузить таблицу в файл
    public void openRules(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузка правил");
        File file = fileChooser.showOpenDialog(new Stage());
        ObservableList<Rule> rules = table.getItems();
        rules.removeAll();

        String line;
        BufferedReader inp = null;
        String[] data = new String[4];


        try{
            inp = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            while ((line = inp.readLine()) != null){
                line = line.trim();
                if(line.equals("")) continue;
                data = line.split("\\s+");
                rules.add(new Rule(Integer.parseInt(data[0]),data[1], data[2],data[3]));
            }
        } catch (Exception e){
            System.out.println("Error - " + e.getMessage());
        }
    }

    //инициализировать переменные
    @FXML
    protected void initializeVariables() {
        arr_variable[0] = new Variable(Integer.parseInt(getA1Field.getText()), Integer.parseInt(getB1Field.getText()), Integer.parseInt(getF1Field.getText()), Integer.parseInt(getD1Field.getText()), Integer.parseInt(getEnd1Field.getText()));
        arr_variable[1] = new Variable(Integer.parseInt(getA2Field.getText()), Integer.parseInt(getB2Field.getText()), Integer.parseInt(getF2Field.getText()), Integer.parseInt(getD2Field.getText()), Integer.parseInt(getEnd2Field.getText()));
        arr_variable[2] = new Variable(Integer.parseInt(getA3Field.getText()), Integer.parseInt(getB3Field.getText()), Integer.parseInt(getF3Field.getText()), Integer.parseInt(getD3Field.getText()), Integer.parseInt(getEnd3Field.getText()));
    }

    //выполнить алгоритм Мамдани
    @FXML
    protected void executeMamdani() {
        MamdaniAlgorithm Task = new MamdaniAlgorithm(arr_variable, table.getItems());
        Task.setX1(Integer.parseInt(getX1Field.getText()));
        Task.setX2(Integer.parseInt(getX2Field.getText()));
        Task.execute();
        LoadBalance lb = new LoadBalance(Task.getOutput_value());
        loadBalanceResultArea.setText("Время обработки трафика: " + lb.getProcess_time() + "\n" + lb.getRoute_numbers());
    }
}