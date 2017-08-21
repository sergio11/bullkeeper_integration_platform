# spring-integration-sample
A Spring Integration Sample Applying EIPs

## Deployment Guide

Check the **JDK** used by default.

```
  sudo update-alternatives --config java
  error: no hay alternativas para java
```
Install JDK package with apt-get


```
  sudo apt-get install default-jdk -Y

```
Check the version of the installed JDK:

```
  java -version
  
  openjdk version "1.8.0_131"

```
For security purposes, Tomcat should be run as an unprivileged user (i.e. not root). We will create a new user and group that will run the Tomcat service.

First, create a new tomcat group:

```
 sudo groupadd tomcat
  
```

Next, create a new tomcat user. We'll make this user a member of the tomcat group, with a home directory of **/opt/tomcat** (where we will install Tomcat), and with a shell of **/bin/false** (so nobody can log into the account):

```
  sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat

```

Next, change to the **/tmp** directory on your server. This is a good directory to download ephemeral items, like the Tomcat tarball, which we won't need after extracting the Tomcat contents:

```
  cd /tmp
  
```

Use curl to download the link that you copied from the Tomcat website:

``` 
  curl -O "http://apache.rediris.es/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.tar.gz"
  
```

We will install Tomcat to the **/opt/tomcat** directory. Create the directory, then extract the archive to it with these commands:

```
  sudo mkdir /opt/tomcat
  sudo tar xzvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1
  
```

The tomcat user that we set up needs to have access to the Tomcat installation. We'll set that up now.

Change to the directory where we unpacked the Tomcat installation:

```
  cd /opt/tomcat

```

Give the tomcat group ownership over the entire installation directory:

```
  sudo chgrp -R tomcat /opt/tomcat
  
```

Next, give the tomcat group read access to the conf directory and all of its contents, and execute access to the directory itself:

```
  sudo chmod -R g+r conf
  sudo chmod g+x conf

```

Make the tomcat user the owner of the webapps, work, temp, and logs directories:

```
  sudo chown -R tomcat webapps/ work/ temp/ logs/
```

Open a file called tomcat.service in the /etc/systemd/system directory by typing:

```
  sudo nano /etc/systemd/system/tomcat.service

```
Next, reload the systemd daemon so that it knows about our service file:

```
  sudo systemctl daemon-reload
  
```

Start the Tomcat service by typing:

```
  sudo systemctl start tomcat
  
```

Double check that it started without errors by typing:

```
  sudo systemctl status tomcat
  
```

If you were able to successfully accessed Tomcat, now is a good time to enable the service file so that Tomcat automatically starts at boot:

```
  sudo systemctl enable tomcat
  
```

In order to use the manager web app that comes with Tomcat, we must add a login to our Tomcat server. We will do this by editing the tomcat-users.xml file:

```
 sudo vi /opt/tomcat/conf/tomcat-users.xml
 
```

You will want to add a user who can access the manager-gui and admin-gui (web apps that come with Tomcat). You can do so by defining a user:

```
 <tomcat-users>
     <user username="admin" password="password" roles="manager-gui,admin-gui"/>
 </tomcat-users>
```

By default, newer versions of Tomcat restrict access to the Manager and Host Manager apps to connections coming from the server itself. Since we are installing on a remote machine, you will probably want to remove or alter this restriction. To change the IP address restrictions on these, open the appropriate context.xml files.

For the Manager app, type:

```
 
 sudo vi /opt/tomcat/webapps/manager/META-INF/context.xml

```

For the Host Manager app, type:

```
 sudo vi /opt/tomcat/webapps/host-manager/META-INF/context.xml

```

Inside, comment out the IP address restriction to allow connections from anywhere. Alternatively, if you would like to allow access only to connections coming from your own IP address, you can add your public IP address to the list:

To put our changes into effect, restart the Tomcat service:

```
 sudo systemctl restart tomcat
 
```
