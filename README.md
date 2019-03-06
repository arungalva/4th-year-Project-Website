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
- In the professor view, professors will be able to add projects for students to register for.
- Professors will also be able to download documents submitted to the projects they have added.
- Coordinators will be able to change deadlines for the different documents to be submitted, as well as send mass emails to students of a group or all students of all groups


# Model UML Class Diagram
--> This will be generated once our models are finalized


# ER Diagram
--> This needs to be generated


# Milestone 1 PR work summary
- The base project has been deployed on Heroku
- The Github repository has been connected with Travis CI and Heroku
- The entity classes for the projects and users have been created
- A MySql server has been set up on the Heroku server
- The basic home view is working
- The basic professor view is working
- The professor controller can add new projects and edit existing projects
