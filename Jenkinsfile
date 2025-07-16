pipeline {
    agent { label 'host-1' }

    environment {
        GIT_CREDENTIALS_ID = 'github-pat'
        IMAGE_NAME = 'contactbook'
        IMAGE_TAG = "v1.${BUILD_NUMBER}"
        FULL_IMAGE_NAME = "192.168.56.10/javacontactbook/${IMAGE_NAME}:${IMAGE_TAG}" // use domain name instead 
    }

    stages {
        stage('Clone Repository') {
            steps {
                git credentialsId: "${GIT_CREDENTIALS_ID}",
                    url: 'https://github.com/erkdk/devsecops-javacontactbook.git',
                    branch: 'initial-setup'                                             // appropriate name for branch
            }
        }

        stage('Archive contactbook.WAR') {  // archive is used for building image later
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        stage('List Docker Images') {           // this stage no need in production
            steps {
                sh 'docker images'
            }
        }

        stage('Trivy Scan') { // store reports, check high/critical vulnerabilities level and fail build.
            steps {
                sh 'trivy image ${IMAGE_NAME}:${IMAGE_TAG}'
            }
        }

        stage('Tag Image for Harbor') {
            steps {
                sh 'docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${FULL_IMAGE_NAME}'
            }
        }

        stage('Push to Harbor') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'harbor-creds', usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASS')]) {
                    sh '''
                        echo "$HARBOR_PASS" | docker login 192.168.56.10 -u "$HARBOR_USER" --password-stdin
                        docker push ${FULL_IMAGE_NAME}
                        docker logout 192.168.56.10
                    '''
                }
            }
        }

        stage('Deploy to Swarm') {          // ssh remote clusters and deploy
            agent { label 'devops-master' }

            steps {
                git credentialsId: "${GIT_CREDENTIALS_ID}",
                    url: 'https://github.com/erkdk/devsecops-javacontactbook.git',
                    branch: 'initial-setup'

                // dynamically update image version in docker-stack.yml and deploy
                sh '''
                    sed -i "s|image: .*|image: ${FULL_IMAGE_NAME}|" docker-stack.yml
                    docker service rm contactbook || true
                    docker stack deploy -c docker-stack.yml contactbook
                '''
            }
        }


    }
}


// some commented lines are the corrections to be made.
