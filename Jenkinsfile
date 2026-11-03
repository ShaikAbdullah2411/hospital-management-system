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
    stage('Build authService'){
      steps{
        dir('authService/authService'){
          bat 'call mvnw.cmd clean test'
        }
      }
    }
    stage('Build EurekaServer'){
      steps{
        dir('EurekaServer/EurekaServer'){
          bat 'call mvnw.cmd clean test'
        }
      }
    }
    stage('Build ApiGateway'){
      steps{
        dir('ApiGateway/ApiGateway'){
          bat 'call mvnw.cmd clean test'
        }
      }
    }
    stage('Build DoctorService'){
      steps{
        dir('DoctorService/DoctorService'){
          bat 'call mvnw.cmd clean test'
        }
      }
    }
    stage('Build PatientService'){
      steps{
        dir('PatientService/PatientService'){
          bat 'call mvnw.cmd clean test'
        }
      }
    }
}
}
  
