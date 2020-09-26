pid="$(ps -ef | grep resource-isolate-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}')"
if [ -n "$pid" ]; then
  kill -9 "$pid"
fi

nohup java -server -Xms512m -Xmx512m -jar target/resource-isolate-0.0.1-SNAPSHOT.jar &
