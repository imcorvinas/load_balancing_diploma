module sevsu.loadbalancingdiploma {
    requires javafx.controls;
    requires javafx.fxml;


    opens sevsu.loadbalancingdiploma to javafx.fxml;
    exports sevsu.loadbalancingdiploma;
}