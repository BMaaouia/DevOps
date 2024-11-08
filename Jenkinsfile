pipeline {
    agent any

    environment {
        // Set environment variables for SonarQube
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'b804c880886ff8d30caa992641e288482ea9a431' // Replace with your token
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
                url: 'https://github.com/BMaaouia/DevOps.git'
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
                    sh 'chmod +x mvnw'
                    sh 'chmod +x mvnw.cmd'
                    sh './mvnw sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN'
            }
        }
        stage('NEXUS') {
                    steps {
                        sh 'mvn deploy -X'
                    }
                }
    }
}
