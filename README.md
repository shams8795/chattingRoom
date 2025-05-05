
# Chatting Room

A simple chat application built with **Java**, using **JavaFX** for the User Interface and **MySQL** for the backend database.

## 📌 Features

- User registration (Sign Up)
- User login
- Real-time chat between clients
- Server and Client architecture
- JavaFX UI with FXML and CSS styling
- Image backgrounds for a modern look
- Database integration for user management

## 📦 Project Structure

```
chattingRoom/
└── demo11/
    ├── pom.xml
    ├── src/
    │   ├── main/
    │   │   ├── java/com/example/ged/
    │   │   │   ├── ChatApp.java
    │   │   │   ├── ChatApp2.java
    │   │   │   ├── Client.java
    │   │   │   ├── Server.java
    │   │   │   ├── DatabaseConnection.java
    │   │   │   └── DataBaseManager.java
    │   │   └── resources/
    │   │       └── com/example/demo11/
    │   │           ├── hello-view.fxml
    │   │           ├── SignUp-view.fxml
    │   │           ├── style.css
    │   │           └── images...
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL Database

### How to Run

1. Clone the repository:

```bash
git clone https://github.com/yourusername/chattingRoom.git
```

2. Open in your favorite IDE (IntelliJ IDEA recommended).

3. Set up your database:
   - Create a MySQL database.
   - Update `DatabaseConnection.java` with your DB credentials.

4. Build and run:

```bash
mvn clean install
mvn javafx:run
```

5. Start the Server:

```bash
Run Server.java
```

6. Run Clients:

```bash
Run Client.java or ChatApp.java
```

### 📌 Notes

- Ensure the server is running before launching clients.
- Customize database credentials in `DatabaseConnection.java`.
- The app uses JavaFX 19.

## 📄 License

This project is for educational purposes only.
