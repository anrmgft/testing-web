pipeline {
    agent any
    options {
                ansiColor('xterm')
                timestamps()
                disableConcurrentBuilds()
                buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
            }
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean test check pitest'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                    jacoco execPattern: 'build/jacoco/*.exec'
                    recordIssues(
                        tools: [
                            pmdParser(pattern: 'build/reports/pmd/*.xml')
                        ]
                    )
                    recordIssues(tools: [pit(pattern: 'build/reports/pitest/*.xml')])

                }
            }
        }
        stage('Build') {
            steps {
                // Run Gradle Wrapper
                sh "./gradlew assemble"
            }
            post {
                // If Gradle was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    archiveArtifacts 'build/libs/*.jar'
                }
            }
        }
        stage('Deploy') {
            steps {
                   echo 'Deploying...'
            }
        }
    }
}
// ____________________________________________________
/*
pipeline {
    agent any
    options {
            ansiColor('xterm')
            timestamps()
            disableConcurrentBuilds()
            buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                //git branch: 'main', url: 'https://github.com/anrmgft/hello-springrest.git'

                // Run Maven on a Unix agent.
                withGradle {
                sh "./gradlew check test assemble"
                jacoco()
                recordIssues(tools: [pmdParser(pattern: 'build/reports/pmd */
/*.xml')])
                recordIssues(tools: [pit(pattern: 'build/reports/pitest */
/*.xml')])

                }
                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit 'build/test-results/test */
/*.xml'
                    archiveArtifacts 'build/libs */
/*.jar'

                }
            }
        }
        stage('Publish') {
            steps{
                sshagent(['github-ssh']) {
                            sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                            sh 'git push --tags'
                }
            }
        }
    }
} */
