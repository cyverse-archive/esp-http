# esp-http

HTTP-based JSON API for basic interactions with the Event Stream Processor (ESP). This is still under initial development and probably won't be useful to anyone outside of the iPlant DE dev team for quite some time.

## Installation

You need to have an instance of the ESP database set up and running somewhere. The ESP database currently expects version 8.4 or later of PostgreSQL. The SQL files for setting up the database are available here:

    https://github.com/iPlantCollaborativeOpenSource/esp-database

Right now you need to run the files manually through psql, but automation for that is under development.

To deploy esp-http itself, you're going to need Leiningen 2 and Immutant 0.3.0. Instructions on getting Immutant set up are available here:

    http://immutant.org/tutorials/installation/index.html

Once you have Immutant running (hint: use the Leiningen plugin for now), set up a config file for esp-http. To do that create a $HOME/.esp/esp-http.properties file and fill it in with the following options:

    esp.http.db.name=esp
    esp.http.db.user=<esp-database-user>
    esp.http.db.password=<esp-database-password>
    esp.http.db.hostname=<esp-database-hostname>
    esp.http.db.port=<esp-database-port>

Obviously, you'll need to replace the values in the <> with the values applicable to your database install. Support for reading config values from Zookeeper is planned.

Finally, check out the code, change to the top level directory, and run:

    lein immutant deploy

esp-http should start up.