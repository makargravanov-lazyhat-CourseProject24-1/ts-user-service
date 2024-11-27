pipeline {
    agent any

    environment {
        SERVICE_NAME = "${JOB_NAME}"
        HTTP_PORT = 8080
        DB_URL = credentials("common-db-url")
        DB_USERNAME = credentials("common-db-username")
        DB_PASSWORD = credentials("common-db-password")
    }

    stages {
        stage("Build"){
            steps {
                sh "chmod a+x ./gradlew"
                sh "./gradlew bootBuildImage"
            }
        }
        stage("Image"){
            steps {
                sh "sudo docker build -t jetlabs/${SERVICE_NAME}:latest -t jetlabs/${SERVICE_NAME}:${BUILD_NUMBER} --build-arg name=${SERVICE_NAME}"
            }
        }
        stage("Deploy"){
            steps {
                sh "sudo docker run --name ${SERVICE_NAME}"
            }
        }
    }
}