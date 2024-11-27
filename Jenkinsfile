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
                sh "chmod a+x ./gradlew"
                sh "sudo ./gradlew bootBuildImage"
            }
        }
        stage('Deploy'){
            steps {
                sh "sudo docker ps"
            }
        }
    }
}