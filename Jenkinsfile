pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
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
        stage('Checkout') {
            steps {
                git branch: 'Maaouia', url: 'https://github.com/BMaaouia/DevOps.git'
            }
        }
        
        stage('Build and Package') { // Compile and package before Nexus upload
            steps {
                sh 'mvn clean package -DskipTests' // Package the app and skip tests for this step
            }
        }

        stage('Publish to Nexus') {
            steps {
                script {
                    nexusArtifactUploader artifacts: [[artifactId: 'kaddem', classifier: '', file: 'target/kaddem-0.0.1-SNAPSHOT.jar', type: 'jar']],
                                           credentialsId: 'nexus-credentials-id',
                                           groupId: 'tn.esprit.spring',
                                           nexusUrl: 'http://192.168.33.10:8081',
                                           nexusVersion: 'nexus3',
                                           protocol: 'http',
                                           repository: 'maven-snapshots',
                                           version: '0.0.1-SNAPSHOT'
                }
            }
        }

        stage('SonarQube') {
            environment {
                SONAR_SCANNER_HOME = tool 'SonarQube Scanner'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN -Dsonar.java.binaries=target/classes"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ." // Build Docker image
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
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    sh 'docker-compose -f ./docker-compose.yml up -d' // Deploy using Docker Compose
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test' // Run tests after deployment
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
