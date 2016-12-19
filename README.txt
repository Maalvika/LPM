/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
 * Colorado State University
 * Department of Computer Science,
 * Fort Collins, CO  80523-1873, USA
 *
 * E-mail contact:
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 *   
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
*/

Linux Policy Machine (LPM) - managing the application-level OS resource control
and inter-application interaction in the "containerized" Linux environments
- project sponsored by the research grant from the National Institute of 
Standards and Technology (NIST) - http://www.nist.gov/

Please refer to the documents in the docs folder.

ADDITIONAL WORK for term project: 

TASK 1: Refactor the existing code
I worked on Parser_implement class.
Operation involved in the task:
1. Based upon Single Responsibility Principle, I had split the Parser_implement into 3 classes.
a. Parser_implement - The responsibility of this class is to parse the incoming command. 
It accepts the command and check it against various command formats supported by LPM.
b. Parser_validate - It validates the command received against the command supported by LPM. 
It checks the proper format of the command received. The various if-else statements were converted into different case statements.
c. Parse_and_Execute - Parser_validation class uses this class. 
It contains the actions are performed once the command is identified.

TASK 2: Add CHANGE_PASSWORD command and authentication
CHANGE_PASSWORD command requires the interaction with Parser layer to interpret the it and check if valid arguments are present or not. If Parser parses the command successfully, then values are sent to DB layer.
In DB layer, a user authentication table exits which check whether old password is correct or not. After satisfying all conditions, the password is changed in the database.
New Classes added to accommodate this command:
1. UserAuthDAO – It is an interface that provides functionality to access the user authentication database.
2. UserAuthDAO_impl: It contains the implementation for UserAuthDAO.
3. New constants were added in DB_Constants.

TASK 3: Enable Remote Log-in
In order to work on this task, java.net.Socket and java.net.ServerSockets class are used. 
The server will run on port 9001. Only one client will be able to connect to server at a point of time.


