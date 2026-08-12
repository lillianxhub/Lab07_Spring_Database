# 📖 คู่มือคำแนะนำการทำ Lab 7: เพิ่มข้อมูลเกม, Strategy Pattern, SOLID & บันทึกภาพหน้าจอรายงาน

> **สำหรับนักศึกษา:** คู่มือนี้สรุปขั้นตอนการทดสอบระบบ Game Catalog, การประยุกต์ใช้ **Strategy Pattern & SOLID Principles**, การเพิ่มข้อมูลเกมด้วย **รหัสนักศึกษา และ Section**, และการแคปภาพหน้าจอใส่ในเล่มรายงาน PDF ให้ครบถ้วนตามเกณฑ์การประเมิน

---

## 1. 📝 ข้อมูลเกมตัวอย่างสำหรับทดสอบ (ต้องระบุ รหัสนักศึกษา + Section)

ในการทดสอบเพิ่มข้อมูลเกม ให้กรอกข้อมูลที่มี **รหัสนักศึกษา** และ **Section** ของนักศึกษาเองลงในช่อง **ชื่อเกม (Title)** หรือ **แนวเกม (Genre)** และทดสอบเลือกรูปแบบส่วนลด (**Discount Strategy**)

---

## 2. 🧩 การประยุกต์ใช้ Strategy Pattern & SOLID Principles

### 1. Strategy Pattern (การคำนวณส่วนลดราคาเกม)

โปรเจคนี้ใช้ Strategy Pattern ในการคำนวณราคาเกมตามประเภทส่วนลด:

- **Interface:** `DiscountStrategy`
- **Concrete Strategies:**
    - `NoDiscountStrategy` (ราคาปกติ 0%)
    - `StudentDiscountStrategy` (ส่วนลดนักศึกษา 10%)
    - `SeasonalSaleStrategy` (ส่วนลดเทศกาล 20%)
- **Context:** `DiscountContext` ทำหน้าที่สวิตช์เลือก Strategy ที่เหมาะสมโดยอัตโนมัติ

### 2. High-Level SOLID Principles

- **S - Single Responsibility Principle (SRP):** แยกหน้าที่ชัดเจน Controller (HTTP/View), Service (Business Logic), Strategy (คำนวณราคา), Repository (Database)
- **O - Open/Closed Principle (OCP):** หากต้องการเพิ่มส่วนลดรูปแบบใหม่ (เช่น VIP 30%) สามารถสร้างคลาสใหม่ที่ implement `DiscountStrategy` ได้ทันที โดยไม่ต้องแก้ไขโค้ดเดิมใน `GameService`
- **L - Liskov Substitution Principle (LSP):** ทุกคลาสใน Strategy Package สามารถใช้แทน `DiscountStrategy` ได้อย่างสมบูรณ์
- **I - Interface Segregation Principle (ISP):** `DiscountStrategy` มีเฉพาะ method เท่าที่จำเป็น
- **D - Dependency Inversion Principle (DIP):** `GameService` พึ่งพา Abstraction (`DiscountStrategy`, `DiscountContext`) ผ่าน Constructor Injection

---

## 3. 🚀 ขั้นตอนการเปิดใช้งานและทดสอบระบบ

### Step 1: ตรวจสอบ PostgreSQL Database

1. ตรวจสอบให้แน่ใจว่า PostgreSQL Server เปิดใช้งานอยู่
2. สร้าง Database ชื่อ **`lab7demo`**
3. ตรวจสอบพอร์ตใน `src/main/resources/application.properties` (เช่น `5432` หรือ `5433`) ให้ตรงกับเครื่องของนักศึกษา

### Step 2: รันโปรเจค Spring Boot

รันโปรเจคด้วยคำสั่งใน Terminal:

```bash
./mvnw spring-boot:run
```

_(หรือคลิกปุ่ม **Run / Debug** ในไฟล์ `DemoApplication.java` ผ่าน IDE)_

### Step 3: เปิดเบราว์เซอร์เข้าใช้งาน

- เข้าไปยัง URL: `http://localhost:8080/games`

---

## 4. 📸 รายการภาพถ่ายหน้าจอที่ต้องแนบลงในเล่มรายงาน (Screenshot Checklist)

ให้นักศึกษาบันทึกภาพหน้าจอขณะทดสอบระบบทั้ง **6 หน้าสำคัญ** ดังต่อไปนี้ เพื่อนำไปใส่ในรายงานส่วนที่ 3:

| ลำดับ | หน้าจอที่ต้องแคป                               | URL / คำสั่ง                            | รายละเอียดที่ต้องเห็นในภาพ                                                                 |
| :---: | :--------------------------------------------- | :-------------------------------------- | :----------------------------------------------------------------------------------------- |
| **1** | **หน้าฟอร์มเพิ่มเกม (Create Form)**            | `http://localhost:8080/games/add`       | เห็นข้อมูลที่กำลังกรอก **ที่มีรหัสนักศึกษา + Section** และเลือก Strategy ส่วนลด            |
| **2** | **หน้าแสดงรายการเกม (Read/List)**              | `http://localhost:8080/games`           | เห็นแจ้งเตือนสีเขียว (Success Alert), ชื่อส่วนลด และราคาสุทธิที่คำนวณผ่าน Strategy Pattern |
| **3** | **หน้าฟอร์มแก้ไขเกม (Update Form)**            | `http://localhost:8080/games/edit/{id}` | เห็นฟอร์มแก้ไขที่มีข้อมูลเดิมดึงมาจากฐานข้อมูล                                             |
| **4** | **หน้ารายการหลังแก้ไขสำเร็จ (List Updated)**   | `http://localhost:8080/games`           | เห็นตารางรายการเกมที่มีข้อมูลที่ได้รับการอัปเดตแล้ว                                        |
| **5** | **หน้ารายการหลังลบเกมสำเร็จ (Delete Success)** | `http://localhost:8080/games`           | เห็นข้อความแจ้งเตือนลบสำเร็จ และข้อมูลเกมถูกลบออกจากตาราง                                  |
| **6** | **หน้าจอ PostgreSQL Database**                 | pgAdmin หรือ terminal (`psql`)          | เห็นผลลัพธ์คำสั่ง `SELECT * FROM games;` ที่แสดงข้อมูลจริงในฐานข้อมูล                      |

---

## 5. 📄 ตัวอย่างโครงสร้างการจัดวางรูปภาพในเล่มรายงาน PDF

```markdown
## ส่วนที่ 3: Web Application & Database Screenshots

### 3.1 หน้าจอการเพิ่มเกมใหม่ (Create)

[ วางภาพถ่ายหน้าจอ /games/add ที่กรอกรหัสนักศึกษา ]
รูปที่ 3.1: หน้าฟอร์มเพิ่มเกมใหม่ (ระบุรหัสนักศึกษา 663380123-4 Section 1 และเลือกส่วนลดนักศึกษา 10%และ20%)

### 3.2 หน้าจอแสดงรายการเกมทั้งหมด (Read)

[ วางภาพถ่ายหน้าจอ /games แสดงตารางรายการเกม ]
รูปที่ 3.2: หน้าแสดงรายการเกมทั้งหมด พร้อมแสดงราคาสุทธิที่คำนวณผ่าน Strategy Pattern

### 3.3 หน้าจอแก้ไขข้อมูลเกม (Update)

[ วางภาพถ่ายหน้าจอ /games/edit/1 ]
รูปที่ 3.3: หน้าฟอร์มแก้ไขข้อมูลเกม

### 3.4 หน้าจอยืนยันและการลบข้อมูลเกม (Delete)

[ วางภาพถ่ายหน้าจอ /games/delete/1 และหน้า /games หลังลบสำเร็จ ]
รูปที่ 3.4: หน้าจอยืนยันการลบเกม และผลลัพธ์หลังทำการลบ

### 3.5 หน้าจอตรวจสอบข้อมูลใน PostgreSQL Database

[ วางภาพถ่ายหน้าจอ pgAdmin หรือ Terminal psql ]
รูปที่ 3.5: ข้อมูลตาราง games ที่ถูกจัดเก็บจริงในฐานข้อมูล PostgreSQL
```

<img width="1550" height="653" alt="Screenshot 2026-08-04 at 13 36 25" src="https://github.com/user-attachments/assets/2d900a42-590f-41fd-9bd8-bf227ddf1184" />
