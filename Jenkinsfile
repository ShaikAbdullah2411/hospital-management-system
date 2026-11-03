pipeline{

  agent any

  stages{
     stage('Welcome') {
            steps {
                echo 'Welcome to Jenkins!'
                echo 'Starting Hospital Management System CI/CD Pipeline.'
            }
        }
    stage('Build AppointmentService'){
      steps{
        dir('AppointmentService/AppointmentService'){
          bat 'call mvnw.cmd clean test'
    }
}
}
}
}
  
