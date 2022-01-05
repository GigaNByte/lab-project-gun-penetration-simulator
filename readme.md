#  Gun Penetration Simulator : Java+JavaFX+Hibernate+SQLite CRUD Sample App

<hr>


This App provides graphical interface to manipulate Tank Guns and Tanks/Vehicles datasets. Allows to create, remove, modify and delete them and perform simplified fire tests between vehicles/tanks based on guns penetration/balistic tables.

Demo database contains some WWII German and Soviet normalized gun&shell test data based on US Naval Ballistic Limit Penetration criteria (RHA Steel BHN 270)

<hr>

# Basic usage
 
### Gun CRUD Interface:

View consists of tableview of all avaible guns in database.
Gun form allows to create new Guns by entering gun data textfields and spinners. 
 In Tableview we can delete single records using "Delete" button, or examine and edit penetration table parameters or edit existing gun details using "Edit" button.

![](./readme/1.gif) 
![](./readme/1.png)

### Gun Edit Penetration Table CRUD Interface:

After editing details press "Update Gun Data" button to commit changes.

![](./readme/2.png)


 
 ### Vehicle CRUD Interface:

 Application provides similar interface to create vehicle. To add new vehicle, fill a form, select gun from existing database and press "Add Vehicle.
 
 ![](./readme/3.png)
 ![](./readme/2.gif)

 ### FireTest CRUD Interface:

To perform Firetests you need to provide test vehicle from which we want to shoot, and target vehicle with targeted tank/vehicle part (Front or Side hull). 
You can provide secondary angle witch shell will hit the armor (to simulate shooting from highground). App will caluclate if shell will penetrate armor, based on penetration tables from gun form. 

 ![](./readme/4.png)
 ![](./readme/3.gif)

<hr>

# ERD Diagram
![](./readme/erd.png)
<hr>

# Demo dataset sources
[http://www.panzer-war.com/page59.html](http://www.panzer-war.com/page59.html)  
[http://www.panzer-war.com/page58.html](http://www.panzer-war.com/page58.html)
[https://panzerworld.com/](https://panzerworld.com/)  
[http://amizaur.prv.pl/www.wargamer.org/GvA/weapons/soviet.html](http://amizaur.prv.pl/www.wargamer.org/GvA/weapons/soviet.html)  
[http://amizaur.prv.pl/www.wargamer.org/GvA/weapons/germany.html](http://amizaur.prv.pl/www.wargamer.org/GvA/weapons/germany.html)

