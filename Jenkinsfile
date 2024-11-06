pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/BMaaouia/DevOps.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -o'
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
