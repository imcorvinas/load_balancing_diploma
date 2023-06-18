package sevsu.loadbalancingdiploma;

public class Variable {
    private int id;
    private final int a; // левая граница среднего терма
    private final int b; // правая граница малого терма
    private final int f; // левая граница большого терма
    private final int d; // правая граница среднего терма
    private final int end; // правая граница области
    double[][] vector_set;

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Variable(int a, int b, int f, int d, int end) {
        this.a = a;
        this.b = b;
        this.f = f;
        this.d = d;
        this.end = end;
        fuzzyfication();
    }

    public int getEnd() {
        return this.end;
    }

    private double function_1(int x) {
        if (x <= this.a) return 1;
        if (x > this.a && x < this.b) return (double)(this.b-x)/(this.b-this.a);
        if (x >= this.b) return 0;
        return -1;
    }

    private double function_2(int x) {
        if (x <= this.a || x >= this.d) return 0;
        if (x > this.a && x < this.b) return (double)(x-this.a)/(this.b-this.a);
        if (x >= this.b && x <= this.f) return 1;
        if (x >= this.f && x <= this.d) return (double)(this.d-x)/(this.d-this.f);
        return -1;
    }

    private double function_3(int x) {
        if (x <= this.f) return 0;
        if (x > this.f && x < this.d) return (double)(x-this.f)/(this.d-this.f);
        if (x >= this.d) return 1;
        return -1;
    }

    private void fuzzyfication() {
        this.vector_set = new double[3][this.end];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.end; j++) {
                if (i == 0) this.vector_set[i][j] = function_1(j);
                if (i == 1) this.vector_set[i][j] = function_2(j);
                if (i == 2) this.vector_set[i][j] = function_3(j);
            }
        }
    }
}

