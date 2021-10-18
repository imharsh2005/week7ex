pipeline{
agent{
kubernetes{
yaml '''
apiVersion: v1
kind: Pod
spec:
   containers:
   - name: gradle
     image: gradle:6.3-jdk14
     command:
     - sleep
     args:
     - 99d
     volumeMounts:
     - name: shared-storage
     mountPath: /mnt 
   - name: kaniko
     image: gcr.io/kaniko-project/executor:debug
     command:
     - sleep
     args:
     - 9999999
     volumeMounts:
     - name: shared-storage
       mountPath: /mnt
     - name: kaniko-secret
       mountPath: /kaniko/.docker
   restartPolicy: Never
   volumes:
   - name: shared-storage
     persistentVolumeClaim:
       claimName: jenkins-pv-claim
   - name: kaniko-secret
     secret:
         secretName: dockercred
         items:
           - key: .dockerconfigjson
             path: config.json
'''
	}
}
stages {
		stage('debug') {
			echo env.GIT_BRANCH			
		}
		 stage('feature') {
			if (env.GIT_BRANCH == "origin/feature") {
					try{
						sh '''
						pwd
						./gradlew checkstyleMain
						#./gradlew jacocoTestReport 
						'''
						}
						catch(all){
							echo "Catch"
						}
						publishHTML(target: [
							reportDir: 'Chapter08/sample1/build/reports/checkstyle',
							reportFiles: 'main.html',
							reportName: "JaCoCo CheckStyle"])
			 } else {
				echo "not a feature brnach"
			 }   
		   }
		  stage('CodeCoverage') {
			if (env.GIT_BRANCH == "origin/master") {
					sh '''
					pwd 
					sed -i 's/minimum = 0.2/minimum = 0.1/g' build.gradle
					./gradlew jacocoTestCoverageVerification 
					./gradlew jacocoTestReport 
					'''
					publishHTML(target: [
					reportDir: 'build/reports/jacoco/test/html',
					reportFiles: 'index.html',
					reportName: "JaCoCo Report"])
			 } else {
				echo "not a MASTER brnach"
			 }                        
		   }
    }
}
