pipeline {
    agent any

    environment {
        SERVICE_NAME = "ts-user-service"
        HTTP_PORT = 8080
        DB_URL = credentials("common-db-url")
        DB_USERNAME = credentials("common-db-username")
        DB_PASSWORD = credentials("common-db-password")
    }

    stages {
        stage("Build"){
            steps {
                sh "chmod a+x ./gradlew"
                sh "./gradlew bootJar"
            }
        }
        stage("Image"){
            steps {
                sh "sudo docker build -t jetlabs/${SERVICE_NAME}:latest -t jetlabs/${SERVICE_NAME}:${BUILD_NUMBER} --build-arg name=${SERVICE_NAME} ."
            }
        }
        stage("Deploy"){
            steps {
                script {
                    try {
                        sh "sudo docker container stop ${SERVICE_NAME}"
                    } catch(err) {
                        echo "Container does not exists."
                    }
                }
                sh "sudo docker container rm ${SERVICE_NAME}"
                sh "sudo docker run --name ${SERVICE_NAME} -p 8021:${HTTP_PORT} --interactive --tty jetlabs/${SERVICE_NAME}:latest"
            }
        }
    }
}