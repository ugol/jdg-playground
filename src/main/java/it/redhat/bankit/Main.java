package it.redhat.bankit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        InfinispanDAO dao = new InfinispanDAO();
        JDGNode node = null;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("node1")) {
                node = new Node1();
            } else if (args[0].equalsIgnoreCase("node2")) {
                node = new Node2();
            } else {
                System.out.println("usage: node");
                System.exit(-1);
            }
        }

        banner(node);
        dao.connect(node);
        TextUI ui= new TextUI(dao, System.in, System.out);
        ui.processCommands();

    }

    private static void banner(JDGNode node) {

        System.out.println("---------------------------------------");
        System.out.println("         Bankit JDG Testing CLI");
        System.out.println("             " + node.toString());
        System.out.println("            written by uL");
        System.out.println("---------------------------------------");
        System.out.println();

    }

}