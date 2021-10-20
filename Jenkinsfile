pipeline {
agent {
 kubernetes {yaml '''
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
triggers {
          githubPush()
 }
 stages {
   stage('debug') {
     steps {
        echo env.GIT_BRANCH
        echo env.GIT_LOCAL_BRANCH 
     }
   }
   stage('feature') {
      when {
	   expression {
    	       return env.GIT_BRANCH == "origin/feature"
	      }
	   }
	steps {
		echo "I am a feature branch"
		// git 'https://github.com/imharsh2005/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git'
		container('gradle') {	
			sh '''
			pwd
			cd Chapter08/sample1/
			chmod +x gradlew	
			rm build.gradle
			mv build.gradle_feature build.gradle
			sed -i 's/minimum = 0.2/minimum = 0.1/g' build.gradle
			./gradlew build
			mv ./build/libs/calculator-0.0.1-SNAPSHOT.jar /mnt
			'''			
		     }
		   container('kaniko') {
			sh '''
				echo 'FROM openjdk:8-jre' > Dockerfile
				echo 'COPY ./app.jar app.jar' >> Dockerfile
				echo 'ENTRYPOINT ["java", "-jar", "app.jar"]' >> Dockerfile
				mv /mnt/calculator-0.0.1-SNAPSHOT.jar ./app.jar
				/kaniko/executor --context `pwd` --destination imharsh2005/calculator-feature:0.1
				'''
			}
		   
	      }							   
	  }
stage('playground') {
      when {
	   expression {
    	       return env.GIT_BRANCH == "origin/playground"
	      }
	   }
	steps {
		echo "I am a playground branch"
		// git 'https://github.com/imharsh2005/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git'
		   
		 echo "No action to be taken for playground branch"
	      }							   
	  }
      stage('master') {
         when {
  	   expression {
   	       return env.GIT_BRANCH == "origin/master"
	     }
	   } 
	   steps {
		echo "I am a master branch"
		// git 'https://github.com/imharsh2005/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git'
		container('gradle') {	
			sh '''
			pwd
			cd Chapter08/sample1/
			chmod +x gradlew	
			rm build.gradle
			mv build.gradle_master build.gradle
			sed -i 's/minimum = 0.2/minimum = 0.1/g' build.gradle
			./gradlew build
			mv ./build/libs/calculator-0.0.1-SNAPSHOT.jar /mnt
			'''			
		     }
		   container('kaniko') {
			sh '''
				echo 'FROM openjdk:8-jre' > Dockerfile
				echo 'COPY ./app.jar app.jar' >> Dockerfile
				echo 'ENTRYPOINT ["java", "-jar", "app.jar"]' >> Dockerfile
				mv /mnt/calculator-0.0.1-SNAPSHOT.jar ./app.jar
				/kaniko/executor --context `pwd` --destination imharsh2005/calculator:1.0
				'''
			}
		   
	      }
	  }
	}
}

