# parser
Log file Parser

Assumptions :

1) App was written on MySql server version 5.7.19
2) App expects JAVA_MYSQL schema on MySQL server. Table creations are done at runtime via hibernate (attached DB creation script).
3) App code compiles and runs on Java 8.

App Usages:

App usages :
java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --accesslog=/Users/pdewanga/sourcecode/java_mysql/src/main/resources/access.log --mysqlusername=root --mysqlpassword=admin --mysqlconnectionurl=jdbc:mysql://localhost:3306/JAVA_MYSQL
 or 
java -cp "parser.jar" com.ef.Parser --accesslog=/Users/pdewanga/sourcecode/java_mysql/src/main/resources/access.log --mysqlusername=root --mysqlpassword=admin --mysqlconnectionurl=jdbc:mysql://localhost:3306/JAVA_MYSQL
Please ensure that your MySQL server is running
Please ensure that your MySQL server has JAVA_MYSQL schema
