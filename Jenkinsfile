pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'fatmabk/fatma-ben-khedher-4sleam1-devops'
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-fatmabk')
        SONAR_TOKEN = credentials('sonar-kuber-jenkins')
        KUBE_CONFIG = '/var/lib/jenkins/.kube/config'
    }

    stages {

        stage('Récupération Git') {
            steps {
                git url: 'https://github.com/Fatma-benkhedher/Fatma-ben-khedher-4sleam1-devops.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }
stage('Start SonarQube Pod') {
    steps {
        sh """
            kubectl apply -f sonarqube.yaml --kubeconfig=/var/lib/jenkins/.kube/config -n devops 
            kubectl rollout status deployment/sonarqube --kubeconfig=/var/lib/jenkins/.kube/config -n devops --timeout=120s
        """
    }
}
       stage('SonarQube Analysis') {
    steps {
        withCredentials([string(credentialsId: 'sonar-kuber-jenkins', variable: 'SONAR_TOKEN')]) {
            sh """
                mvn sonar:sonar \
                    -Dsonar.projectKey=student-management \
                    -Dsonar.host.url=http://localhost:30090 \
                    -Dsonar.login=$SONAR_TOKEN
            """
        }
    }
}
        stage('Check Coverage') {
            steps {
                script {
                    def response = sh(
                        script: "curl -s -u ${SONAR_TOKEN}: http://localhost:30090/api/measures/component?componentKey=student-management&metricKeys=coverage",
                        returnStdout: true
                    )

                    echo "Sonar JSON: ${response}"
                    def json = readJSON text: response

                    if (json.component?.measures) {
                        def coverage = json.component.measures[0].value
                        echo "Coverage = ${coverage}%"
                    } else {
                        echo "Warning: coverage data not found in SonarQube response!"
                    }
                }
            }
        }

      stage('Quality Gate') {
    steps {
        script {
            timeout(time: 5, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Quality Gate failed: ${qg.status}"
                } else {
                    echo "Quality Gate passed: ${qg.status}"
                }
            }
        }
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

        stage('Deploy to Kubernetes') {
            steps {
                sh "kubectl apply -f mysql-deployment.yaml --kubeconfig=${KUBE_CONFIG}"
                sh "kubectl apply -f spring-deployment.yaml --kubeconfig=${KUBE_CONFIG}"
            }
        }

    }

    post {
        always {
            sh 'docker logout || true'
            cleanWs()
        }
        success {
            echo "Pipeline terminé avec succès !"
        }
        failure {
            mail to: 'fatma.benkhedher@esprit.tn',
                 subject: "Build Failed: ${currentBuild.fullDisplayName}",
                 body: "Check Jenkins console output: ${env.BUILD_URL}"
        }
    }
}
