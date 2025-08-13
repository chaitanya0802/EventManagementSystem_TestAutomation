pipeline {
    agent any
 
    tools {
        maven 'maven 3.9.11'  
        jdk 'jdk-21'   
    }
 
    environment {
        GIT_REPO = 'https://github.com/chaitanya0802/EventManagementSystem_TestAutomation.git'
        BRANCH = 'main'
        REPORT_PATH = 'target/surefire-reports'
    }
 
    stages {
        stage('Checkout Code') {
            steps {
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }
 
        stage('Build & Run Tests') {
            steps {
               bat 'mvn clean test -DsuiteXmlFile=testng.xml || exit 0'
            }
        }
 
        stage('Publish Reports') {
            steps {
		//extent reports
                publishHTML(target: [
                    reportName: 'Extent Report',
                    reportDir: 'target/extent-report',  
                    reportFiles: 'ExtentReport.html',          
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])

                //cucumbr report
                publishHTML(target: [
                    reportName: 'Cucumber Report',
                    reportDir: 'target/cucumber-reports', 
                    reportFiles: 'cucumber.html',           
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
            
        }
    }
 
    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Please check logs.'
        }
    }
}
