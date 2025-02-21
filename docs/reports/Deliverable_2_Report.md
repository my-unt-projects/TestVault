2. Written Requirements Specifications

2a. Functional requirements

1) Create test cases

Description: 
The user will create a new test case. The user will push a create button which will display a test case input form with certain fields for the user to input data (see below). Once the user is finished inputing data, then the user pressed a save button. The test case is viewable.

Fields include:
module
project
test run date
review date


Questions:
Would the view of all test cases be another feature?

Would the view of a singular test case be a feature?

Is the test case an input form or is it an option for the user to upload a txt file?

If it is an input form, does the user have the option to add or change the fields or is it preset?

How is the test case named? Does the user select or input the name of the test case, or is it an automatically assigned number?

Is view test case a separate feature?

Are the fields correct?


(2) Edit test cases 

Description:
The user will be able to edit an existing test case. Editing of test cases will occur one at a time. The User will be able to search for a specific test case, select it for viewing and then the user will press an edit button. The fields of the test case are prepopulated with the existing information which was previously entered, but can be edited by the user. Once finished editting, the user will then click on a save button to save the test case.
 
 Questions:
 
 Is the edit test case view just the create view but with prepopulated information?
 
 Will there be a select test case button in the view of test cases or an edit test case button in the view of test cases so the user can go straight to editting?
 
 Is there a button to cancel the edits made and revert to the original?
 
 
(3) Delete test cases

Description: The user will be able to delete test cases. The user will search for a specific test case, select the case for deletion and then select a delete button. Once deleted there is no retrieval of the test case.

Questions: Similair to the edit feature, will they be a delete button next to the test cases in the view test cases display or will the the delete button be in a single test case view?

Is there a way to select and delete multiple cases?

(4) Organize test cases into modules

Description:
The user will be able to assign a test case to a module. The user will select a module for a test case to be assigned to. 

Questions: 
Does a project need to be created prior to creation of a test case?

Does a module need to be created prior to creation of a project?

Could there be a default module and project group so the user can create a test case without assigning a project or module to it?

Is create module and create project on the "home page" along with reporting notification and assignment functions?

Is create module and create project another feature?

Are there drop downs in create and edit test cases which are prepopulated with 
existing modules and projects that the user can select?

(5) Clone test cases

Description:
The user will be able to clone a test case. The user will select a test case in the view test cases display and select a clone button. This action creates a new test case with the same data for the fields of the original test case.

Questions:
Does the user name the new test case? If so, is this a popup window that asks the user to name the teset case?

Or does the new test case have a name which is a version of the original test case?

(6) Use test cases across different projects

Description:
The user can assign a test case to multiple projects. In the create or edit test case view, the drop down assigning a test case to a project allows multiple selections of projects.

Questions:
Is this how it is?

(7) Attachments to test case

Description:
The user is able to attach files  to a test case. The files can be x,y,z format. 

Questions:

What file formats are attached?

Where is the user able to attach the file? Is it in the create test case form? Is there an option to add an attachment next to a test case in the display of all test cases? Is there a button to add an attachment in the edit test case view?

Is there an icon that shows a test case has an attachment in the view display of all test cases?

Is there an icon at the bottom of a test case in the view test case display? 

Can the user open the attachment?

Can the user download the attachment?

(8) Tag test cases

Description: The user can assign test cases certain tags. These include priority, module, and functionality. In the create and edit view there are drop downs with prepopulated values the users can select for the test case.

Priority: Low, Medium, High, Critical
Module:?
Functionality: To Do, In progress, Done

Questions:
Are they drop downs, or should they be like tags displayed that the user can toggle?

Can the user assign tags in the view all test cases display?

What are the prepopulated values for module?

Are the other prepopulated values correct?

(9) Filter and search test cases

Description: The user can search and filter test cases by any of the following meta information:

priority
project
module
functionality 
name

Questions:
Is this option on the home screen or navigation menu?

Does this have its own screen devoted to it?

(10) Assign test cases to team members

Description: The admin is able to assign test cases to team members.

Question:

Is it correct that only admin can assign test cases to team members?

Is this option a button on the create, edit, and display all test cases screen? Is the option only visible to admin? 

Are the options always visible but a pop up shows up when a user does not have admin privledges with a message denying them?

Are the team members based on the accounts in access control, and therefore prepopulated?

Can the admin make up team members, and therefore input data?

(11) Role-based access control
There are two different types of users of the program. An admin and a developer. The admins can x. The developers can y.

(12) Comment test cases with discussion threads

The user can comment on a test case with a discussion thread. The test case will display an icon in the view all test cases that shows there is a comment.
The comments will display underneath the test case when the test case is viewed. 
There is a button to comment on a test from the view test case screen.

Questions:
When another comment is added to a test case, is the assigned user notified?
Is this part of the notifications feature? Is this type of notification an additional feature?

Is there a button to comment on a test case from the view all test cases screen?

(13) Display interactive dashboards with summary statistics

The user can choose to view a dashboard with statistics for all of the cases. 
The statistics include: 
Pass/Fail rate
Total test cases

Questions:
Should other meta information be included on this screen as well? I.E. any information from the drop down menus, such as project, module, functionality?

(14) Export reports
The user can export reports. The reports include CSV and PDF file formats. 
The user will select certain values 
Questions: 

Is this from its own separate screen? 

(15) Alert users for overdue test run or pending reviews
The user will recieve an email notification if the test run date has passed or if the review date has passed. The test run date and review date are set when the user creates a test case. At midnight of the date set, the user will recieve an email notification that the date has passed.

(16) Remind users with notifications for assigned tasks or deadlines

The user will receive an email notification when a task or deadline has been added to a test case they are assigned. The user will also recieve an email notification at midnight beginning the day a task or deadline is due.

Questions:

What are tasks?

Are deadlines the dates associated with test runs or reviews or for something separate?

Is this then a field option in the edit/create tests?

Is this a "task" field that has a description input field for text and has a date assignment attached to it? Is the task anything the user wants?

Is this a "deadline" field that has a description input field for text and has a date assignment attached to it? Is the deadline anything the user wants?

2b. Non-Functional Requirements:

1) User Login

The user is able to login using an email and password. There is a screen for an admin to create accounts for a user with email and password details.

Questions:

Is this correct? Can admins invite users or is this not something included here?

Performance:

Pages should display in under one second. Filter and display records should be under one minute. 

Security:

Security is handled with role based access control. Please see feature 11.
