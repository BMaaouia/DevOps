pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar_token')  
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/BMaaouia/DevOps.git'
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

	stage('Install') {
            steps {
                sh 'mvn install'
            }
        }

	stage('SonarQube Analysis') {
            steps {
                    sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }



    
    }

    post {
        success {
            echo 'Build finished successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
