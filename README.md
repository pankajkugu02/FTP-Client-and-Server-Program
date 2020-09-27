# FTP-Client-and-Server-Program
Progress-1:- Till now we have written code for client and server .Client will send "Hello Server" then Server will send "Hello Client".

Progress-2:- Then we updated our code,and now client will send a message to server to send the directory ,the server will send the directory to the client 
             and will ask client which file do you want,then client will reply and then server will send that file.

Progress-3:- Now we have created same thing as above using go back n protocol.

Progress-3:- Then we have implemented both Go Back n and Stop and Wait Protocol and then we have compared transferring time of both the protocols.<br>
             After comparison we come to know that the Go Back N is performing good in case of big files in comparison to Stop and Wait .<br>
             We have run the code for a file of 10MB for which :-<br>Go Back N takes : 5 seconds<br>Stop and Wait takes : 9-10 seconds

Progress-4: We tried to implement it using ipv6 address such that we can share files between two remote machines but didn't get success. 
            We will try to continue this project.

# How to run
Step-1:-Download the repository to your PC.

Step-2:- Put files naming ACKpacket.java, Client1.java, Helper.java, Packet.java,Server1.java to a new 
          folder and save it where you want.
Step-3:-Now open two Command Prompt Windows one to run Server code and other to run Client code.

Step-4:-Change the directory of each cmd to folder where you saved the folder created in step 2.

Step-5:- Use command "javac Server1.java" and "javac Client.java" to compile Server and Client code respectively.

Step-6:-First Run Server code by command "java Server1" then run Client code using command java Client1
        and the follow the steps instructed by command prompts.
        
 # Note-1:- Use your directory path in Server code line no-35
 # Note-2:- Try to send large files like more than 10 mb file then go back n will take lesser time than stop and wait 
            It is because we do many operations in Go back n Protocol which take time and when we share small size file this time matters
            but when we send large files then result will be correct.

