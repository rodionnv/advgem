module nazarrod.adventgem.advgem {
    requires javafx.controls;
    requires javafx.fxml;


    opens nazarrod.adventgem.advgem to javafx.fxml;
    exports nazarrod.adventgem.advgem;
}