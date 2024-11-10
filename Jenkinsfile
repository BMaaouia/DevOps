pipeline {
    agent any

    environment {
        // Set environment variables for SonarQube
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = '2becdf0c929409112e6545f46c00ed9211551520' // Replace with your token
        DOCKER_IMAGE = 'azizallani/backend-image'
        DOCKER_TAG = 'latest'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_CREDENTIALS = 'dockerhub-creds'
    }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
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

        stage('MVN Clean') {
            steps {
                sh 'mvn clean install'
            }
        }



        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                sh 'chmod +x mvnw'
                sh 'chmod +x mvnw.cmd'
                sh './mvnw sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN -Dsonar.ws.timeout=120'
            }
        }

        stage('Test Hello') {
            steps {
                echo 'Hello World'
            }
        }

        stage('Package Artifact') {
            steps {
                sh "mvn package -DskipTests"
            }
        }

        stage('NEXUS Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
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
                    sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    sh 'docker compose pull'
                    sh 'docker compose up -d'
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
