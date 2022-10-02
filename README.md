# Amazon-Price-Checker
This application Works
</br>
Road make to make this better
- Finish front end, display data to html page using thymeleaf &check;
- add bootstrap to have a cleaner front end.(Low prio)
- add delete function in main controller &check;
- Finish url validation, must include duplicate website
- Learn if website is open to sql injection
- add scheduled method to webscrape website &check;
- add secrets management .env file
- Fix spring from caching static images and preventing new images from being displayed
- Debug naming conventions on images unqiue to Primary KEY &check;

Create an applications.properties in resources directory to make it run on your computer, make sure to have mysql installed and make sure to have the following changes

<pre>
spring.jpa.hibernate.ddl-auto=#https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl
spring.datasource.url=#jdbc:mysql://localhost:3306/#yourschema
spring.datasource.username= #your mysql name
spring.datasource.password= #password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true
toSend = #an email to send to
spring.mail.username= #email Sender
spring.mail.password= #sender password
</pre>
