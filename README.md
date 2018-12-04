# computersDBframework
This repository contains WebDriver code examples and test case document. This Test Automation Framework is created using Java + Selenium Web Driver + TestNG + Maven. Which can be used across different web based applications.


# testcase document: Computer DB testcases.xlsx

# Prerequisites:

1. Install java JDK 1.8 from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Set export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home in your .bash_profile
2. Install Maven from http://maven.apache.org/install.html
Verify Maven in installed correctly with mvn -version
3. Make sure you have the latest versions of Firefox, Internet Explorer and Chrome installed.
4. IDE for running - Eclipse or Intellij


# Environment:
Since there is onely 1 environment to test, but configuration can be set to - dev, qa, stage, and prod.

By default, Chrome is set for test automation and can be changed in generic.Initialization class file <Browser>

# Execution:
Clone the repository.

Open command prompt and go to web-test directory.
To run on local environment use command .....\computersDBframework> mvn clean test


Import the project in Eclipse.

Ensure that project <computersDBframework> has pom.xml and testng.xml
right click on pom.xml and Run As - Maven install {test, if goal is required}
or 
right click on testng.xml and Run As - TestNG suite

# Logging:
Currently Logging is disabled due to dependency on local files


# Screenshot:
Each tests has the screenshot captured at the end of execution, either when all the steps are completed or encounted with exception.
It is set to <My Documents> local folder and it can be changed.
Screenshots have test name to understand the objective of test case.

# Reporting:
TestNG provides built-in reporting with status of each tests and it can be found in test-output folder.
View emailable-report.html and index.html for the details.

