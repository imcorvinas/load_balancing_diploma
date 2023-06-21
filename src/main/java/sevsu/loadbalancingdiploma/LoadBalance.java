package sevsu.loadbalancingdiploma;

public class LoadBalance {
    private double process_time;
    private String route_numbers;
    public LoadBalance (double process_time) {
        this.process_time = process_time;
        doLoadBalancing();
    }
    public int getProcess_time() {
        int integer_process_time;
        integer_process_time = (int) process_time;
        return integer_process_time;
    }
    public String getRoute_numbers() {
        return route_numbers;
    }

    public void doLoadBalancing() {
        if (this.process_time <= 250) {
            this.route_numbers = "Требуется один маршрут для балансировки траффика.";
        } else if (this.process_time <= 500) {
            this.route_numbers = "Требуется два маршрута для балансировки траффика.";
        } else if (this.process_time <= 700) {
            this.route_numbers = "Требуется три маршрута для балансировки траффика.";
        } else this.route_numbers = "Требуется четыре маршрута для балансировки траффика.";
        if (this.process_time == 0) this.route_numbers = "Ошибка балансировки, время обработки равно нулю";
    }
}

