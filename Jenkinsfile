pipeline {
agent {
 kubernetes {
    yaml '''
         spec:
	 containers:
	   - name: gradle
	     image: gradle:6.3-jdk14 
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
           }							   
	  }
      stage('main') {
         when {
  	   expression {
   	       return env.GIT_BRANCH == "origin/main"
	     }
	   } 
	   steps { 
	       echo "I am a main branch"
	   }
	  }
	}
}

/*pipeline{
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
		git 'https://github.com/imharsh2005/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git'
		container('gradle') {
		
			sh '''
			pwd
			cd Chapter08/sample1/
			chmod +x gradlew
			./gradlew build
			mv ./build/libs/calculator-0.0.1-SNAPSHOT.jar /mnt
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
       }
    }

 }
}
*/
