pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "bizconnect"
        DOCKER_TAG = "${BUILD_NUMBER}"
        CONTAINER_NAME = "bizconnect-app"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== Checking out code ==='
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '=== Building with Gradle ==='
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test'
            }
        }

        stage('Docker Build') {
            steps {
                echo '=== Building Docker image ==='
                sh """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                    docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                """
            }
        }

        stage('Deploy') {
            steps {
                echo '=== Deploying application ==='
                sh """
                    # 기존 컨테이너 중지 및 제거
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true

                    # 새 컨테이너 실행
                    docker run -d \
                      --name ${CONTAINER_NAME} \
                      --network bizconnect_bizconnect-network \
                      -e SPRING_PROFILE=dev \
                      -e SERVER_PORT=8301 \
                      -e DB_HOST=mariadb \
                      -e DB_PORT=3306 \
                      -e DB_NAME=bizconnect \
                      -e DB_USERNAME=lsh \
                      -e DB_PASSWORD=lsh \
                      -p 8301:8301 \
                      ${DOCKER_IMAGE}:latest
                """
            }
        }

        stage('Health Check') {
            steps {
                echo '=== Checking application health ==='
                sh """
                    sleep 15
                    curl -f http://localhost:8301/health || exit 1
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful!'
        }
        failure {
            echo '❌ Deployment failed!'
            sh 'docker logs ${CONTAINER_NAME} || true'
        }
        always {
            echo '=== Cleaning up old images ==='
            sh 'docker image prune -f'
        }
    }
}