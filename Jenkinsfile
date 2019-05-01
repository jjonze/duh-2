#!groovy
pipeline {
    agent any

    environment {
        commitMessage = ""
        artifactVersion = "1.0.${env.BUILD_NUMBER}"
    }

    stages {
        stage('compile') {
            steps {
                sh "./gradlew clean classes"
            }
        }
        stage('micro test') {
            steps {
                sh "./gradlew test"
            }
        }
        stage('build artifact') {
            steps {
                sh "./gradlew build -Pversion=${artifactVersion}"
            }
        }
        stage('deploy') {
            steps {
                sh "./gradlew publish -Pversion=${artifactVersion}"
            }
        }
    }

    post {
        always {
            junit "build/test-results/**/*.xml"
        }
    }
}

