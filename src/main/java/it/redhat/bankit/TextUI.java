package it.redhat.bankit;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

public class TextUI {

    public TextUI(InfinispanDAO dao, InputStream in, PrintStream out) {
        this.dao = dao;
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public void processCommands() throws IOException {

        boolean keepRunning = true;
        while(keepRunning) {
            System.out.print("> ");
            out.flush();
            String line = in.readLine();
            if(line == null) {
                break;
            }
            keepRunning = processLine(line);
        }
    }

    private boolean processLine(String line) {


        Scanner scanner = new Scanner(line);

        if(readCommand(scanner, "put") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            dao.put(id, value);
            out.println("Written (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "get") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            out.println(dao.get(id));
        }

        else if(readCommand(scanner, "modify") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            out.println(dao.modify(id, value));
            out.println("Modified (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "all")) {
            //printValues(dao.keySetOnServer());
        }

        else if(readCommand(scanner, "local")) {
            printValues(dao.keySet());
        }

        else if(readCommand(scanner, "clear")) {
            dao.clear();
        }

        else if(readCommand(scanner, "lock")) {
            //dao.lock();
        }

        else if(readCommand(scanner, "unlock")) {
            //dao.unlock();
        }

        else if(readCommand(scanner, "grantor")) {
            //dao.grantor();
        }

        else if (readCommand(scanner, "exit|quit|q|x")) {
            return false;
        }
        else {
            usage();
        }

        return true;
    }

    private void printValues(Set<String> values) {
        for(String person : values) {
            out.println(person);
        }
    }

    private void usage() {
        out.println("Commands:");
        out.println("get id");
        out.println("     Get a String.");
        out.println("put id text");
        out.println("     Add a String.");
        out.println("all");
        out.println("     List all valuesFromKeys.");
        out.println("local");
        out.println("     List all local valuesFromKeys.");
        out.println("clear");
        out.println("     Clear all valuesFromKeys.");
        out.println("lock");
        out.println("     Get the distributed lock.");
        out.println("unlock");
        out.println("     Release the distributed lock.");
        out.println("grantor");
        out.println("     See if the current system is grantor for locks.");
        out.println("quit");
        out.println("     Exit the shell.");
    }

    private boolean readCommand(Scanner scanner, String command) {
        if(scanner.hasNext(command)) {
            scanner.next(command);
            return true;
        }
        return false;
    }

    private final InfinispanDAO dao;
    private final BufferedReader in;
    private final PrintStream out;

}