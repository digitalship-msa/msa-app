pipeline {
    agent any
    environment {
        IMAGE_NAME = 'app-test:latest'
        CONTAINER_NAME = 'app-test'
    }

    stages {
        stage('pull request'){
            when {
                expression { env.CHANGE_ID != null }
            }
            steps {
                 echo 'pull request =======================  '
                 echo 'jenkins test add  '
                 echo 'jenkins test add  2'
                 echo "pull request ID ======================= ${env.CHANGE_ID}"
            }
        }
        stage('No changes detected') {
            when {
                expression { env.CHANGE_ID == null }
            }
            steps {
                echo "No changes detected, skipping pull request stage."
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
                  sh 'docker build --cache-from ${IMAGE_NAME} -t ${IMAGE_NAME} .'
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
        stage('Clean-Up') {
            steps {
                sh 'docker image prune -f'
            }
        }
    }
    post {
        success {
            echo '배포 성공'
        }
        failure {
            echo '배포 실패'
        }
    }
}