**1. Overall Structure**
**Diagram of the system structure**

![Untitled Diagram drawio](https://github.com/user-attachments/assets/b6588614-c004-4644-84b7-c7e5bf765fac)

![Untitled diagram-2025-02-22-045417](https://github.com/user-attachments/assets/f7b8bb0a-de4b-419d-8bee-dbc73887799e)

![Untitled diagram-2025-02-22-054852](https://github.com/user-attachments/assets/24aa9ea7-3979-4d97-b3c2-ef4d95e7518f)

**Description of the system structure**

**System Structure of TestVault**
1. Overview
Test Vault helps users create test cases quickly then run them efficiently without missing key organizational aspects. The solution permits all test stakeholders to work effectively through a step-by-step approach for handling test cases.
 
Our project requires an architectural diagram built on this system description.
•​Descriptions of all major components and subsystems.
 
2. System Components
The system divides its operations into different units of work that connect with one another to enhance test case tracking and running.
A. User Roles & Access Control (RBAC)
The platform grants role-based permission access through RBAC for different user position groups.
* Roles Included:
* Administrator – Manages user access, configurations, and system settings.
* The QA staff develops test cases then adapts and runs them in QA system functions.
* The developer checks the tests given to them and responds with their feedback.
* The manager oversees test activities while checking performance results.
* Users access the system through Frontend UI by logging in and working according to their assigned job functions.
 
B. Frontend (User Interface)
The frontend gives direct system access to all users.
* Developed Using: JavaScript, HTML5, CSS3, Thymeleaf.
* Key Features:
* The system allows users to make changes to their test case records through its Create Read Update Delete actions also known as CRUD.
* Role-based Access Management
* Notifications & Alerts
* Interactive Dashboard for Statistics
* The Frontend part of the system sends instructions to the Backend API which handles the task requests.
C. Backend API
Companies develop the system using Spring Boot (Java) to run it as their central processing unit.
* Responsibilities:
* Our solution controls every step of the test handling process from start to finish.
* The backend handles all API communications that come from the user interface of the application.
* Manages authentication and authorization for role-based access control.
* Handles data processing and interactions with the database.
* The backend system connects to both UI, database, testing unit and external integration features.
 
D. Database (MySQL)
* The system database has a regular design with test case actions data.
* Data Stored Includes:
* Test Case Information (Title, Description, Tags, Priority, Status).
* The system keeps track of test runs with their outcome results plus time needed and user assignments.
* User Roles & Permissions (Admin, QA, Developer, Manager).
* Attachments & Logs (Screenshots, Error Reports, Files).
* The Backend API gets and saves records to the database.
 
E. Reporting & Analytics
The reporting module enables stakeholders to view data visualization results and requested data reports.
•Key Features:
You can download test results as PDF CSV and Excel files from this system.
o​Displays pass/fail trends of test cases.
o​Shows summary dashboards with key performance metrics.
The system fetches database data through the backend API to show users what they need.

F. External Integrations
The system links to other applications to speed up test case management operations.

•Connected Services:
* The GitHub platform links with commit functions to manage version changes and test case information records.
* Trello connects with tasks to follow assigned test cases through their work progression.
* The system sends automatic email alerts to notify users about test cases awaiting action plus late tasks and failed test tests.
* Through its Backend API the system links to external systems to run smooth integration.
 
3. System Workflow & Data Flow
* The system serves test cases to each user after successful authentication.
* Users submit test case details and the information reaches the Back End API.
* The system backend handles requests by making updates to the database.
* Test Case Engine runs planned tests while data goes into the Database for results logging.
* Users create reports by accessing stored Database data.
* The system delivers notifications when test cases become due or produce failure results.
* Data from Email systems automatically updates to our platform.

**2. Written Requirements Specifications**

**2a. Functional requirements**

List of Features
1. Create test case
2. Edit test cases
3. Delete test cases
4. Assign test case to modules
5. Clone test cases
6. Assign test case to multiple projects
7. Attachments to test case
8. Tag test cases
9. Filter and search test cases
10. Assign test cases to team members
11. Role-based access control
12. Comment test cases
13. Dashboards
14. Export Report
15. Notify Users: overdue test run or pending reviews
16. Notify users: assigned tasks or deadlines

**Feature List Additions or Changes:**

1. View test case
2. View all test cases
3. Notify users
   spans Features 15 & 16
    either this should be broken into four features or kept as one feature for organization sake?
4. Assign/Tag test cases
   spans Features 4, 6, 8, 10
   how are these different?

**Functional Requirements Table**
| Feature Number | Feature Name | Description | Questions | Priority |
| --------------- | --------------- | --------------- |--------------- |--------------- |
|1| Create test cases| The user will create a new test case. The user will push a create button which will display a test case input form with certain fields for the user to input data (see below). Once the user is finished inputing data, then the user pressed a save button. The test case is viewable. </br></br>Fields include:</br>module</br>project</br>test run date</br>review date |1) Would the view of all test cases be another feature?</br>2) Would the view of a singular test case be a feature?</br>3) Is the test case an input form or is it an option for the user to upload a txt file?</br>4) If it is an input form, does the user have the option to add or change the fields or is it preset?</br>5)How is the test case named?</br> 6)Does the user select or input the name of the test case, or is it an automatically assigned number?</br>7))Is view test case a separate feature?</br>8)Are the fields correct?|High|
|2| Edit test cases |The user will be able to edit an existing test case. Editing of test cases will occur one at a time. The User will be able to search for a specific test case, select it for viewing and then the user will press an edit button. The fields of the test case are prepopulated with the existing information which was previously entered, but can be edited by the user. Once finished editting, the user will then click on a save button to save the test case.|1) Is the edit test case view just the create view but with prepopulated information?</br>2)Will there be a select test case button in the view of test cases or an edit test case button in the view of test cases so the user can go straight to editting?</br>3)Is there a button to cancel the edits made and revert to the original?|Medium
|3|Delete test cases| The user will be able to delete test cases. The user will search for a specific test case, select the case for deletion and then select a delete button. Once deleted there is no retrieval of the test case.|1) Similair to the edit feature, will there be a delete button next to the test cases in the view test cases display or will the the delete button be in a single test case view? </br>Is there a way to select and delete multiple cases?|Medium
|4| Organize test cases into modules|The user will be able to assign a test case to a module. The user will select a module for a test case to be assigned to.|1)Does a project need to be created prior to creation of a test case?</br>Does a module need to be created prior to creation of a project?</br>Could there be a default module and project group so the user can create a test case without assigning a project or module to it?</br>Is create module and create project on the "home page" along with reporting notification and assignment functions?</br>Is create module and create project another feature?</br>Are there drop downs in create and edit test cases which are prepopulated with existing modules and projects that the user can select?|Medium
|5| Clone test cases|The user will be able to clone a test case. The user will select a test case in the view test cases display and select a clone button. This action creates a new test case with the same data for the fields of the original test case.|1)Does the user name the new test case? If so, is this a popup window that asks the user to name the teset case?Or does the new test case have a name which is a version of the original test case?|Low
6|Use test cases across different projects|The user can assign a test case to multiple projects. In the create or edit test case view, the drop down assigning a test case to a project allows multiple selections of projects.|Is this how it is?|Low
7|Attachments to test case|The user is able to attach files  to a test case. The files can be x,y,z format. |1)What file formats are attached?</br>2)Where is the user able to attach the file? Is it in the create test case form?</br>3) Is there an option to add an attachment next to a test case in the display of all test cases?</br>4) Is there a button to add an attachment in the edit test case view?</br>5)Is there an icon that shows a test case has an attachment in the view display of all test casesIs there an icon at the bottom of a test case in the view test case display?</br>6)Can the user open the attachment?</br>7/Can the user download the attachment?|Low
|8| Tag test cases| The user can assign test cases certain tags. These include priority, module, and functionality. In the create and edit view there are drop downs with prepopulated values the users can select for the test case.</br>Field Values</br>Priority: Low, Medium, High, Critical</br>Module:?</br>Functionality: To Do, In progress, Done|1)Are they drop downs, or should they be like tags displayed that the user can toggle?2)Can the user assign tags in the view all test cases display?3)What are the prepopulated values for module?4)Are the other prepopulated values correct?|Low
|9| Filter and search test cases|The user can search and filter test cases by any of the following meta information:</br>priority</br>project</br>module</br>functionality </br>name|1)Is this option on the home screen or navigation menu?</br> 2)Does this have its own screen devoted to it?|High
|10| Assign test cases to team members|The admin is able to assign test cases to team members.|1)Is it correct that only admin can assign test cases to team members?</br>2)Is this option a button on the create, edit, and display all test cases screen?</br>3) Is the option only visible to admin? </br>4)Are the options always visible but a pop up shows up when a user does not have admin privledges with a message denying them?</br>5)Are the team members based on the accounts in access control, and therefore prepopulated?</br>6)Can the admin make up team members, and therefore input data?|Medium
|11| Role-based access control|There are two different types of users of the program. An admin and a developer. The admins can do x. The developers can do y.|Can't remember right now, wrote it down in my notes, will go check after completed formatting table.|High
|12| Comment test cases with discussion threads|The user can comment on a test case with a discussion thread. The test case will display an icon in the view all test cases that shows there is a comment.The comments will display underneath the test case when the test case is viewed. There is a button to comment on a test from the view test case screen.|1)<When another comment is added to a test case, is the assigned user notified?</br>2)Is this part of the notifications feature?</br>3)Is this type of notification an additional feature?</br>4)Is there a button to comment on a test case from the view all test cases screen?|Medium
|13| Display interactive dashboards with summary statistics|The user can choose to view a dashboard with statistics for all of the cases. </br>The statistics include: </br>Pass/Fail rate</br>Total test cases|1)Should other meta information be included on this screen as well? I.E. any information from the drop down menus, such as project, module, functionality?|Medium
|14| Export reports|The user can export reports. The reports include CSV and PDF file formats. The user will select certain fields to be included in the report.|Is this from its own separate screen? |Medium
|15| Alert users for overdue test run or pending reviews|The user will recieve an email notification if the test run date has passed or if the review date has passed. The test run date and review date are set when the user creates a test case. At midnight of the date set, the user will recieve an email notification that the date has passed|----|Medium
|16| Remind users with notifications for assigned tasks or deadlines|The user will receive an email notification when a task or deadline has been added to a test case they are assigned. The user will also recieve an email notification at midnight beginning the day a task or deadline is due.|1)What are tasks?</br>Are deadlines the dates associated with test runs or reviews or for something separate?</br>Is this then a field option in the edit/create tests?</br>Is this a "task" field that has a description input field for text and has a date assignment attached to it? Is the task anything the user wants?</br>Is this a "deadline" field that has a description input field for text and has a date assignment attached to it? Is the deadline anything the user wants?|Medium|

**2b. Non-Functional Requirements**
**Non-Functional Requirements Table**
| Feature Number | Feature Name | Description | Questions | Priority |
| --------------- | --------------- | --------------- |--------------- |--------------- |
|1| User Login|The user is able to login using an email and password. There is a screen for an admin to create accounts for a user with email and password details.|Is this correct? Can admins invite users or is this not something included here?|High
|2|Performance|Pages will display in under 500 milliseconds or under 1 second. Filter and display of test cases will be under 5 minutes.|----|Medium
|3|Security|Security is handled with role based access control. The separation of developer and admin priviledges means less people have access to hire functions which minimizes the potential risk or attack space. Please see feature 11 for what privledges developer and admins have.|Are priviledges across admin and developer correct in Feature 11?|High
<table>
   <tbody>
      <tr>
         <th align="center">Number</th>
         <th align="center">Feature Name</th>
         <th align="center">Description</th>
         <th align="center">Questions</th>
         <th align="center">Priority</th>
      </tr>
      <tr>
         <td></td>
      </tr>
   </tbody>
</table>
**2c. User Interfaces**

**3. Timeline**\
Phase 1\
Start Date: 02/25/25\
End Date: 3/17/25\
Deliverable 3 due\
Duration: 20 days

<table>
  <tbody>
    <tr>
      <th align="center">Week 1</br>02/25/25-02/03/25</th>
      <th align="center">Week 2</br>02/04/25-03/10/25</th>
      <th align="center">Week 3</br>03/11/25-03/17/25</th>
    </tr>
    <tr>
      <td>
         <ul>
            <li>Create project structure</li>
            <li>Integrate third-partylibraries</li>
            <li>Design Database (DB) schema </li>
            <li>Finalize boilerplate</li>
            <li>Testing</li>
         <td>
         <ul>
            <li>Create user interface (UI) design and HTMLtemplate for test case</li>
            <li>Create Read Update (CRUD) operation</li>
            <li>Code Back-end for create/update/delete of test cases</li>
            <li>integrate UI and backend model for test case</li>
            <li>Testing</li>
         </ul>
         </td>
      <td > 
         <ul>
            <li>Code organization of test cases into different modules and projects</li>
            <li>Code clone test cases from existing test cases</li>
            <li>Testing</li>
            <li>Create necessary docs</li>
         </ul>
      </td>
    </tr>
  
  </tbody>
</table>


Phase 2\
Start Date: 03/18/2025\
End Date: 04/07/2025\
Deliverable 4 due\
Duration: 20 days

<table>
  <tbody>
    <tr>
      <th align="center">Week 1</br>03/18/25-03/24/25</th>
      <th align="center">Week 2</br>03/25/25-03/31/25</th>
      <th align="center">Week 3</br>04/01/25-04/07/25</th>
   </tr>
   <tr>
      <td>
         <ul>
            <li>Code to add attachments to test cases</li>
            <li>Code to tag test cases and filtering capabilities</li>
         </ul>
         </td>
         <td>
         <ul>
            <li>Code role-based access control</li>
            <li>Testing</li>
            <li>Add seed data for test cases and types of users</li>
         </ul>
         </td>  
         <td>
         <ul>
            <li>Code test case assignment to team members and send notification email to the assignee</li>
            <li>Write documentation</li>
            <li>Integration Test</li>
            <li>Performance Test</li>  
         </ul>
      </td>
    </tr>
  </tbody>
</table>


Phase 3\
Start Date: 04/08/2025\
End Date: 04/28/2025\
Duration: 20 days\
Deliverable 5 due

<table>
  <tbody>
    <tr>
      <th align="center">Week 1</br>04/08/25-04/14/25</th>
      <th align="center">Week 2</br>04/15/25-04/21/24</th>
      <th align="center">Week 3</br>04/22/25-04/28/25</th>
    <tr>
      <td>
         <ul>
            <li>Code discussion thread for each test case</li>
            <li>Implementing alerting and notification</li>
            <li>Create Summary Dashboard</li>
         <td>
         <ul>
            <li>Build report generation in PDF, Excel, CSV, etc</li>
            <li>Test</li>
            <li>Document Integration/Performance/User Acceptance Test(UAT) </li>
            <li>Release Application Beta Version</li>
         </td>
      <td > 
         <ul>
            <li>Review Final Product</li>
            <li>Test full application</li>
            <li>Write document</li>
            <li>Finalize reports</li>
         </ul>
      </td>
    </tr>
  
  </tbody>
</table>

**4. Member Contribution Table**
<table>
  <tbody>
    <tr>
      <th align="center">Member Name</th>
      <th align="center">Contribution Description</th>
      <th align="center">Overall Contribution</th>
       <th align="center">Note(if applicable)</th>
    </tr>  
    <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
     <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
     <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
     <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
     <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
     <tr>
      <td>
         </td>
      <td >
      </td>
       <td >
      </td>
       <td >
      </td>
    </tr>
  
  </tbody>
</table>

**5. Updated Meeting Minutes**


