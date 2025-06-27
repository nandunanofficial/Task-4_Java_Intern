package Task_4;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class NotesApp {
    private static final String NOTES_DIRECTORY = "notes/";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Create notes directory if it doesn't exist
        File directory = new File(NOTES_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== Notes App ===");
            System.out.println("1. Create a new note");
            System.out.println("2. View all notes");
            System.out.println("3. Read a note");
            System.out.println("4. Delete a note");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        createNote();
                        break;
                    case 2:
                        viewAllNotes();
                        break;
                    case 3:
                        readNote();
                        break;
                    case 4:
                        deleteNote();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting Notes App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void createNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine().trim();

        // Replace spaces with underscores and remove special characters
        String filename = title.replaceAll("[^a-zA-Z0-9]", "_") + ".txt";

        System.out.println("Enter your note content (type 'END' on a new line to finish):");
        StringBuilder content = new StringBuilder();

        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            content.append(line).append("\n");
        }

        try (FileWriter writer = new FileWriter(NOTES_DIRECTORY + filename)) {
            writer.write(content.toString());
            System.out.println("Note saved successfully as: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    private static void viewAllNotes() {
        File folder = new File(NOTES_DIRECTORY);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No notes found.");
            return;
        }

        System.out.println("\nYour Notes:");
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println((i + 1) + ". " + files[i].getName().replace(".txt", ""));
            }
        }
    }

    private static void readNote() {
        viewAllNotes();
        File folder = new File(NOTES_DIRECTORY);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        System.out.print("Enter the number of the note to read: ");
        try {
            int noteNumber = Integer.parseInt(scanner.nextLine());
            if (noteNumber < 1 || noteNumber > files.length) {
                System.out.println("Invalid note number.");
                return;
            }

            File selectedFile = files[noteNumber - 1];
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                System.out.println("\n=== " + selectedFile.getName().replace(".txt", "") + " ===");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IOException e) {
            System.out.println("Error reading note: " + e.getMessage());
        }
    }

    private static void deleteNote() {
        viewAllNotes();
        File folder = new File(NOTES_DIRECTORY);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        System.out.print("Enter the number of the note to delete: ");
        try {
            int noteNumber = Integer.parseInt(scanner.nextLine());
            if (noteNumber < 1 || noteNumber > files.length) {
                System.out.println("Invalid note number.");
                return;
            }

            File selectedFile = files[noteNumber - 1];
            if (selectedFile.delete()) {
                System.out.println("Note deleted successfully.");
            } else {
                System.out.println("Failed to delete note.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}
