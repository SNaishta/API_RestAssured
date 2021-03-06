# Marvel
Marvel API Automation Framework using Rest Assured


SETUP
Please follow the below few steps as a pre-requisite for running the automation scripts 

- Download Java
- Download IntelliJ IDE
- Download Maven 
- Create git account to access the automation repository
- Create developer account on Marvel to get the API keys

Set the path in your bash profile

******************* Sample ******************* 

Bash Profile
export JAVA_HOME=$(/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home)
export PATH=$PATH:$JAVA_HOME/bin
export PATH=$PATH:$JAVA_HOME/jre
export PATH=~/.npm-global/bin:$PATH

export M2_HOME=$(/usr/local/Cellar/maven/3.5.3/libexec)
export M2=$M2_HOME/bin

export MARVEL_PUBLIC_KEY=YOURAPIKEY&&
export MARVEL_PRIVATE_KEY=YOURPRIVATEKEY

Clone or download the project from Github Link -> https://github.com/SNaishta/API_RestAssured

Running the scripts from terminal from project path 
mvn clean verify -PRegressionSuite

Reports can be found in 
projectDirectory/target/surefire-reports/emailable-report.html
/target/surefire-reports/Regression Suite/RestAssured Test.html

Exception log can be found in 
projectDirectory/target/logs


Good Reads:
https://blog.overops.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/
https://github.com/rest-assured/rest-assured/wiki/Usage#json-example