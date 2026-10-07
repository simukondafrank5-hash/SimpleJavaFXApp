import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomerManagerApp extends Application {

    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();
    private TextField nameField;
    private ComboBox<String> provinceBox;
    private Label status;
    private TableView<Customer> table;

    @Override
    public void start(Stage stage) {
        Label title = new Label("Customer Manager");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        Label nameLabel = new Label("Customer name");
        nameLabel.setLabelFor(nameField);

        provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Central", "Copperbelt", "Eastern",
                "Luapula", "Lusaka", "Muchinga", "Northern",
                "North-Western", "Southern", "Western");
        provinceBox.setPromptText("Choose a province");
        Label provinceLabel = new Label("Province");
        provinceLabel.setLabelFor(provinceBox);

        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true);
        saveButton.setOnAction(e -> saveCustomer());

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(provinceLabel, 0, 1);
        form.add(provinceBox, 1, 1);
        form.add(saveButton, 1, 2);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        provinceBox.setMaxWidth(Double.MAX_VALUE);

        table = new TableView<>();
        table.setItems(customers);
        table.setPlaceholder(new Label("No customers saved yet."));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(220);
        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provinceCol.setPrefWidth(160);
        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);

        Button deleteButton = new Button("Delete selected");
        deleteButton.setOnAction(e -> deleteSelected());
        status = new Label();
        HBox bottom = new HBox(15, deleteButton, status);

        VBox root = new VBox(12, title, form, table, bottom);
        root.setPadding(new Insets(15));
        VBox.setVgrow(table, Priority.ALWAYS);

        stage.setTitle("Customer Manager");
        stage.setScene(new Scene(root, 480, 520));
        stage.show();
        nameField.requestFocus();
    }

    private void saveCustomer() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            status.setText("Enter the customer name.");
            nameField.requestFocus();
            return;
        }
        String province = provinceBox.getValue();
        if (province == null) {
            status.setText("Choose a province.");
            provinceBox.requestFocus();
            return;
        }
        customers.add(new Customer(name, province));
        status.setText("Customer saved.");
        nameField.clear();
        provinceBox.setValue(null);
        nameField.requestFocus();
    }

    private void deleteSelected() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            status.setText("Select a customer to delete.");
            table.requestFocus();
            return;
        }
        ButtonType delete = new ButtonType("Delete");
        Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete the selected customer?", delete, ButtonType.CANCEL);
        ask.setHeaderText("Confirm deletion");
        if (ask.showAndWait().orElse(ButtonType.CANCEL) == delete) {
            customers.remove(selected);
            status.setText("Customer deleted.");
        } else {
            status.setText("Deletion cancelled.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}