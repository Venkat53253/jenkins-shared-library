def call(Map configMap){
    pipeline {
        agent {
            label 'agent1'
        }
        environment {
            course = 'jenkins'
        }
        options {
                timeout(time: 30, unit: 'MINUTES') 
            disableConcurrentBuilds()
        }
        parameters {
            string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
            text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
            booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
            choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
            password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password') 
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
            stage('Deploy') {
                input {
                    message "Should we continue?"
                    ok "Yes, we should."
                    submitter "alice,bob"
                    parameters {
                        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
                    }
                }
                steps {
                    script{
                        echo "Hello, ${PERSON}, nice to meet you."
                        
                        echo 'Deploying..'
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