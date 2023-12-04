package com.company;
import java.io.*;
import java.util.*;

public class Knapsack {
    private static List<List<Integer>> projects; // Declare it as a class-level variable

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of available employee work weeks: ");
        int availableWorkWeeks = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the name of the input file: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter the name of the output file: ");
        String outputFile = scanner.nextLine();

        projects = readProjects(inputFile); // Assign the result to the class-level variable

        int[][] dp = solveKnapsack(availableWorkWeeks);

        List<Integer> selectedProjects = getSelectedProjects(dp);

        writeOutput(outputFile, availableWorkWeeks, selectedProjects);

        scanner.close();
    }

    static List<List<Integer>> readProjects(String inputFile) {
        List<List<Integer>> projects = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(inputFile))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    List<Integer> project = new ArrayList<>();
                    project.add(Integer.parseInt(parts[1])); // Labor Demand
                    project.add(Integer.parseInt(parts[2])); // Profit
                    projects.add(project);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFile);
            System.exit(1);
        }

        return projects;
    }

    static int[][] solveKnapsack(int availableWorkWeeks) {
        int numProjects = projects.size();
        int[][] dp = new int[numProjects + 1][availableWorkWeeks + 1];

        for (int i = 1; i <= numProjects; i++) {
            List<Integer> project = projects.get(i - 1);
            int laborDemand = project.get(0);
            int profit = project.get(1);
            for (int j = 0; j <= availableWorkWeeks; j++) {
                if (laborDemand > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - laborDemand] + profit);
                }
            }
        }

        return dp;
    }

    static List<Integer> getSelectedProjects(int[][] dp) {
        List<Integer> selectedProjects = new ArrayList<>();
        int i = projects.size();
        int j = dp[0].length - 1;

        while (i > 0 && j > 0) {
            if (dp[i][j] != dp[i - 1][j]) {
                selectedProjects.add(i - 1); // Index of the selected project
                j -= projects.get(i - 1).get(0); // Deduct labor demand
            }
            i--;
        }

        Collections.reverse(selectedProjects);
        return selectedProjects;
    }

    static void writeOutput(String outputFile, int availableWorkWeeks, List<Integer> selectedProjects) {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.println("Number of projects available: " + projects.size()); // Corrected
            writer.println("Available employee work weeks: " + availableWorkWeeks);
            writer.println("Number of projects chosen: " + selectedProjects.size());

            int totalProfit = 0;
            for (int projectIndex : selectedProjects) {
                List<Integer> project = projects.get(projectIndex);
                int laborDemand = project.get(0);
                int profit = project.get(1);
                writer.println("Project" + projectIndex + " " + laborDemand + " " + profit);
                totalProfit += profit;
            }

            writer.println("Total profit: " + totalProfit);
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to the output file: " + outputFile);
            System.exit(1);
        }
    }
}
