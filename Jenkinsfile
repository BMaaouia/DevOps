pipeline {
    agent any

    environment {
        // Set environment variables for SonarQube
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'b804c880886ff8d30caa992641e288482ea9a431' // Replace with your token
        DOCKER_IMAGE = 'azizallani/backend-image'
        DOCKER_TAG = 'latest'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_CREDENTIALS = 'dockerhub-creds'
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
                    sh './mvnw sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN -Dsonar.ws.timeout=120'
            }
        }
        stage('NEXUS') {
                    steps {
                        sh 'mvn deploy'
                    }
                }
        stage('Build Docker Image') {
                    steps {
                        script {
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
                            sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                        }
                    }
                }

                stage('Deploy Application') {
                    steps {
                        script {
                            withEnv([
                                "DOCKER_IMAGE=${DOCKER_IMAGE}",
                                "DOCKER_TAG=${DOCKER_TAG}",
                                "DOCKER_REGISTRY=${DOCKER_REGISTRY}"
                            ]) {
                                sh 'docker-compose -f  /SonarAndNexus/docker-compose.yml up -d --build'
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
