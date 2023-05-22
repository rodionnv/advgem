module nazarrod.adventgem.advgem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
//    requires org.junit.jupiter.api;

    exports nazarrod.adventgem.advgem;
    opens nazarrod.adventgem.advgem to javafx.fxml;
    exports nazarrod.adventgem.advgem.utils;
    opens nazarrod.adventgem.advgem.utils to javafx.fxml;
    exports nazarrod.adventgem.advgem.editor;
    opens nazarrod.adventgem.advgem.editor to javafx.fxml;
    exports nazarrod.adventgem.advgem.levelPlayer;
    opens nazarrod.adventgem.advgem.levelPlayer to javafx.fxml;
}