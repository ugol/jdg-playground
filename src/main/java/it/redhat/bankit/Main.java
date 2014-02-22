package it.redhat.bankit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        JDGNode node = null;

        if (args.length > 0) {
            node = new JDGNode(args[0]);
        } else {
            System.out.println("usage: node");
            System.exit(-1);
        }

        banner(node);
        new JDG().connect(node).attachUI(new TextUI(System.in, System.out)).processCommands();

    }

    private static void banner(JDGNode node) {

        System.out.println("---------------------------------------");
        System.out.println("         Bankit JDG Testing CLI");
        System.out.println("            written by uL");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("Connecting node: " + node);

    }

}