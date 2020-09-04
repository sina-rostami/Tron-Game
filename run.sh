#/bin/bash

cd PythonServer
python3 main.py &

cd ..

cd JavaClient

cp dependencies/ChillinClient-1.0.2.jar out/production/
cp gamecfg.json out/production/

cd out/production

java -cp "ChillinClient-1.0.2.jar:." Main &
java -cp "ChillinClient-1.0.2.jar:." Main &


