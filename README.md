# AWS Gallery

Cloud-native application for uploading, analyzing, and searching images using **AWS S3** and **AWS Rekognition**.

## Features
* **Smart Upload:** Uploads images directly to AWS S3 Bucket.
* **AI Analysis:** Automatically detects objects (Labels) using AWS Rekognition.
* **Search:** Find images by content (e.g., "Car", "Animal") without manual tagging.
* **Storage:** Metadata stored in MySQL.

## Tech Stack
* **Java 17**
* **Spring Boot 3.2** (Web, Thymeleaf, Data JPA)
* **AWS SDK v2** (S3, Rekognition)
* **MySQL**
* **Docker**

## How to Run

### Prerequisites
* JDK 17
* MySQL Database
* AWS Account (Access Key & Secret Key) with S3/Rekognition access.

### Environment Variables
You must set these variables before running:
```bash
AWS_ACCESS_KEY=key;
AWS_BUCKET=your-bucket;
AWS_REGION_STATIC=your-region;
AWS_SECRET_KEY=your-secret-key;
DB_HOST=db_host;
DB_NAME=your_db;
DB_PASSWORD=db_pass;
DB_PORT=3306 (or your specific one);
DB_USER=user