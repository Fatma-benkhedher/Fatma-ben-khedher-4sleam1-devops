pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn -v"
            }
        }
    }
}
