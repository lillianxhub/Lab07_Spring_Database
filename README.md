# 🎮 Lab 7: Game Catalog CRUD Application

**ชื่อ-นามสกุล:** เพชรภิญโญ ธนศิรินรากร  
**รหัสนักศึกษา:** 673380073-7  
**Section:** 2  
**วิชา:** CP353002 Principles of Software Design

**Document** [Lab 7: Database Connectivity — Game Catalog CRUD](https://docs.google.com/document/d/19CPMPfn8ZG6tyiOJGqi0Wt3XnwkT3BZ4lHYdWE5GshQ/edit?usp=sharing)

---

## 📋 ภาพรวมโปรเจค

เว็บแอปพลิเคชัน **Game Catalog** สำหรับจัดการข้อมูลเกม ออกแบบตามหลักการ **GRASP Patterns**, **SOLID Principles** และ **Strategy Pattern** เชื่อมต่อกับ **PostgreSQL** ผ่าน **Spring Boot + JPA**

### 🎯 ฟีเจอร์หลัก

- ✅ **Create** — เพิ่มเกมใหม่
- ✅ **Read** — แสดงรายการเกมทั้งหมด พร้อมราคาสุทธิที่คำนวณผ่าน Strategy Pattern
- ✅ **Update** — แก้ไขข้อมูลเกม
- ✅ **Delete** — ลบเกม
- ✅ **Strategy Pattern** — คำนวณส่วนลด 3 รูปแบบ (ไม่ลด, นักศึกษา 10%, เทศกาล 20%)

---

## 🏗️ สถาปัตยกรรม

### Layered Architecture

```
┌─────────────────────────────────────┐
│   Presentation Layer (Controller)   │  → HTTP Request/Response
├─────────────────────────────────────┤
│   Business Logic Layer (Service)    │  → Strategy Pattern, Business Rules
├─────────────────────────────────────┤
│   Data Access Layer (Repository)    │  → JPA CRUD Operations
├─────────────────────────────────────┤
│   Database Layer (PostgreSQL)       │  → Data Storage
└─────────────────────────────────────┘
```

### โครงสร้างโปรเจค

```
src/main/java/com/example/demo/
├── DemoApplication.java
├── model/
│   └── Game.java                      (Entity)
├── repository/
│   └── GameRepository.java            (JPA Repository)
├── strategy/                          (Strategy Pattern)
│   ├── DiscountStrategy.java          (Interface)
│   ├── NoDiscountStrategy.java        (0%)
│   ├── StudentDiscountStrategy.java   (10%)
│   ├── SeasonalSaleStrategy.java      (20%)
│   └── DiscountContext.java           (Context)
├── service/
│   └── GameService.java               (Business Logic)
└── controller/
    └── GameController.java            (MVC Controller)

src/main/resources/
├── application.properties
├── static/css/
│   └── style.css
└── templates/games/
    ├── list.html
    ├── add.html
    ├── edit.html
    └── delete.html
```

---

## 💻 เทคโนโลยีที่ใช้

| Technology      | Version | Purpose               |
| --------------- | ------- | --------------------- |
| Java            | 17+     | Programming Language  |
| Spring Boot     | 3.x     | Framework             |
| Spring Data JPA | 3.x     | ORM / Database Access |
| PostgreSQL      | 16+     | Relational Database   |
| Thymeleaf       | 3.x     | Template Engine       |
| Maven           | 3.x     | Build Tool            |
| Bootstrap       | 5.3     | CSS Framework         |

---

## 🚀 การติดตั้งและรัน

### 1. ข้อกำหนดเบื้องต้น

- ✅ Java 17 หรือสูงกว่า
- ✅ PostgreSQL 16 หรือสูงกว่า
- ✅ Maven 3.x

### 2. ติดตั้ง PostgreSQL

**Windows:**

```bash
# ดาวน์โหลดจาก https://www.postgresql.org/download/windows/
# ติดตั้ง pgAdmin 4 พร้อมกัน
```

**macOS:**

```bash
brew install postgresql@16
brew services start postgresql@16
```

### 3. สร้าง Database

```bash
# เข้า psql
psql -U postgres

# สร้าง database
CREATE DATABASE lab7demo;

# ออกจาก psql
\q
```

### 4. ตั้งค่า Environment Variable

[`application.properties`](src/main/resources/application.properties)

```
spring.datasource.password=your_password_here
```

### 5. รันโปรเจค

```bash
# Clone repository (ถ้ามี)
git clone <repository-url>
cd Lab07-673380073-7-sec2/src

# รัน Spring Boot
./mvnw spring-boot:run
```

### 6. เปิดเบราว์เซอร์

```
http://localhost:8080/games
```

---

## 📦 ฐานข้อมูล

### ตาราง `games`

| Column          | Type         | Description                            |
| --------------- | ------------ | -------------------------------------- |
| `id`            | BIGINT       | Primary Key (Auto-increment)           |
| `title`         | VARCHAR(255) | ชื่อเกม                                |
| `genre`         | VARCHAR(255) | แนวเกม (Action, RPG, Adventure)        |
| `platform`      | VARCHAR(255) | แพลตฟอร์ม (PC, PS5, Switch)            |
| `rating`        | DOUBLE       | คะแนน (0.0 - 10.0)                     |
| `release_date`  | DATE         | วันวางจำหน่าย                          |
| `price`         | DOUBLE       | ราคาปกติ (บาท)                         |
| `discount_type` | VARCHAR(50)  | ประเภทส่วนลด (NONE, STUDENT, SEASONAL) |

---

## 🎨 หลักการออกแบบที่ใช้

### 1. GRASP Patterns

- ✅ **Controller Pattern** — `GameController` เป็นตัวกลางระหว่าง View และ Service
- ✅ **Information Expert** — `GameService` มีข้อมูลและความรู้ในการคำนวณราคา
- ✅ **Low Coupling** — Controller ไม่รู้จัก Repository โดยตรง
- ✅ **High Cohesion** — แต่ละคลาสมีหน้าที่ชัดเจน
- ✅ **Indirection** — `DiscountContext` เป็นตัวกลางในการเลือก Strategy

### 2. SOLID Principles

- ✅ **Single Responsibility (SRP)** — แต่ละคลาสมีหน้าที่เดียว
- ✅ **Open/Closed (OCP)** — เพิ่ม Strategy ใหม่ได้โดยไม่แก้โค้ดเดิม
- ✅ **Liskov Substitution (LSP)** — Strategy ทุกตัวแทนที่กันได้
- ✅ **Interface Segregation (ISP)** — Interface กะทัดรัด
- ✅ **Dependency Inversion (DIP)** — ใช้ Constructor Injection ทุก Layer

### 3. Strategy Pattern

```
DiscountStrategy (Interface)
    ├── NoDiscountStrategy      (0%)
    ├── StudentDiscountStrategy (10%)
    ├── SeasonalSaleStrategy    (20%)
    ├── DiscountContext → เลือก Strategy ที่เหมาะสม
    └── GameService → เรียกใช้ผ่าน Context
```

**ประโยชน์:**

- เพิ่มส่วนลดรูปแบบใหม่ได้ง่าย (OCP)
- แยก algorithm ออกเป็นคลาสย่อย
- ทดสอบแต่ละ Strategy แยกกันได้

---

## 🔄 Execution Flow

### ตัวอย่าง: เพิ่มเกมใหม่

```
1. Browser → POST /games/save (Form Data)
2. GameController.saveGame()
3. GameService.saveGame()
4. GameService.calculateFinalPrice()
5. DiscountContext.getStrategy("STUDENT")
6. StudentDiscountStrategy.calculatePrice(1790.0)
7. Return 1611.0 (ลด 10%)
8. GameRepository.save(game)
9. PostgreSQL → INSERT INTO games...
10. Redirect → GET /games
11. GameService.getAllGamesWithFinalPrice()
12. Thymeleaf Template → แสดงรายการ + Success Message
```

---

## 🧪 การทดสอบ

### ข้อมูลตัวอย่างสำหรับทดสอบ

**ข้อมูลเริ่มต้น (Create):**

| Field            | Value                                  |
| ---------------- | -------------------------------------- |
| **Title**        | `673380073-7 SEC 2`                    |
| **Genre**        | `Action Code`                          |
| **Platform**     | `PC`                                   |
| **Rating**       | `10`                                   |
| **Price**        | `9999.00`                              |
| **Discount**     | `STUDENT` (10%) → ราคาสุทธิ: 8999.10 ฿ |
| **Release Date** | `02/02/2222`                           |

**ข้อมูลหลังแก้ไข (Update):**

| Field            | Value                                   |
| ---------------- | --------------------------------------- |
| **Title**        | `673380073-7 SEC 2 naja`                |
| **Genre**        | `Action Code RPG Open World`            |
| **Platform**     | `PC CONSOLE TERMINAL`                   |
| **Rating**       | `10`                                    |
| **Price**        | `9999.00`                               |
| **Discount**     | `SEASONAL` (20%) → ราคาสุทธิ: 7999.20 ฿ |
| **Release Date** | `02/02/2222`                            |

### ทดสอบ Strategy Pattern

| Discount Type | Original Price | Final Price | Calculation |
| ------------- | -------------- | ----------- | ----------- |
| NONE          | 9999.00 ฿      | 9999.00 ฿   | 9999 × 1.0  |
| STUDENT       | 9999.00 ฿      | 8999.10 ฿   | 9999 × 0.9  |
| SEASONAL      | 9999.00 ฿      | 7999.20 ฿   | 9999 × 0.8  |

---

## 📸 Screenshots

ดูภาพหน้าจอทั้งหมดได้ที่: [`report/screenshots/`](report/screenshots/)

1. **1.png** — หน้าฟอร์มเพิ่มเกมใหม่
2. **2.png** — รายการเกม (ส่วนลดนักศึกษา 10%)
3. **3.png** — รายการเกม (ส่วนลดเทศกาล 20%)
4. **updated_data_form.png** — หน้าฟอร์มแก้ไข
5. **6.updated.png** — รายการหลังแก้ไขสำเร็จ
6. **delete_form.png** — หน้ายืนยันลบ
7. **6.deleted.png** — รายการหลังลบสำเร็จ
8. **6.db.png** — ข้อมูลจริงใน PostgreSQL

---

## 📝 เอกสารเพิ่มเติม

- 📄 **รายงานฉบับสมบูรณ์:** [`report/Lab7_Report.md`](report/Lab7_Report.md)
- 📄 **รายงานผล Lab:** [`Lab7_report.docx`]()
- 📚 **คู่มือนักศึกษา:** [`STUDENT_GUIDE.md`](STUDENT_GUIDE.md)

---

## 🎓 ผู้จัดทำ

**เพชรภิญโญ ธนศิรินรากร**  
รหัสนักศึกษา: 673380073-7  
Section: 2

---

## 📊 สรุปการประยุกต์ใช้หลักการออกแบบ

| Principle              | Implementation          | Benefit       |
| ---------------------- | ----------------------- | ------------- |
| **Controller Pattern** | GameController          | Low Coupling  |
| **Information Expert** | GameService             | High Cohesion |
| **SRP**                | แต่ละคลาสมีหน้าที่เดียว | ชัดเจน        |
| **OCP**                | เพิ่ม Strategy ได้ง่าย  | ขยายได้       |
| **DIP**                | Constructor Injection   | ทดสอบง่าย     |
| **Strategy Pattern**   | Discount Calculation    | ยืดหยุ่น      |

---

## 🏆 คะแนนที่คาดว่าจะได้

| หัวข้อ                       | คะแนน    | สถานะ           |
| ---------------------------- | -------- | --------------- |
| Software Design & Principles | 20%      | ✅ เสร็จสมบูรณ์ |
| Strategy Pattern             | 15%      | ✅ เสร็จสมบูรณ์ |
| Entity/Model                 | 10%      | ✅ เสร็จสมบูรณ์ |
| Repository                   | 10%      | ✅ เสร็จสมบูรณ์ |
| Service Layer                | 10%      | ✅ เสร็จสมบูรณ์ |
| Controller & MVC             | 15%      | ✅ เสร็จสมบูรณ์ |
| Database Connectivity        | 10%      | ✅ เสร็จสมบูรณ์ |
| PDF Report                   | 10%      | ✅ เสร็จสมบูรณ์ |
| **รวม**                      | **100%** | ✅              |

---

## 📚 เทคโนโลยีและแนวคิดที่ใช้

- ✅ Spring Boot — Rapid Application Development
- ✅ Spring Data JPA — ORM และ Repository Pattern
- ✅ PostgreSQL — Relational Database
- ✅ Thymeleaf — Server-side Template Engine
- ✅ Strategy Pattern — Behavioral Design Pattern
- ✅ GRASP Patterns — Object-Oriented Design Principles
- ✅ SOLID Principles — Clean Code Principles
- ✅ MVC Architecture — Separation of Concerns
- ✅ Dependency Injection — Inversion of Control

---

**© 2026 — Lab 7: Game Catalog CRUD Application**
