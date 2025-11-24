pipeline {
    agent any
    environment {
        JAVA_HOME = "/usr/lib/jvm/java-11-openjdk"
        M2_HOME   = "/usr/share/maven"
    }
    stages {
        stage('Build') {
            steps {
                sh "${M2_HOME}/bin/mvn clean package"
            }
        }
    }
}
