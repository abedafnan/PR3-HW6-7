package com.abedafnan.exercise3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Invoice invoice1 = new Invoice(83, "Electric sander", 7, 57.98);
        Invoice invoice2 = new Invoice(24, "Power saw", 18, 99.99);
        Invoice invoice3 = new Invoice(7, "Sledge hammer", 11, 21.50);
        Invoice invoice4 = new Invoice(77, "Hammer", 76, 11.99);
        Invoice invoice5 = new Invoice(39, "Lawn mower", 3, 79.50);
        Invoice invoice6 = new Invoice(68, "Screwdriver", 106, 6.99);
        Invoice invoice7 = new Invoice(56, "Jig saw", 21, 11.00);
        Invoice invoice8 = new Invoice(3, "Wrench", 34, 7.50);

        ArrayList<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);
        invoices.add(invoice6);
        invoices.add(invoice7);
        invoices.add(invoice8);

        DBConnection dbConnection = DBConnection.getDbConnection();
        Statement statement = dbConnection.createStatement();

        // Insert invoices into the database
        insertIntoDB(invoices, statement);

        // Retrieve the invoice table content
        ArrayList<Invoice> retrievedInvoices = queryTheDB(statement);
        printHeader("Original");
        retrievedInvoices.forEach(Main::printInvoice);
        System.out.println();

        // Sort the list of invoices based on the description
        printHeader("Sorted by Description");
        retrievedInvoices.stream()
                .sorted(Comparator.comparing(Invoice::getPartDescription))
                .forEach(Main::printInvoice);
        System.out.println();

        // Sort the list of invoices based on the price
        printHeader("Sorted by Price");
        retrievedInvoices.stream()
                .sorted(Comparator.comparing(Invoice::getPrice))
                .forEach(Main::printInvoice);
        System.out.println();

        // Map and sort the list of invoices based on the quantity
        System.out.println("Sorted by Quantity:\n--------------------------------------");
        retrievedInvoices.stream()
                .sorted(Comparator.comparing(Invoice::getQuantity))
                .flatMap(invoice -> Stream.of(invoice.getPartDescription(), invoice.getQuantity()))
                .forEach(System.out::println);
        System.out.println();

        // Map, sort and filter the list of invoices based on the invoice value
        System.out.println("Sorted and Filtered by Invoice Value:\n--------------------------------------");
        retrievedInvoices.stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceValue))
                .filter(invoice -> invoice.getInvoiceValue() >= 200 && invoice.getInvoiceValue() <= 500)
                .flatMap(invoice -> Stream.of(invoice.getPartDescription(), invoice.getInvoiceValue()))
                .forEach(System.out::println);
        System.out.println();
    }

    /**
     * @param invoices is the arrayList in which its content is going to be inserted into the database
     * @param statement is used to execute the query
     */
    private static void insertIntoDB(ArrayList<Invoice> invoices, Statement statement) {
        invoices.forEach(invoice -> {
            String query = "INSERT INTO invoice(part_number, part_description, quantity, price)" + "VALUES('"
                    + invoice.getPartNumber() + "', '" + invoice.getPartDescription() + "', '" + invoice.getQuantity()
                    + "', '" + invoice.getPrice() + "')";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @param statement is used to execute the query
     * @return arrayList of data selected from the database
     */
    private static ArrayList<Invoice> queryTheDB(Statement statement) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        try {
            String query = "SELECT * FROM invoice";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int partNumber = resultSet.getInt("part_number");
                String partDesc = resultSet.getString("part_description");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                invoices.add(new Invoice(partNumber, partDesc, quantity, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    /**
     * Prints the Invoice data in a formatted way
     */
    private static void printInvoice(Invoice invoice) {
        System.out.printf("%-15d %-20s %-15d %-15.2f\n",
                invoice.getPartNumber(), invoice.getPartDescription(), invoice.getQuantity(), invoice.getPrice());
    }

    /**
     * Prints the header of the Invoice data
     */
    private static void printHeader(String title) {
        System.out.println(title + ":\n------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-15s\n------------------------------------------------------------\n"
                , "Part Number", "Part Description", "Quantity", "Price");
    }

}


