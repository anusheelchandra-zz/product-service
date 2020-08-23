
sh ./stop_app.sh
echo "starting the applciation .Plesse wait ......"
docker-compose up -d

echo "waiting for application to start in 10 secs .."
sleep 10
echo "waiting for application to start in 5 secs .."
sleep 5
echo "Started application. Please check logs if you cannot access the service."

