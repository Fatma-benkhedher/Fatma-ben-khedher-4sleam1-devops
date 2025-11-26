pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'fatmabk/student-management'
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-fatmabk')
    }

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('Récupération Git') {
            steps {
                git url: 'https://github.com/Fatma-benkhedher/Fatma-ben-khedher-4sleam1-devops.git', branch: 'main'
            }
        }

        stage('Compilation') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }

        stage('Tests unitaires') {
            steps {
                sh 'mvn test -Dmaven.test.skip=true'
            }
        }

        stage('Création du livrable') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh """
                    docker build -t ${DOCKERHUB_REPO}:${IMAGE_TAG} .
                    docker tag ${DOCKERHUB_REPO}:${IMAGE_TAG} ${DOCKERHUB_REPO}:latest
                    echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin
                    docker push ${DOCKERHUB_REPO}:${IMAGE_TAG}
                    docker push ${DOCKERHUB_REPO}:latest
                    docker logout
                """
            }
        }
    }
}
