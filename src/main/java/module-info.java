module com.enrollment {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.enrollment to javafx.fxml;
    exports com.enrollment;
}
