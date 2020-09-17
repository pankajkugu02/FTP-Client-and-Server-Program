# FTP-Client-and-Server-Program
Till now we have written code for client and server .Client will send "Hello Server" then Server will send "Hello Client".

Then we updated our code,and now client will send a message to server to send the directory ,the server will send the directory to the client and will ask client which file do you want,then client will reply and then server will send that file.

Now we have created same thing as above using go back n protocol.

Then we have implemented both Go Back n and Stop and Wait Protocol and then we have compared transferring time of both the protocols.<br>
After comparison we come to know that the Go Back N is performing good in case of big files in comparison to Stop and Wait .<br>
We have run the code for a file of 10MB for which :-<br>Go Back N takes : 5 seconds<br>Stop and Wait takes : 9-10 seconds
