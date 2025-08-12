pipeline {
    agent any

    tools {
        maven 'maven 3.9.11'  // Name as configured in Jenkins
        jdk 'jdk-21'        // Name as configured in Jenkins
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chaitanya0802/EventManagementSystem_TestAutomation.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
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
            junit 'target/surefire-reports/*.xml'
            cucumber fileIncludePattern: 'target/cucumber.json'
        }
    }
}
