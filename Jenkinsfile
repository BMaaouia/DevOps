pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar_token')  
	DOCKER_IMAGE = 'brmaaouia/backend-image'  
        DOCKER_TAG = 'latest'  
        DOCKER_REGISTRY = 'docker.io'  
        DOCKER_CREDENTIALS = 'dockerhub-creds'
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


        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image from Dockerfile
                    sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {

                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                    }
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    // Push the built image to DockerHub
                    sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    // Deploy using Docker Compose (if applicable)
                    sh 'docker-compose up -d --build'
                }
            }
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
