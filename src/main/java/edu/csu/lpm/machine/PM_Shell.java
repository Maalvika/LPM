/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
 */
package edu.csu.lpm.machine;

import edu.csu.lpm.implementation.Parser_implement;
import edu.csu.lpm.interfaces.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class PM_Shell {

    private Parser_implement p = Parser_implement.getInstance();
    private PrintWriter out;
    private BufferedReader in;

    public PM_Shell(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    private void show_Prompt() {
        out.print("tinyPM::<>");
    }

    public void verify() throws IOException, SQLException, Exception {

        out.println("Enter username");
        String username = in.readLine();
        out.println("Enter password");
        String password = in.readLine();
        if (!username.equals(p.getUserAuthDAO().getUsernameFromDB())
                || !password.equals(p.getUserAuthDAO().getPasswordFromDB())) {
            out.println("Invalid Details!!!! Unable to log in");
        } else {
            process_UserInput();
        }

    }

    private void process_UserInput() throws Exception {
        int x = -1;

        for (;;) { //shell loop starts

            show_Prompt();
            //TODO: pass out reference so that we can write 
            x = p.parse_and_execute_Command(in.readLine().trim());
            out.println(p.get_ERROR_MESSAGE());
            out.println(p.get_ResultOutput());
            if (x == Parser.INDICATE_IMMEDIATE_EXIT_STATUS) {
                break;
            }
        } //end of for loop
    }

}
