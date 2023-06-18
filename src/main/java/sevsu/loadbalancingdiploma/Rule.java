package sevsu.loadbalancingdiploma;

public class Rule {
    private final int id;
    private final static String RULE_FORMAT_STRING = "Если интенсивность трафика %s и степень заполнения очереди %s, то время обработки - %s";
    private final String in_term_1;
    private final String in_term_2;
    private final String out_term_1;
    public Rule (int id, String term1, String term2, String term3) {
        this.id = id;
        this.in_term_1 = term1;
        this.in_term_2 = term2;
        this.out_term_1 = term3;
    }

    public String getIn_term_1() {
        return in_term_1;
    }

    public String getIn_term_2() {
        return in_term_2;
    }

    public String getOut_term_1() {
        return out_term_1;
    }

    public int getId() { return id; }

    public String toString() {return String.format(RULE_FORMAT_STRING,in_term_1,in_term_2,out_term_1);}
}

