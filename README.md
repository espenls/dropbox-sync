# dropbox-sync
Java application for dropbox sync
0) create a dropbox app:
https://www.dropbox.com/developers/apps/create
 -> dropbox API
 -> App folder
 -> Name of the app

1)Buld jar:
mvn clean compile assembly:single

2)Copy the jar file to the raspberry pi

3)create slideshow.sh and copyfile.sh in the home dir for pi on raspberry(scripts are located under resources)

4) ad the following to crontab:

crontab -e 

*/5 * * * * cd /home/pi/photoframe/; java -jar dropbox-sync-1.0-SNAPSHOT-jar-with-dependencies.jar
*/1 * * * * cd /home/pi/; ./copyfile.sh

5) Stop the screen from going blank:

Edit /etc/xdg/lxsession/LXDE-pi/autostart and add these three lines

@xset s off
@xset -dpms
@xset s noblank

6) make .slideshow run on startup
create folder /home/pi/.config/autostart
create a file called slideshow.desktop in the previous created folder, with the following content:

[Desktop Entry]
Type=Application
Exec=/home/pi/slideshow.sh


