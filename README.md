# Drinked, THE way to drink.

### Database Setup
Here we is the procedure to follow in order to install the database. All commands are meant to be run in bash.

<ol>
    <li>You must log in your mysql server using "mysql -u username -p".</li>
    <li>You will then create the database: "CREATE DATABASE drinked;".</li>
    <li>Then you log off from mysql and you go into the directory containing "drinked.sql".</li>
    <li>Once you are in the right directory, you will execute this command: "mysql -u root -p drinked < drinked.sql".</li>
</ol>
You have now imported successfully the drinked database, with already existing examples in it.

##### Now to get your drinkedDB to work:

<ol>
    <li>You will find in src/main/resources/fr/drinked/config/ the config.properties.example file.</li>
    <li>You will copy paste it in the same directory and rename it to "config.properties".</li>
    <li>You will then enter your database (mysql) username and password.</li>
    <li>As it is in a conf.properties file which was gitignored, it will never be on git, so you can safely enter your 
        credentials, this is a simple trick to secure the application.</li>
</ol>