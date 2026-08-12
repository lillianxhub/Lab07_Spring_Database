# 🎮 Lab 7: Database Connectivity — Game Catalog CRUD

**วิชา:** CP353002 Principles of Software Design  
**เรื่อง:** การเชื่อมต่อฐานข้อมูลด้วย Spring Boot + JPA + JDBC + PostgreSQL  
**รูปแบบ:** ทำเดี่ยว

---

## 📋 วัตถุประสงค์

1. นักศึกษาสามารถสร้าง Spring Boot Project ด้วยตัวเอง และเชื่อมต่อกับฐานข้อมูล PostgreSQL ได้
2. นักศึกษาเข้าใจหลักการ MVC (Model-View-Controller) และการประยุกต์ใช้ **GRASP Patterns** (Controller, Low Coupling, High Cohesion, Information Expert)
3. นักศึกษาเข้าใจและสามารถประยุกต์ใช้ **Design Patterns (Strategy Pattern)** ในการคำนวณราคาสินค้า/ส่วนลดโปรโมชั่น
4. นักศึกษาเข้าใจและสามารถประยุกต์ใช้ **High-level SOLID Principles** (SRP, OCP, LSP, ISP, DIP) ร่วมกับ **Dependency Injection (DI)** ใน Spring Framework
5. นักศึกษาสามารถสร้าง Entity, Repository, Service, Strategy, Controller เพื่อทำ CRUD (Create, Read, Update, Delete) ร่วมกับ Spring Data JPA ได้
6. นักศึกษาสามารถเขียนอธิบายสถาปัตยกรรมซอฟต์แวร์, GRASP Patterns, SOLID Principles, Strategy Pattern และลำดับการทำงาน (Execution Flow) ของระบบได้

---

## 🎯 โจทย์

สร้างเว็บแอปพลิเคชัน **"Game Catalog"** สำหรับจัดการข้อมูลเกม โดยปฏิบัติตามหลักการออกแบบซอฟต์แวร์ (Principles of Software Design), ประยุกต์ใช้ **Strategy Pattern** ในการคำนวณราคา และมีความสามารถ CRUD ครบ 4 ฟังก์ชัน

**สิ่งที่ให้:**

- ไฟล์ Thymeleaf Templates ครบ 4 หน้า (`list.html`, `add.html`, `edit.html`, `delete.html`)
- ไฟล์ CSS สำหรับหน้าเว็บ (`style.css`)
- ไฟล์ `pom.xml` ที่มี dependencies พร้อมใช้งาน

**สิ่งที่นักศึกษาต้องทำเอง:**

- สร้างโปรเจค Spring Boot (`DemoApplication.java` และ Package structure)
- สร้าง **Entity** (Model) ที่ map กับตาราง Database
- สร้าง **Repository** สำหรับเข้าถึงข้อมูล (Data Access Layer)
- สร้าง **Strategy Package** (`DiscountStrategy`, `NoDiscountStrategy`, `StudentDiscountStrategy`, `SeasonalSaleStrategy`, `DiscountContext`) สำหรับการคำนวณราคาโปรโมชั่น (**Strategy Pattern**)
- สร้าง **Service** สำหรับจัดการ Business Logic (Service Layer)
- สร้าง **Controller** ที่ทำ CRUD ครบทุกฟังก์ชัน โดยใช้ **Constructor Injection**
- เขียนอธิบาย **Software Design, GRASP Patterns, SOLID Principles, Strategy Pattern** และ **Execution Flow** ในเล่มรายงาน
- ตั้งค่า **Database Connection** ใน `application.properties`
- ติดตั้งและสร้าง **Database** ใน PostgreSQL

### 📛 การตั้งชื่อโปรเจค

นักศึกษาต้องตั้งชื่อโปรเจค Spring Boot ตามรูปแบบ:

```
lab7-{รหัสนักศึกษา}-sec{หมายเลข section}
```

**ตัวอย่าง:**

| รหัสนักศึกษา | Section | ชื่อโปรเจค              |
| ------------ | ------- | ----------------------- |
| 653380001-1  | 1       | `lab7-653380001-1-sec1` |
| 663380123-4  | 2       | `lab7-663380123-4-sec2` |

> ⚠️ **หมายเหตุ:** ใช้รูปแบบนี้เป็นชื่อโฟลเดอร์โปรเจคและชื่อ Git Repository ด้วย

---

## 📦 สิ่งที่ Thymeleaf Templates ต้องการ

### Entity Fields

Templates อ้างถึง field ต่อไปนี้ — Entity ของนักศึกษาต้องมี field เหล่านี้:

| Field          | Type      | คำอธิบาย                                                           |
| -------------- | --------- | ------------------------------------------------------------------ |
| `id`           | Long      | Primary Key (Auto Generate)                                        |
| `title`        | String    | ชื่อเกม                                                            |
| `genre`        | String    | แนวเกม เช่น Action, RPG, Adventure                                 |
| `platform`     | String    | แพลตฟอร์ม เช่น PC, PS5, Switch                                     |
| `rating`       | Double    | คะแนน (0.0 - 10.0)                                                 |
| `releaseDate`  | LocalDate | วันวางจำหน่าย                                                      |
| `price`        | Double    | ราคาปกติ (บาท)                                                     |
| `discountType` | String    | ประเภทส่วนลด (Strategy Pattern) เช่น `NONE`, `STUDENT`, `SEASONAL` |

### URL Mappings ที่ Templates ใช้

| HTTP Method | URL                  | หน้าที่              | Template            |
| ----------- | -------------------- | -------------------- | ------------------- |
| `GET`       | `/games`             | แสดงรายการเกมทั้งหมด | `games/list`        |
| `GET`       | `/games/add`         | แสดงฟอร์มเพิ่มเกม    | `games/add`         |
| `POST`      | `/games/save`        | บันทึกเกมใหม่        | redirect → `/games` |
| `GET`       | `/games/edit/{id}`   | แสดงฟอร์มแก้ไข       | `games/edit`        |
| `POST`      | `/games/update/{id}` | อัปเดตข้อมูลเกม      | redirect → `/games` |
| `GET`       | `/games/delete/{id}` | แสดงหน้ายืนยันลบ     | `games/delete`      |
| `POST`      | `/games/delete/{id}` | ลบเกม                | redirect → `/games` |

### Model Attributes ที่ Templates ใช้

| Template      | Attribute | Type                | คำอธิบาย                        |
| ------------- | --------- | ------------------- | ------------------------------- |
| `list.html`   | `games`   | `List<Game>`        | รายการเกมทั้งหมด                |
| `list.html`   | `message` | `String` (optional) | ข้อความแจ้งผลสำเร็จ             |
| `add.html`    | `game`    | `Game` (new)        | Object เปล่าสำหรับ form binding |
| `edit.html`   | `game`    | `Game` (existing)   | Object ที่ดึงมาจาก DB           |
| `delete.html` | `game`    | `Game` (existing)   | Object ที่ต้องการลบ             |

---

## 📂 โครงสร้างโปรเจค (ที่สมบูรณ์แล้ว) ← ❌ ให้นักศึกษาสร้างโปรเจคเอง

```
src/main/java/com/example/demo/   ← ❌ นักศึกษาสร้างเอง
├── DemoApplication.java          ← ❌ นักศึกษาสร้างเอง
├── model/
│   └── Game.java                 ← ❌ นักศึกษาสร้างเอง
├── repository/
│   └── GameRepository.java       ← ❌ นักศึกษาสร้างเอง
├── strategy/                     ← ❌ นักศึกษาสร้างเอง (Strategy Pattern)
│   ├── DiscountStrategy.java (interface)
│   ├── NoDiscountStrategy.java (ไม่ลดราคา)
│   ├── StudentDiscountStrategy.java (ลด10%)
│   ├── SeasonalSaleStrategy.java (ลด20%)
│   └── DiscountContext.java (switch ตัวเลือก หน้าที่ strategies)
├── service/
│   └── GameService.java          ← ❌ นักศึกษาสร้างเอง
└── controller/
    └── GameController.java       ← ❌ นักศึกษาสร้างเอง

src/main/resources/
├── application.properties        ← ❌ นักศึกษาตั้งค่า DB เอง
├── static/css/
│   └── style.css                 ← ✅ มีให้แล้ว
└── templates/games/
    ├── list.html                 ← ✅ มีให้แล้ว
    ├── add.html                  ← ✅ มีให้แล้ว
    ├── edit.html                 ← ✅ มีให้แล้ว
    └── delete.html               ← ✅ มีให้แล้ว
```

---

## 🗄️ ฐานข้อมูลและการติดตั้ง PostgreSQL

โปรเจคนี้ใช้ **PostgreSQL** เป็นฐานข้อมูล นักศึกษาสามารถเลือกติดตั้งตามระบบปฏิบัติการที่ใช้งานดังนี้:

---

### 💻 1. การติดตั้งบน Windows

#### วิธีที่ 1: ติดตั้งผ่าน Official Installer (แนะนำสำหรับผู้เริ่มต้น)

1. ดาวน์โหลดโปรแกรมติดตั้งจาก [PostgreSQL Official Download (Windows)](https://www.postgresql.org/download/windows/) (เลือกตัว Installer โดย EnterpriseDB)
2. รันไฟล์ `.exe` แล้วกด **Next** ตามขั้นตอน
3. **กำหนด Password สำหรับ User `postgres`** (⚠️ **สำคัญ:** กรุณาจำหรือจดรหัสผ่านนี้ไว้)
4. กำหนด Port (ค่าเริ่มต้นคือ `5432` หรือตั้งตามต้องการ เช่น `5433`)
5. ติดตั้ง **pgAdmin 4** (ติดมากับตัว Installer) เพื่อใช้จัดการฐานข้อมูลผ่าน GUI

---

### 🍎 2. การติดตั้งบน macOS

#### วิธีที่ 1: ติดตั้งผ่าน Homebrew

1. ติดตั้ง PostgreSQL ด้วยคำสั่ง:
    ```bash
    brew install postgresql@16
    ```
2. เริ่มการทำงานของ PostgreSQL Service:
    ```bash
    brew services start postgresql@16
    ```
3. (Optional) ติดตั้ง [pgAdmin 4](https://www.pgadmin.org/download/pgadmin-4-macos/) หรือ [DBngin](https://dbngin.com/) / [TablePlus](https://tableplus.com/) เพื่อใช้ GUI จัดการฐานข้อมูล

#### วิธีที่ 2: ติดตั้งผ่าน Postgres.app (ใช้งานง่ายด้วย GUI)

1. ดาวน์โหลดโปรแกรมจาก [Postgres.app](https://postgresapp.com/)
2. ลากไฟล์ไปวางในโฟลเดอร์ `Applications` แล้วเปิดโปรแกรม
3. คลิกปุ่ม **Initialize** เพื่อสร้างเซิร์ฟเวอร์ใหม่

---

### 🛠️ 3. การสร้าง Database สำหรับ Lab 7

หลังจากติดตั้งและสั่งให้ PostgreSQL ทำงานเรียบร้อยแล้ว ให้สร้าง Database ชื่อ **`lab7demo`** โดยทำได้ 2 วิธี:

#### วิธี A: ใช้ GUI (pgAdmin / TablePlus / DBeaver)

1. เชื่อมต่อไปยัง PostgreSQL Server (Host: `localhost`, Port: `5432`, User: `postgres`)
2. คลิกขวาที่ **Databases** ➔ เลือก **Create** ➔ **Database...**
3. ตั้งชื่อ Database Name: `lab7demo` แล้วกด **Save**

#### วิธี B: ใช้ Terminal / Command Line (`psql`)

```bash
# เข้าใช้งาน psql ด้วยตัวแปร postgres
psql -U postgres

# คำสั่ง SQL สร้างฐานข้อมูล (อย่าลืมเครื่องหมาย ;)
CREATE DATABASE lab7demo;

# ตรวจสอบรายการฐานข้อมูล
\l

# ออกจาก psql
\q
```

---

### ⚙️ 4. ตัวอย่างการตั้งค่าใน `src/main/resources/application.properties`

เมื่อสร้าง Database เรียบร้อยแล้ว ให้นำข้อมูลการเชื่อมต่อมาใส่ใน `application.properties`:

```properties
spring.application.name=demo

# Database Connection Settings
spring.datasource.url=jdbc:postgresql://localhost:5432/lab7demo
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE

# JPA / Hibernate Settings
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

> ⚠️ **หมายเหตุ:** หากกำหนด Port ตอนติดตั้งเป็น `5433` ให้เปลี่ยน URL เป็น `jdbc:postgresql://localhost:5433/lab7demo`

## 📝 สิ่งที่ต้องส่ง

1. **ลิ้งค์ Git Repository** — Push โปรเจคที่ทำเสร็จขึ้น GitHub/GitLab ส่วนตัว
2. **ไฟล์ PDF เล่มรายงาน** อธิบายขั้นตอนการทำงาน พร้อมแคปภาพประกอบ ดังนี้:
    - **ส่วนที่ 1: Software Design & Principles Explanation (เขียนอธิบาย)**
        - 🧠 **อธิบายสถาปัตยกรรมและ GRASP Patterns:** เขียนอธิบายการแบ่งหน้าที่ของคลาส (Entity, Repository, Service, Controller) ตามหลัก **GRASP Patterns** (เช่น Controller Pattern, High Cohesion, Low Coupling, Information Expert, Indirection)
        - 🎯 **อธิบาย High-Level SOLID Principles:** เขียนอธิบายการประยุกต์ใช้หลักการ SOLID (SRP, OCP, LSP, ISP, DIP) ในระบบ
        - 🧩 **อธิบาย Strategy Pattern:** เขียนอธิบายการประยุกต์ใช้ **Strategy Pattern** ในการคำนวณส่วนลดราคาเกม (`DiscountStrategy`, `NoDiscountStrategy`, `StudentDiscountStrategy`, `SeasonalSaleStrategy`, `DiscountContext`) พร้อมประโยชน์ด้าน Open/Closed Principle (OCP)
        - 🏗️ **อธิบาย Layered Architecture:** เขียนอธิบายว่าทำไมต้องแยก Service Layer ออกจาก Controller และ Repository ประโยชน์ด้าน **Low Coupling** และ **High Cohesion**
        - 🔄 **อธิบาย Execution Flow:** เขียนอธิบายลำดับการทำงาน (Flow) เมื่อมี HTTP Request เข้ามาจาก Browser จนไปถึงการบันทึก/ดึงข้อมูลจาก PostgreSQL และคำนวณส่วนลดผ่าน Strategy Pattern
    - **ส่วนที่ 2: Code Implementation & Explanation**
        - โครงสร้าง Code พร้อมคำอธิบาย (Entity, Repository, Strategy Package, Service, Controller) โดยอธิบายการใช้ **Dependency Injection (Constructor Injection)** ในทุก Layer
    - **ส่วนที่ 3: Web Application & Database Screenshots**
        - 📌 **ข้อกำหนดสำคัญ:** ในขั้นตอนการเพิ่มเกมใหม่ **นักศึกษาต้องใส่รหัสนักศึกษาและ Section ของตนเอง** ลงในข้อมูลเกม (เช่น ในช่องชื่อเกม `Title` หรือแนวเกม `Genre` เช่น `Elden Ring (663380123-4 Sec 1)`)
            ### ตัวอย่างการกรอกข้อมูล
        * **ชื่อเกม (Title):** `Elden Ring (663380123-4 SEC 1)`
        * **แนวเกม (Genre):** `Action RPG`
        * **แพลตฟอร์ม (Platform):** `PC / PS5`
        * **คะแนน (Rating):** `9.8`
        * **ราคาปกติ (บาท):** `1790.00`
        * **ส่วนลด (Strategy):** `ส่วนลดนักศึกษา (10%)` _(ระบบจะคำนวณราคาสุทธิอัตโนมัติเป็น 1,611.00 บาท)_(ให้ถ่ายภาพหน้าจอ)
        * **ส่วนลด (Strategy):** `ส่วนลดเทศกาล (20%)` _(ระบบจะคำนวณราคาสุทธิอัตโนมัติเป็น 1,432.00 บาท)_(ให้ถ่ายภาพหน้าจอ)
        * **วันวางจำหน่าย (Release Date):** `2022-02-25`
        - หน้าจอเพิ่มเกมใหม่ (Create) ที่กำลังกรอกข้อมูลที่มีรหัสนักศึกษา + Section
        - หน้าจอแสดงรายการเกมทั้งหมด (Read) ที่เห็นแถบแจ้งเตือนสีเขียวสำเร็จ และข้อมูลเกมที่มีรหัสนักศึกษาในตาราง
        - หน้าจอแก้ไขเกม (Update) แสดงฟอร์มแก้ไขข้อมูลเกม
        - หน้าจอยืนยันลบ + ผลลัพธ์หลังลบ (Delete)
        - หน้าจอ Database (pgAdmin หรือ terminal `psql`) แสดงข้อมูลจริงในตาราง `games` ที่มีรหัสนักศึกษาบันทึกอยู่

---

<img width="1917" height="732" alt="image" src="https://github.com/user-attachments/assets/e806e760-6c6f-4c1d-bcbf-d6dfc231e7fa" />
ตัวอย่าง http://localhost:8080/games

## 📊 เกณฑ์การให้คะแนน (Software Design Focused)

| หัวข้อ                                 | คะแนน    | รายละเอียด                                                                                                                              |
| -------------------------------------- | -------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| **Software Design Principles & SOLID** | 20%      | เขียนอธิบายหลักการออกแบบซอฟต์แวร์, GRASP Patterns, SOLID Principles (SRP, OCP, LSP, ISP, DIP) และ Layered Architecture ได้ถูกต้องชัดเจน |
| **Strategy Pattern Implementation**    | 15%      | ออกแบบและสร้าง Strategy Pattern ในการคำนวณส่วนลดได้อย่างถูกต้อง สอดคล้องกับหลัก OCP และ DIP                                             |
| **Entity / Model**                     | 10%      | สร้าง Entity ถูกต้องตามหลัก JPA,JBDC และ Object-Relational Mapping (ORM)                                                                |
| **Repository & Data Access**           | 10%      | ออกแบบ Repository Interface และการจัดการ Data Access Layer ถูกต้อง                                                                      |
| **Service Layer**                      | 10%      | สร้าง Service แยก Business Logic ออกจาก Controller เรียกใช้งาน Strategy Context และใช้ Constructor Injection ถูกต้อง                    |
| **Controller & MVC Design**            | 15%      | ควบคุม Flow ด้วย Spring MVC, ใช้ Constructor Injection (DI) เรียกผ่าน Service Layer และทำ CRUD ครบ 4 ฟังก์ชัน                           |
| **Database Connectivity**              | 10%      | เชื่อมต่อ PostgreSQL สำเร็จ ข้อมูลถูกจัดเก็บจริงในฐานข้อมูล                                                                             |
| **PDF Report Quality**                 | 10%      | เล่มรายงานเรียบร้อย อธิบายแนวคิดการออกแบบและมีภาพประกอบครบถ้วน                                                                          |
| **รวม**                                | **100%** |                                                                                                                                         |

---

> **หมายเหตุ:** นักศึกษาสามารถปรับแต่งหน้าเว็บเพิ่มเติมได้ตามต้องการ แต่ฟังก์ชัน CRUD และหลักการออกแบบต้องถูกต้องตามหลักการ Principles of Software Design
