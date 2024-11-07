pipeline {
    agent any

    environment {
        // Set environment variables for SonarQube
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'dc08becfa9075f5ff7b6caf4971beb9b179182c8' // Replace with your token
    }

    stages {
        stage('Hellooooo') {
            steps {
                echo 'Hellooo World'
            }
        }
        stage('Checkout GIT') {
            steps {
                git branch: 'AzizAllani',
                    url: 'https://github.com/BMaaouia/DevOps.git',
            }
        }
        stage('Test Maven') {
            steps {
                sh 'mvn -version'
            }
        }
        stage('MVN clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('MVN compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('SonarQube analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                dir('5DS6-G1-Kaddem-main') {
                    sh 'chmod +x mvnw'
                    sh 'chmod +x mvnw.cmd'
                    sh './mvnw sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }


}