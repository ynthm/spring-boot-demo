FILE_NAME=cross-login-0.0.1-SNAPSHOT
FILE_FOLDER=abc-demo
pid="$(ps -ef | grep $FILE_NAME.jar | grep -v grep | awk '{print $2}')"
if [ -n "$pid" ]; then
  kill -2 "$pid"
fi

nohup java -jar /opt/apps/$FILE_FOLDER/$FILE_NAME.jar --spring.profiles.active=demo &> $FILE_NAME.out&