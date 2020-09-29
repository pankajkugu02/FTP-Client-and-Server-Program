# Note
Sir Please read the features and instruction carefully.
<br>Every code is running there is no compilation error, If any doubt please contact us.
# Features
 **We can share file of any size using code in Folder "FTP using Stop and Wait and IPV6" between two    remote PCs.
 <br>**We can share file of small size using code in Folder "FTP using go and back-n and IPV6" between two remote PCs but it is taking very much time for large file.
 <br>**We can share files in localhost network using steps written in heading "How to run in local host" and compare the results. 
 
 
# FTP-Client-and-Server-Program  Progress
Progress-1:- Till now we have written code for client and server. Client will send "Hello Server" then Server will send "Hello Client".

Progress-2:- Then we updated our code,and now client will send a message to server to send the directory ,the server will send the directory to the client 
             and will ask client which file do you want,then client will reply and then server will send that file.

Progress-3:- Now we have created same thing as above using go back n protocol.

Progress-4:- Then we have implemented both Go Back n and Stop and Wait Protocol and then we have compared transferring time of both the protocols.<br>
             After comparison we come to know that the Go Back N is performing good in case of big files in comparison to Stop and Wait .<br>
             We have run the code for a file of 10MB for which :-<br>Go Back N takes : 5 seconds<br>Stop and Wait takes : 9-10 seconds

Progress-5: We tried to implement it using ipv6 address such that we can share files between two remote machines and we succeeded.           

# How to run for local host 
Step-1:-Download the repository to your PC.

Step-2:- Put files naming ACKpacket.java, Client1.java, Helper.java, Packet.java,Server1.java to a new 
          folder and save it where you want.
Step-3:-Now open two Command Prompt Windows one to run Server code and other to run Client code.

Step-4:-Change the directory of each cmd to folder where you saved the folder created in step 2.

Step-5:- Use command "javac Server1.java" and "javac Client1.java" to compile Server and Client code respectively.

Step-6:-First Run Server code by command "java Server1" then run Client code using command " java Client1 "
        and the follow the steps instructed by command prompts.
        
# How to run codes in "FTP using go and back-n and IPV6" folder for remote PC Sharing
Step-1:-Download the repository to your PC.

Step-2:-Put all the files of folder "FTP using go and back-n and IPV6" in one folder in your PC.

Step-3:-Make your friend as Client and you as Server and share all the files to your friend and change directory in Server code as per your PC.

Step-4:-First Run "Server.java" code on your side and then tell your friend to run "Client.java" code and then follow the steps.
# How to run codes in "FTP using Stop and Wait and IPV6" folder for remote PC Sharing
Step-1:-Download the repository to your PC.

Step-2:-Put all the files of folder "FTP using Stop and Wait and IPV6" in one folder in your PC.

Step-3:-Make your friend as Client and you as Server and share all the files to your friend and change directory in Server code as per your PC.

Step-4:-First Run Server.java code on your side and then tell your friend to run Client.java code and then follow the steps.
        
 # Note-1:- Use your directory path in Server code.
 # Note-2:- For localhost sharing try to send large files like more than 10 mb file then go back n will take lesser time than stop and wait,on average they will take 8-10 seconds. 
            It is because we do many operations in Go back n Protocol which take time and when we share small size file this time matters
            but when we send large files then result will be correct.   
 

