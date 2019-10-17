Project Title: P2P Chat
Course: CSE 362, Computer Networking Lab
Submitted by: Md Atik Shahriar (2016331027), Md Nurul Afsar Mahmud (2016331046)

This is a peer to peer messaging app which send and receive message using Socket of server type and client type. This app can connect only two peer at a time. While connected, peers can send and receive message in real time. But both peers must be under same network.


<<<<<<Features>>>>>>
1. The app can create a server using preset port number or user defined port number.
2. Client can connect to server using preset or user defined IP address and port number of Server.
3. The app can act both as a server and client.
4. After successfull connection between server and client, messaging can be done.
5. The timestamp is shown in each message. 
6. Show device's IP address inside app.
7. Clean and sleek user interface with Navigation Drawer.
8. User will be able to change background from a list of background in settings.
9. User will be able to change theme color from a list of colors in settings.

<<<<<<Requirements>>>>>>
1. Minimum Android SDK version 21
2. Wifi

<<<<<<How to run the system?>>>>>>
Step 1: The app can be installed directly from apk or from Android Studio project. 
Step 2: To get the apk following steps should be followed:
	- Open the project in Android Studio
	- Click the dropdown menu in the toolbar at the top
	- Select "Edit Configurations"
	- Click the "+"
	- Select "Gradle"
	- Choose module as a Gradle project
	- In Tasks: enter assemble
	- Press Run
	- The unsigned APK will be located in ProjectName\app\build\outputs\apk
Step 3: To install from Android Studio following steps should be followed:
	- Open the project in Android Studio
	- Click on run button while connecting desired device through USB cable.

<<<<<<How the system works?>>>>>>
1. When the app starts user will have two option two connect with other user.
    - Creating a server and wait for other user to connect to that server as a client.
    - Connecting to other server created by other user by creating a client.
2. User can change own server port before creating server from "Update Server" option of Navigation Drawer .
3. On the other hand user can also change client IP address and port number before connecting to other server from "Update Client" option of Navigation Drawer.
4. After Successful connection between server and client both user can go to "Chat Box" option from Navigation Drawer where they can message in real time.
5. User can change different settings in "Settings" option of Navigation Drawer.
6. When a user sends a message, the app emits the message to the server via a socket connection.
7. When the server gets the emitted message, it checks the sender & receiver info attached to it. Then it emits the message to the receiver client.
8. The server app is always checking the connected sockets to it. Then it detects the user by socket ID and broadcasts a list of active users to clients.

<<<<<<Why it is correct?>>>>>>
A peer-to-peer network is designed around the notion of equal peer nodes simultaneously functioning as both "clients" and "servers" to the other nodes on the network. The characteristics are written below:
1. In this architecture each workstation, or node, has the same capabilities and responsibilities. Our app also has same characteristic as both client and server need same type of device to connect.
2. Peer-to-peer network also refer to a single software program designed so that each instance of the program may act as both client and server, with the same responsibilities and status. Our app also has same characteristic as both client and server use same app.
3. A peer-to-peer network application interacts directly with socket to establish communication and to send or receive information. Our apps are also using socket to establish a connection and send or receive information.
4. peer-to-peer network also use very short resource, thus it's more efficient and fast. Our app is no different.

As our system is satisfying the base requirements of peer-to-peer architecture and also serves the purposes of a chatting application, we can say that it is correct.

<<<<<<What Md Atik Shahriar (2016331027) have separately done?>>>>>>
1. Implemented the messaging procedure using server, client class and input and output Stream.
2. Designed the UI of the app.
3. Implemented Navigation Drawer and RecyclerView.
4. Debugging.

<<<<<<What Md Nurul Afsar Mahmud (2016331046) have separately done?>>>>>>
1. Planned and initiated the project structure.
2. Implemented server and client class.
3. Implemented home and settings fragment.
4. Documented everything.
