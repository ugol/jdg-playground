package it.redhat.bankit;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

public class TextUI {

    public TextUI(InputStream in, PrintStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public void processCommands() throws IOException {

        boolean keepRunning = true;
        while(keepRunning) {
            out.print("> ");
            out.flush();
            String line = in.readLine();
            if(line == null) {
                break;
            }
            keepRunning = processLine(line);
        }
    }

    public void setJdg(JDG jdg) {
        this.jdg = jdg;
    }

    private boolean processLine(String line) {


        Scanner scanner = new Scanner(line);

        if(readCommand(scanner, "put") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            jdg.put(id, value);
            out.println("Written (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "get") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            out.println(jdg.get(id));
        }

        else if(readCommand(scanner, "modify") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            out.println(jdg.modify(id, value));
            out.println("Modified (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "all")) {
            //printValues(jdg.keySetOnServer());
        }

        else if(readCommand(scanner, "local")) {
            printValues(jdg.keySet());
        }

        else if(readCommand(scanner, "clear")) {
            jdg.clear();
        }

        else if (readCommand(scanner, "exit|quit|q|x")) {
            jdg.shutdown();
            return false;
        }

        else if(readCommand(scanner, "help")) {
            usage();
        }

        else {
            out.println("> ");
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
        out.println("help");
        out.println("     List of commands.");
        out.println("quit");
        out.println("     Exit the shell.");
    }

    private boolean readCommand(Scanner scanner, String command) {
        if (scanner.hasNext(command)) {
            scanner.next(command);
            return true;
        }
        return false;
    }

    private JDG jdg;
    private final BufferedReader in;
    private final PrintStream out;

}