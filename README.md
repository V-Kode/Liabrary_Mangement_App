📚 Library Management App
Java Swing + JDBC + SQLite | Semester 3 Project (B.Tech CSE )

The Library Management App is a modern GUI-based desktop application built using:

Java Swing (Frontend UI)
JDBC (Database Connectivity)
SQLite (Local Database Storage)
DAO Pattern (Clean DB Operations)
Maven (Build Tool)
This project allows users to Add, View, Edit, Delete, Issue, and Return books through a professional, premium UI.

🚀 Features
✅ Add Books
Add a new book with:

Title
Author
Copies
Issue status
✅ View Books
Displays all books in a clean, modern, blue-themed table view.

✅ Edit Book
Update book details with a dedicated dialog.

✅ Delete Book
Remove any book with a confirmation prompt.

✅ Issue / Return Book
Manage availability status with a single click.

🔎 Real-Time Search
Search live by ID, title, or author.

🎨 Premium GUI
Gradient blue header
Modern sidebar
Rounded buttons
Light/Dark theme toggle
FlatLaf modern UI
Enhanced table look with striping
🛠️ Tech Stack
Component	Technology
UI	Java Swing + FlatLaf
Backend Logic	Core Java
Database	SQLite
Pattern Used	DAO (Data Access Object)
Build Tool	Maven
IDE	IntelliJ IDEA
📁 Project Folder Structure
LIBRARY-MANAGEMENT-SYSTEM/
│
├── src/main/java/org/example/
│   ├── Main.java
│   ├── model/
│   │   └── Book.java
│   ├── dao/
│   │   └── BookDAO.java
│   ├── util/
│   │   └── DbUtil.java
│   └── ui/
│       ├── ModernDashboardFrame.java
│       └── BookDialog.java
│
├── src/main/resources/
│   └── (DB + config)
│
└── pom.xml
🗄️ Database Schema (SQLite)
CREATE TABLE books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    copies INTEGER DEFAULT 1,
    issued INTEGER DEFAULT 0
);
⚙️ Backend Status — Completed ✔
The complete backend + UI of the Library Management System is fully implemented and stable.

✔ Completed Modules:
Add Book
View Book
Update Book
Delete Book
Issue / Return Book
DAO Layer for DB Operations
SQLite JDBC Connectivity
MVC Architecture
Exception Handling
Search + Filters
Modern Premium UI Integration
The system is fully functional, visually polished, and ready for submission.

▶️ How to Run This Project
1. Install Requirements
Java 17+
IntelliJ IDEA
Maven
(No need to install SQLite separately — file auto-creates)
2. Clone the repository
git clone https://github.com/your-username/library-management-system.git
cd library-management-system
3. Build the project
mvn clean package
4. Run the application
mvn exec:java -Dexec.mainClass="org.example.Main"
Or run Main.java directly from IntelliJ.

📄 License
This project is created for academic purposes at Galgotias University.

✨ Thank You for Checking Out the Project!
Feel free to explore, fork, or improve the repository.
