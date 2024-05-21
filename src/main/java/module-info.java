module com.omurefeguclu.puzzlegamehw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.omurefeguclu.puzzlegamehw to javafx.fxml;
    exports com.omurefeguclu.puzzlegamehw;
}