### 4th-year-Project-Website

[![Build Status](https://travis-ci.org/arungalva/4th-year-Project-Website.svg?branch=master)](https://travis-ci.org/arungalva/4th-year-Project-Website)


# Project Description
This project is a web application to manage 4th year engineering capstone projects. This application is targeted towards students, professors, and program coordinators to help them manage all of the capstone projects for a given year. Additionally, this application allows students to submit documents, receive updates from the professors and coordinators, and register for presentations or meetings.


# MVP Key Features
- The application will have 3 main views; a home view, a student view, and a professor/coordinator view.
- The home view will display the list of capstone projects along with their relevant details. Users will be able to search through these projects.
- The application will offer authentication to allow users to log in as students, professors, or coordinators.
- In the student view, students will be able to register for projects.
- Once already registered, students will be able to submit documents for their project through the student view.
- In the professor view, professors will be able to add projects for students to register for, edit projects and archive them.
- Professors will also be able to download documents submitted to the projects they have added.
- Coordinators will be able to change deadlines for the different documents to be submitted, as well as send mass emails to students of a group or all students of all groups


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
- The basic home view is working with static deadlines, announcement, and projects pages
- The basic professor view is working
- The professor controller can add new projects
- The professor controller can edit existing projects
- The professor controller can archive existing projects
- Professor view can display all the projects that were added and not archived
- Projects view can display all the projects that were added and not archived


# Sprint [Mar 20 - Mar 27]
- [ ] Registration
- [ ] Sign in
- [ ] Authorization
- [ ] Student view