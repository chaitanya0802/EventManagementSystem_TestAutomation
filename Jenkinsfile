pipeline {
    agent any
 
    tools {
        maven 'maven 3.9.11'  // Name as configured in Jenkins
        jdk 'jdk-21'     // Name of JDK configured in Jenkins
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
               bat 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }
 
        stage('Publish Reports') {
            steps {
                // Publish Extent report
                publishHTML(target: [
                    reportName: 'Extent Report',
                    reportDir: 'target/extent-report',  // change if your folder is different
                    reportFiles: 'ExtentReport.html',          // change file name if different
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])

                // Publish Cucumber report
                publishHTML(target: [
                    reportName: 'Cucumber Report',
                    reportDir: 'target/cucumber-reports', // folder containing cucumber HTML
                    reportFiles: 'cucumber.html',           // cucumber html report file name
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
            
        }
    }
 
    post {
        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Please check logs.'
        }
    }
}
