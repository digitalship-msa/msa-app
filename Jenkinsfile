pipeline {
    agent any
    environment {
        IMAGE_NAME = 'app-test:latest'
        CONTAINER_NAME = 'app-test'
    }

    stages {
        stage('pull request'){
            when {
                expression { env.CHNAGE_ID != null }
            }
            steps {
                 sh 'echo pull request ======================= '
                 sh 'echo jenkins test add  '
                 sh 'echo jenkins test add  2'
                 sh "echo pull request ID ======================= ${env.CHNAGE_ID}"
            }
        }
        stage('GitHub Repository Clone') {
            steps {
                git 'https://github.com/digitalship-msa/msa-app.git'
            }
        }
        stage('build') {
            steps {
                sh '''
                    chmod +x gradlew
                    echo build start
                    ./gradlew build
                '''
            }
        }
        stage('find jar') {
            steps {
                sh 'ls -al build/libs/'
            }
        }
        stage('Build docker image') {
            steps {
                script {
                  sh 'docker build -t ${IMAGE_NAME} .'
                }
            }
        }
        stage('Deploy Container') {
            steps {
                // 기존 컨테이너 정지 및 삭제
                sh "docker stop ${CONTAINER_NAME} || true"
                sh "docker rm ${CONTAINER_NAME} || true"

                // 새로운 컨테이너 실행
                sh "docker run -d -p 9090:8091 --name ${CONTAINER_NAME} ${IMAGE_NAME} "
            }
        }
    }
}