import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class LibraryCatalogFX extends Application {

    private final ArrayList<Book> catalog = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Catalog System");

        // Buttons
        Button addBookButton = new Button("Add a Book");
        Button displayCatalogButton = new Button("Display Catalog");
        Button searchBookButton = new Button("Search for a Book");
        Button deleteBookButton = new Button("Delete a Book");
        Button exitButton = new Button("Exit");

        // Button actions
        addBookButton.setOnAction(e -> addBook());
        displayCatalogButton.setOnAction(e -> displayCatalog());
        searchBookButton.setOnAction(e -> searchBook());
        deleteBookButton.setOnAction(e -> deleteBook());
        exitButton.setOnAction(e -> primaryStage.close());

        VBox vbox = new VBox(10, addBookButton, displayCatalogButton, searchBookButton, deleteBookButton, exitButton);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBook() {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add a Book");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        titleField.setPromptText("Title");
        authorField.setPromptText("Author");

        VBox content = new VBox(10, new Label("Title:"), titleField, new Label("Author:"), authorField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Book(titleField.getText().trim(), authorField.getText().trim());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(book -> {
            if (!book.getTitle().isEmpty() && !book.getAuthor().isEmpty()) {
                catalog.add(book);
                showAlert("Book added successfully!");
            } else {
                showAlert("Title and Author cannot be empty.");
            }
        });
    }

    private void displayCatalog() {
        Stage stage = new Stage();
        stage.setTitle("Library Catalog");

        TableView<Book> tableView = new TableView<>();
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");

        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        titleCol.setMinWidth(150);
        authorCol.setMinWidth(150);

        tableView.getColumns().addAll(titleCol, authorCol);
        tableView.getItems().addAll(catalog);

        VBox vbox = new VBox(tableView);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void searchBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search for a Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title or keyword:");
        String keyword = dialog.showAndWait().orElse("").toLowerCase();

        if (keyword.isEmpty()) {
            showAlert("Search term cannot be empty.");
            return;
        }

        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : catalog) {
            if (book.getTitle().toLowerCase().contains(keyword)) {
                foundBooks.add(book);
            }
        }

        if (!foundBooks.isEmpty()) {
            StringBuilder result = new StringBuilder("Found Books:\n");
            for (Book book : foundBooks) {
                result.append(book).append("\n");
            }
            showAlert(result.toString());
        } else {
            showAlert("No books found matching the keyword.");
        }
    }

    private void deleteBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete a Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to delete:");
        String title = dialog.showAndWait().orElse("").toLowerCase();

        Book toRemove = null;
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                toRemove = book;
                break;
            }
        }

        if (toRemove != null) {
            catalog.remove(toRemove);
            showAlert("Book removed successfully.");
        } else {
            showAlert("Book not found.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Library Catalog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
