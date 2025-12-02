pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'fatmabk/fatma-ben-khedher-4sleam1-devops'
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-fatmabk')
        SONAR_TOKEN = credentials('sonarqube-admin')
    }

    stages {

        stage('Récupération Git') {
            steps {
                git url: 'https://github.com/Fatma-benkhedher/Fatma-ben-khedher-4sleam1-devops.git', branch: 'main'
            }
        }

        stage('Tests unitaires + JaCoCo') {
            steps {
                sh 'mvn clean test'
            }
        }

  stage('Check Coverage > 0%') {
    steps {
        script {
            if (!fileExists('target/site/jacoco/jacoco.xml')) {
                error "Rapport JaCoCo non trouvé !"
            }

            def coverage = sh(
                script: 'xmllint --xpath "sum(//counter[@type=\'LINE\']/@covered) div sum(//counter[@type=\'LINE\']/@covered + //counter[@type=\'LINE\']/@missed) * 100" target/site/jacoco/jacoco.xml | awk \'{printf "%d", $1}\'',
                returnStdout: true
            ).trim()

            if (!coverage || coverage == "0") {
                error "Couverture JaCoCo = 0%. Pipeline arrêté."
            } else {
                echo "Couverture détectée : ${coverage}%"
            }
        }
    }
}





        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=FatmaDevopsProject \
                            -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Création du livrable') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
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
            echo "Pipeline terminé avec succès !"
        }
    }
}
