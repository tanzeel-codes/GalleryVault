# GalleryVault

A Java-based console application that integrates with **gallery-dl** to download media from multiple platforms while maintaining download history, statistics, and configurable application settings.

GalleryVault was built as a personal utility as well as a portfolio project to demonstrate clean Java architecture, object-oriented design, file handling, and modern Java programming practices.

---

## Features

### 📥 Media Downloads

* Download media using **gallery-dl**
* Supports multiple platforms
* Handles successful and failed downloads
* Stores download metadata automatically

### 📜 Download History

* View complete download history
* View successful downloads
* View failed downloads
* Filter downloads by platform
* Sort history (Newest First / Oldest First)
* Clear download history

### 📊 Statistics

* Total downloads
* Successful downloads
* Failed downloads
* Platform-wise download statistics

### ⚙️ Configuration

* Configure application settings
* Persistent configuration storage

---

## Technologies Used

* Java 21
* Maven
* ProcessBuilder
* Java Collections Framework
* EnumMap
* Predicate (Functional Interfaces)
* LocalDateTime API
* Java NIO (Files, Paths)
* CSV File Storage
* Object-Oriented Programming (OOP)

---

## Project Structure

```text
src/main/java
│
├── app
│   └── Application entry point
│
├── config
│   └── Application configuration
│
├── download
│   ├── Download manager
│   ├── Console UI
│   └── Download logic
│
├── history
│   ├── History manager
│   ├── History console
│   ├── Download record
│   └── History utilities
│
├── platform
│   └── Supported platforms
│
└── statistics
    ├── Statistics manager
    ├── Statistics console
    └── Statistics record
```

---

## Project Architecture

The project follows a layered architecture to keep responsibilities separated.

```text
Console Layer
      │
      ▼
Manager Layer
      │
      ▼
Model / Record Layer
```

Example:

```text
HistoryConsole
        │
        ▼
HistoryManager
        │
        ▼
DownloadRecord
```

This separation makes the application easier to maintain, extend, and test.

---

## Design Principles Used

* Separation of Concerns
* Single Responsibility Principle (SRP)
* Dependency Injection (Constructor Injection)
* Composition over Duplication
* Functional Programming using Predicate
* Enum-based Design
* Immutable Record Models
* Modular Package Structure

---

## Data Storage

GalleryVault stores application data inside the user's home directory.

```text
~/.gallery-vault/
```

The download history is stored as a CSV file, making it lightweight and easy to inspect.

---

## How to Run

### Prerequisites

* Java 21 or later
* Maven
* gallery-dl installed and available in PATH

### Clone the repository

```bash
git clone https://github.com/tanzeel-codes/GalleryVault.git
cd GalleryVault
```

### Build

```bash
mvn clean package
```

### Run

```bash
mvn exec:java
```

or execute the generated JAR.

---

## Screenshots

> Screenshots will be added after the console interface is finalized.

Example sections to include later:

* Main Menu
* Download Menu
* History
* Statistics
* Configuration

---

## Future Improvements

* Search download history
* Automatic history backup
* SQLite database support
* JSON export/import
* Download queue
* Multi-threaded downloads
* JavaFX GUI version
* Unit tests (JUnit)
* Logging support
* Plugin-based downloader architecture

---

## What I Learned

Building GalleryVault helped me gain practical experience with:

* Java Collections
* File Handling
* CSV Processing
* ProcessBuilder
* Functional Interfaces
* Java Time API
* Clean Code Practices
* Object-Oriented Design
* Package Organization
* Application Architecture
* Git and GitHub workflow

---

## Author

**Tanzeel Akhtar**

GitHub: https://github.com/tanzeel-codes

---

## License

This project is intended for educational and personal use.
