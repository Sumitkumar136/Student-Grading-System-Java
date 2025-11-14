# 📘 **Student Grade Tracker – Java Swing Project**

A modern and user-friendly **Student Grade Management System** developed using **Java Swing**.
This application allows administrators to **add students, enter grades, view reports, and visualize statistics**, all inside a beautiful dashboard UI with gradient themes.

---

## ✨ **Features**

### 🔐 **Login System**

* Admin login
* Gradient background design
* Auto-focus and Enter-key support

---

### 🧑‍🎓 **Add Student**

* Add new student with fields:

  * Full Name
  * Phone Number
  * Email
  * Course selection
  * Year
* Auto-generated **Roll Number** using course code
* Checks **duplicate phone numbers** using CSV file
* Saves data into `students.csv`

---

### 📝 **Enter Grades**

* Add subject-wise grades
* Load student data
* Save all marks in CSV
  *(Panel included in project structure)*

---

### 📄 **View Reports**

* Table view of saved students
* View individual or complete reports
* Clean UI with scroll support

---

### 📊 **Statistics Module**

* Select course
* View overall stats table
* Shows per-subject statistics like:

  * Highest Marks
  * Lowest Marks
  * Average
  * Pass/Fail count

---

### 🖥️ **Dashboard UI**

* Attractive gradient background
* Sidebar navigation
* Rounded modern buttons
* Dynamic content switching using `contentArea`

---

## 🏗️ **Project Structure**

```
📂 Student-Grade-Tracker
│
├── Dashboard.java
├── LoginPage.java
├── AddStudentPanel.java
├── EnterGradePanel.java
├── ViewReportPanel.java
├── StatisticsPanel.java
│
├── students.csv        # Auto-created after saving students
├── grades.csv          # Marks storage (optional)
│
├── user.png            # Optional icons
└── lock.png
```

---

## 🔧 **Technologies Used**

* **Java**
* **Java Swing (UI)**
* **AWT**
* **CSV File Handling**
* **OOP Concepts**

---

## ▶️ **How to Run the Project**

### **1. Download or Clone the Repository**

```bash
git clone https://github.com/yourusername/Student-Grade-Tracker.git
```

### **2. Open in any IDE**

* NetBeans
* IntelliJ IDEA
* Eclipse
* VS Code (Java Extension)

### **3. Run the Project**

Run the main file:

```bash
javac LoginPage.java
java LoginPage
```

Or simply click **Run** from IDE.

---

## 💾 **Database**

Project uses **CSV files** as storage:

* `students.csv` → stores student details
* `grades.csv` → stores subject marks

No external database required.

---

## 🎨 **UI Highlights**

* Modern gradient design
* Rounded stylish buttons
* Clean spacing & responsive layout
* Easy navigation through sidebar

---

## 👨‍💻 **Developer**

**Sumit Kumar**
BCA 3rd Year • Java Developer • Full Stack Learner

If you like this project, ⭐ **Star the repository** on GitHub!

---

## 📜 **License**

This project is licensed under the **MIT License**.
You can use and modify it freely.

