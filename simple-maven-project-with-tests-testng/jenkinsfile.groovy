node {
   def mvnHome
    cd simple-maven-project-with-tests-testing
    checkout scm
    // sh ("git checkout master && git pull origin master")

    stage ('start') {
        // Get the maven tool.
        // ** NOTE: This 'M3' maven tool must be configured
        // **       in the global configuration
        mvnHome = tool 'M3'
    }
    stage ('Build') {
        if ('isUnix()') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
        } else {
            bat "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
        }
    }
    stage('Unit Testing') {
      echo '** Starting Unit Testing Phase **'
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
      echo '** Unit Tesing finished **'
   }
}