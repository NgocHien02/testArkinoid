module org.example.gamearkanoid {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires jdk.compiler;
//    requires org.example.gamearkanoid;

    opens org.example.gamearkanoid to javafx.fxml;
    exports org.example.gamearkanoid;
}
