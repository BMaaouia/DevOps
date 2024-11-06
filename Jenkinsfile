pipeline {
    agent any

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
                    sh 'mvn sonar:sonar'
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
