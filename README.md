### 4th-year-Project-Website

[![Build Status](https://travis-ci.org/arungalva/4th-year-Project-Website.svg?branch=master)](https://travis-ci.org/arungalva/4th-year-Project-Website)


# Project Description
This project is a web application to manage 4th year engineering capstone projects. This application is targeted towards students, professors, and program coordinators to help them manage all of the capstone projects for a given year. Additionally, this application allows students to receive updates from coordinators.


# MVP Key Features
- The application will have 3 main views; a home view, a student view, and a professor view
- The home view will have links to the login/sign up page as well as the announcements and projects views
- The projects view will display the list of projects along with their relevant details
- The log in/sign up page will offer authentication to allow users to log in as students, professors, or coordinators.
- In the projects view, students will be able to register for projects.
- In the professor view, professors will be able to add projects for students to register for, edit projects and archive them.
- Coordinators will be able to add/edit/delete announcements in the announcements page


# Model UML Class Diagram
![Model Class diagram](src/main/resources/static/assets/images/entity_uml.png)



# ER Diagram
![alt text](src/main/resources/static/assets/images/Database_Schema.png)


# Current Status
- The base project has been deployed on Heroku
- The Github repository has been connected with Travis CI and Heroku
- The entity classes for the projects and users have been created
- A ProstgreSQL server has been set up on the Heroku server
- The application uses repositories to handle persistence
- The header has a button linking to the log in / sign up page
- The basic home view is working with announcements and projects pages
- If logged in as a student, the home view holds a link to the student view
- If logged in as a professor, the home view holds a link to the professor view
- The announcements view displays all the announcements
- If logged in as a coordinator, the announcements view allows for adding, deleting, or editing announcements
- The projects view displays all the projects that were added and not archived
- The projects view has links for every project to lead to detailed project pages
- If logged in as a student who does not currently have a project, the projects view has buttons for students to join projects (this only applies to projects with room for additional students)
- The professor view is working; here the professor can add new projects, edit existing projects, and archive existing projects
- The student view is working; here, details about the student and their registered project are displayed
- The footer for all pages is set up to mimic actual Carleton web pages