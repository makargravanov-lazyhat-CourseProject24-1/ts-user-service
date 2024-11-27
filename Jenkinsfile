pipeline {
    agent any
    stages {
        stage('Build'){
            steps {
                sh "chmod 755 $USER ./gradlew"
                sh "./gradlew build"
            }
        }
        stage('Image'){
            steps {
                sh "sudo docker ps"
            }
        }
        stage('Deploy'){
            steps {
                sh "sudo docker ps"
            }
        }
    }
}