# 📚 Library Catalog System (JavaFX)

A simple and interactive **Library Catalog System** built using **JavaFX**. This desktop GUI application allows users to:

- 📖 Add new books
- 🔍 Search for books (case-insensitive, partial match)
- 🗂️ Display the full catalog in a TableView
- ❌ Delete books
- 🚪 Exit the application

---

## 🛠️ Features

| Feature           | Description                                                                 |
|-------------------|-----------------------------------------------------------------------------|
| Add Book          | Input title and author using a custom dialog.                               |
| Display Catalog   | View all books in a JavaFX `TableView` for clear readability.               |
| Search Book       | Search by title or keyword (case-insensitive, partial match supported).     |
| Delete Book       | Remove a book by entering its title (case-insensitive).                     |
| Clean UI          | User-friendly layout using `VBox` and spacing.                              |

---

## 📂 Project Structure

```plaintext
src/
└── com.example.demo1/
    ├── LibraryCatalogFX.java   # Main JavaFX application
    └── Book.java               # Book model class
