pipeline {
    agent any // Corrected the syntax for specifying any agent

    environment {
        SONAR_TOKEN = credentials('sonar_token')
        DOCKER_IMAGE = 'brmaaouia/backend-image'
        DOCKER_TAG = 'latest'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_CREDENTIALS = 'dockerhub-creds'
    }
    
    tools { 
        maven 'Maven 3.6.3' 
    }

    stages {
        stage('Checkout') { // Move checkout stage first
            steps {
                git branch: 'Maaouia', url: 'https://github.com/BMaaouia/DevOps.git'
            }
        }

	stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }

        stage('Nexus Clean') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .' // Build Docker image
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin ${DOCKER_REGISTRY}'
                    }
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    withEnv([
                        "DOCKER_IMAGE=${DOCKER_IMAGE}",
                        "DOCKER_TAG=${DOCKER_TAG}"
                    ]) {
                        sh 'docker-compose -f ./docker-compose.yml up -d' // Deploy using Docker Compose
                    }
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test' // Run tests
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
