def call(Map configMap){
    pipeline {
        agent {
            label 'agent1'
        }
        environment {
            course = 'jenkins'
            greeting = configMap.get('greeting')
        }
        options {
                timeout(time: 30, unit: 'MINUTES') 
            disableConcurrentBuilds()
        }
        parameters {
            string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
            text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
            booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        }
        // Build
        stages {
            stage ('build') {
                steps {
                    script {
                        sh """
                         echo hello build
                         sleep 10
                         env
                         echo Hello ${params.PERSON}
                        """
                    }
                }
            }
            stage ('test') {
                steps {
                    script {
                        echo 'building'
                    }
                }
            }            
        }

        post {
            always {
                echo 'not completed'
                deleteDir()    
            }
            success {
                echo 'sucess'
            }
            failure {
                echo 'fail'
            }
        }
    }
}