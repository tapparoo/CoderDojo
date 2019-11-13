## SD Final Project: Coder Dojo

![coderDojo](https://coderdojo.com/_nuxt/img/coderdojo.761bb66.svg)

### Project Overview:
This application was built to run a educational non-profit keep track of its curriculum, its student's progress, and allow parents to sign their children up for events.  

There are three types of user in this project. Student, Parent, and Admin/Teacher.

**Students** can track their progress from their desktop. They have 3 main components to their view - completed belts, pending belts, and a calendar of upcoming Coder Dojo sessions they have signed up for.
* Log in with: mark/password

**Parents** can track their children’s progress, as well as update/change the child’s profile and add new child accounts.
* Log in with: anna/password

**Admins/Teachers** have the most ability within the system. They can create meetings, assign/award belts to students, track attendance, add to or edit the belt requirements, add new custom belts for specific students, and grant other users admin rights.
* Log in with: adam/password


### Technologies Used

* Front End
  * Angular
  * Angular Material
  * Bootstrap
* Back End
  * Java
  * Spring Boot
  * Spring Security
  * MySQL
  * Hibernate




### How to publish this application

#### Components:
1. MySQL DB File - Username: codedojouser / Password: codedojouser
2. Java projects - CoderDojo & CoderDojoJPA
3. Angular project - lives in the CoderDojo/coderDojoApp folder - this is the angular portion of the project.  There is another copy of it in the CoderDojo project, which will need to be refreshed any time a new WAR file is generated.

#### Steps to publish:
1. This project built to run on an AWS instance with an Apache Tomcat 8.5 server & MySQL installed. These are prerequisites for the steps below.
2. Download this repository, and open it in Spring Tool Suite.  Be sure to add both CoderDojo and CoderDojoJPA to the workspace.
3. Using Gradle tasks, create a WAR file from the CoderDojo project.
4. Login to your AWS/EC2 instance via a bash terminal, and upload the file CoderDojo/DB/coderDojoDB.sql.
5. Still inside your EC2 instance, login to MySQL and initialize the coderDojo DB.  Exit MySQL.
6. Lastly copy the WAR file into your tomcat8 webapps folder so that Tomcat will run the project.
