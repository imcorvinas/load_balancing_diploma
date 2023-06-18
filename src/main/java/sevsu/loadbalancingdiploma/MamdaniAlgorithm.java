package sevsu.loadbalancingdiploma;

import javafx.collections.ObservableList;
import java.util.Objects;

public class MamdaniAlgorithm {
    private Variable[] variables;
    private ObservableList<Rule> rules;
    private int x1;
    private int x2;
    private double[] aggregations;
    private double[][] activizations;
    private double[] accumulations;
    private double output_value;

    public MamdaniAlgorithm (Variable[] variables, ObservableList<Rule> rules) {
        this.variables = variables;
        this.rules = rules;
        this.output_value = 0;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setOutput_value(double output_value) {
        this.output_value = output_value;
    }

    public double[] getAggregations() {
        return aggregations;
    }

    public double[][] getActivizations() {
        return activizations;
    }

    public double[] getAccumulations() {
        return accumulations;
    }

    public double getOutput_value() {
        return output_value;
    }

    public void aggregation() {
        aggregations = new double[rules.size()];
        double x = -1, y = -1;
        int i = 0;
        for (Rule rules1: rules) {
            x = 0; y = 0;
            if (Objects.equals(rules1.getIn_term_1(), "малая")) {
                x = variables[0].vector_set[0][this.x1];
            }
            if (Objects.equals(rules1.getIn_term_1(), "средняя")) {
                x = variables[0].vector_set[1][this.x1];;
            }
            if (Objects.equals(rules1.getIn_term_1(), "высокая")) {
                x = variables[0].vector_set[2][this.x1];;
            }
            if (Objects.equals(rules1.getIn_term_2(), "малая")) {
                y = variables[1].vector_set[0][this.x2];;
            }
            if (Objects.equals(rules1.getIn_term_2(), "средняя")) {
                y = variables[1].vector_set[1][this.x2];;
            }
            if (Objects.equals(rules1.getIn_term_2(), "высокая")) {
                y = variables[1].vector_set[2][this.x2];;
            }
            //System.out.printf("%.2f %.2f", x, y);
            aggregations[i] = Math.min(x, y);
            //System.out.printf(" Минимум - %.2f", aggregations[i]);
            //System.out.println();
            i++;
        }
    }
    public void activization () {
        activizations = new double[rules.size()][variables[2].getEnd()];
        int i = 0; //i - номер правила
        for (Rule rules1: rules) {
            for (int j = 0; j < variables[2].getEnd(); j++) {
                if (Objects.equals(rules1.getOut_term_1(), "малое")) activizations[i][j] = Math.min(aggregations[i],variables[2].vector_set[0][j]);
                if (Objects.equals(rules1.getOut_term_1(), "среднее")) activizations[i][j] = Math.min(aggregations[i],variables[2].vector_set[1][j]);
                if (Objects.equals(rules1.getOut_term_1(), "высокое")) activizations[i][j] = Math.min(aggregations[i],variables[2].vector_set[2][j]);
            }
            i++;
        }
    }
    public void accumulation () {
        accumulations = new double[variables[2].getEnd()];
        for (int j = 0; j < activizations[0].length; j++) {
            double max = 0;
            for (double[] activization : activizations) {
                if (activization[j] > max) max = activization[j];
            }
            accumulations[j] = max;
        }
    }
    public double defuzzyfication () {
        double value1 = 0, value2 = 0;
        for (int i = 0; i < accumulations.length; i++) {
            value1+=i*accumulations[i];
            value2+=accumulations[i];
        }
        return value1/value2;
    }
    public void execute () {
        aggregation();
        activization();
        accumulation();
        this.output_value = defuzzyfication();
    }
}

