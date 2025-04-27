# TestVault

Detailed description:  TestVault is a Test Case Management System designed to create and organize test cases efficiently for seamless collaboration between testers,  developers, and managers. Creating, updating, and  deleting test cases  is easy with the user-friendly responsive design from any type of device: laptop, phone, or tablet. Test cases may be organized into modules  and prioritized  for searching. Summarized reports  and alerts on upcoming deadlines are sent to users.

## Live Demo

https://testvault.onrender.com/

```
Users:
 
admin@testvault.com
manager@testvault.com
dev@testvault.com
qa@testvault.com
user@testvault.com
 
Password: 12345
```

## Screenshot
![image](https://github.com/user-attachments/assets/a89e2b57-1c49-4610-9aa7-84940ad4190f)


**Key features:**

1.  Create test cases
2.  Edit test cases
3.  Delete test cases
4.  Organize test cases into modules
5.  Clone test cases
6.  Use test cases across different projects
7.  Attach to test case
    - Screenshots
    - Files
    - Error Logs
8.  Tag test cases
    - Priority
    - Module
    - Functionality
9.  Filter and search test cases by
    - Tags
    - Priority
    - Status
10.  Assign test cases to team members
11.  Role-based access control
    - Admin
    - Manager
    - Developer
    - Quality Assurance (QA)
12.  Comment test cases with discussion threads
13.  Display interactive dashboards with summary statistics
    - Pass/Fail rate
    - Total test cases
14.  Export reports
    - Portable document format (PDF)
    - Comma separated value (CSV)
15.  Alert users for overdue test run  or pending reviews
16.  Remind users with notifications for assigned tasks or deadlines

**Languages**: Java, JavaScript,  Hyper Text Markup Language 5 (HTML5), Cascading Style Sheets 3  (CSS3)
**Framework**: Spring Boot
**Others**: Thymeleaf, jQuery
**Version Control Platform**: GitHub
**Project Management Platform:** Trello
**Wire Framing and Design:** Figma, Draw.io


**Note Deliverable 1:**
[https://github.com/my-unt-projects/TestVault/blob/main/note-deliverable1.txt](https://github.com/my-unt-projects/TestVault/blob/main/note-deliverable1.txt)

**Source Code:**
[https://github.com/my-unt-projects/TestVault/tree/main/src](https://github.com/my-unt-projects/TestVault/tree/main/src)

**Documents such as Planning Documents, meeting minutes, reports:**

[https://github.com/my-unt-projects/TestVault/tree/main/docs](https://github.com/my-unt-projects/TestVault/tree/main/docs)

**Meeting minutes**
[https://github.com/my-unt-projects/TestVault/tree/main/docs/meetings](https://github.com/my-unt-projects/TestVault/tree/main/docs/meetings)

# Compile and Run the Program

## Prerequisites
1. Ensure Java 17 is installed. You can check your Java version by running:
   ```sh
   java -version
   ```
   If Java is not installed, download it from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or use your package manager.

2. Install Docker Desktop from [here](https://www.docker.com/products/docker-desktop/).

## Steps to Run the Application
1. Clone the repository from the main branch:
   ```sh
   git clone https://github.com/my-unt-projects/TestVault.git
   ```

2. Navigate to the project directory:
   ```sh
   cd TestVault
   ```

3. Start MySQL using Docker:
   ```sh
   docker-compose up -d
   ```

4. Compile and run the application:
   ```sh
   ./mvnw spring-boot:run
   ```

5. Run the Spring Boot tests:
   ```sh
   ./mvnw test
   ```

6. Access the application at [http://localhost:8080](http://localhost:8080).
