pipeline {
    agent any

    environment {
        HTTP_PORT = 8080
        DB_URL = credentials("common-db-url")
        DB_USERNAME = credentials("common-db-username")
        DB_PASSWORD = credentials("common-db-password")
    }

    stages {
        stage('Build'){
            steps {
                sh "sudo docker ps"
                sh "chmod a+x ./gradlew"
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