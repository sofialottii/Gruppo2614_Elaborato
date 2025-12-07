# 🍦 Ice-cream Shop Management System

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![it](https://img.shields.io/badge/lang-it-green.svg)](README.it.md)

*Read this in [Italian](README.it.md).*

This application manages staff and operations for a gelato shop. Follow the instructions below to configure the database and launch the project.

## 🛠️ Setup and Run

### 1. Database Setup
Before running the application, you need to prepare the MySQL database:

1.  **Create the structure**: Copy the content of the `PROGETTO_DATABASE_GELATERIA.ddl` file and execute it in your MySQL client (e.g., Workbench, DBeaver) or command line.
2.  **Populate data**: Execute the queries found in `queries_popolazioneDB.sql` to insert initial data (employees, products, etc.).

### 2. Launching the Application
1.  Open the project folder with your preferred editor (e.g., **Visual Studio Code**).
2.  Open a new terminal in the project root.
3.  Run the start command using Gradle:

    ```bash
    ./gradlew run
    ```

---

## 🔐 Login Credentials

Use the following credentials to test the various application functionalities.

### ✅ Active Employees (Valid Login)
These accounts are active and grant access to the system.

| Employee Code | Password |
| :--- | :--- |
| **D001** | 11111 |
| **D002** | 22222 |
| **D003** | 33333 |
| **D007** | 77777 |
| **D008** | 88888 |

### ❌ Terminated Employees (Access Denied Test)
Use these credentials to verify that the system **blocks** login attempts for former employees.


| Employee Code | Password | Notes |
| :--- | :--- | :--- |
| **D004** | 44444 | Terminated |
| **D005** | 55555 | Terminated |
| **D006** | 66666 | Terminated |


### 📸 Screenshots

<table>
  <tr>
    <td align="center">
      <b>Home Screen</b><br>
      <img src="screenshots/home.png" width="400">
    </td>
    <td align="center">
      <b>Admin Dashboard</b><br>
      <img src="screenshots/adminHome.png" width="400">
    </td>
  </tr>
  <tr>
    <td align="center">
      <b>Customer Registration</b><br>
      <img src="screenshots/iscrizioneCliente.png" width="400">
    </td>
    <td align="center">
      <b>New Order</b><br>
      <img src="screenshots/ordine.png" width="400">
    </td>
  </tr>
  <tr>
    <td align="center">
      <b>Shift Management</b><br>
      <img src="screenshots/turni.png" width="400">
    </td>
    <td align="center">
      <b>Flavor Recipes (Doses)</b><br>
      <img src="screenshots/dosiGusto.png" width="400">
    </td>
  </tr>
</table>
