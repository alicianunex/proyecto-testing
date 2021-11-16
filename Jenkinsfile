pipeline {
    agent any
    tools {
        maven "maven3.8.3"
        jdk "jdk_17"
    }
    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
                }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Site') {
            steps {
                bat 'mvn site'
            }
        }
    }
}
