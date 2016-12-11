UpPointsPDV
==============

Implementation of an example PDV (point-of-sale) system. This project uses Eclipselink JPA for handling database and Vaadin framework to show the result in a web interface.

Features
========
* This project uses eclipselink JPA and Derby for handling storage, the code is prepared for a persistence unit called `uppointspdv.main`, change this name will require code adaptation;
* Allow users to create PDV users and to login to the system;
* CRUD operations for PDVs: PDV users logged in the system are allowed to create new PDVs only, however, the classes `uppointspdv.dao.PDVDao` and `uppointspdv.model.PDV` implements all CRUD operations;
* The web interface was built with [Vaadin](https://vaadin.com/) framework and it presents a login page, a list PDVs page, an add new PDV page and a show PDV details page.

Screenshots
========

Workflow
========

To compile the entire project, run "mvn install".

To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"

Client-Side compilation
-------------------------

The generated maven project is using an automatically generated widgetset by default. 
When you add a dependency that needs client-side compilation, the maven plugin will 
automatically generate it for you. Your own client-side customisations can be added into
package "client".

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

Developing a theme using the runtime compiler
-------------------------

When developing the theme, Vaadin can be configured to compile the SASS based
theme at runtime in the server. This way you can just modify the scss files in
your IDE and reload the browser to see changes.

To use the runtime compilation, open pom.xml and comment out the compile-theme 
goal from vaadin-maven-plugin configuration. To remove a possibly existing 
pre-compiled theme, run "mvn clean package" once.

When using the runtime compiler, running the application in the "run" mode 
(rather than in "debug" mode) can speed up consecutive theme compilations
significantly.

It is highly recommended to disable runtime compilation for production WAR files.
