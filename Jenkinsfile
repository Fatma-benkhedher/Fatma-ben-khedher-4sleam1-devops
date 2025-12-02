pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'fatmabk/fatma-ben-khedher-4sleam1-devops'
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-fatmabk')
        SONAR_TOKEN = credentials('sonarqube-token')   // Token Sonar
    }

    stages {

        stage('Récupération Git') {
            steps {
                echo 'Récupération du code depuis GitHub...'
                git url: 'https://github.com/Fatma-benkhedher/Fatma-ben-khedher-4sleam1-devops.git', branch: 'main'
            }
        }

        stage('Analyse SonarQube') {
            steps {
                echo "Analyse de la qualité du code..."
                withSonarQubeEnv('SonarQubeServer') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=FatmaDevopsProject \
                        -Dsonar.host.url=$SONARQUBE_URL \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Tests unitaires') {
            steps {
                sh 'mvn test -Dmaven.test.skip=true'
            }
        }

        stage('Création du livrable') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t ${DOCKERHUB_REPO}:${IMAGE_TAG} .
                    docker tag ${DOCKERHUB_REPO}:${IMAGE_TAG} ${DOCKERHUB_REPO}:latest
                """
            }
        }

        stage('Push Docker Hub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh """
                    docker push ${DOCKERHUB_REPO}:${IMAGE_TAG}
                    docker push ${DOCKERHUB_REPO}:latest
                """
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
        success {
            echo "Image poussée avec succès !"
        }
    }
}
