package ru.vsu.cs.skofenko.consoleVersion;

import ru.vsu.cs.skofenko.CommonModule;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;

public class ConsoleMain {

    public static void main(String[] args) {
        try {
            InputArgs inputArgs = parseCmdArgs(args);
            List<Integer> inputList = CommonModule.readListFromFile(inputArgs.getInputFile());
            List<Integer> answer = CommonModule.createNewList(inputList);
            CommonModule.writeListToFile(inputArgs.getOutputFile(), answer);
        } catch (DoublePathException e) {
            System.err.println("Путь к " + e.getOfWhat() + " введён два раза");
            System.exit(-2);
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Файл ввода вывода не найден");
            System.exit(-3);
        } catch (NumberFormatException | InputMismatchException e) {
            System.err.println("Неверный список на вход");
            System.exit(-4);
        }
    }

    public static InputArgs parseCmdArgs(String[] args) throws DoublePathException {
        InputArgs inputArgs = new InputArgs();
        if (args.length == 2 && !args[0].contains("-i") && !args[0].contains("-o") && !args[0].contains("--input-file=")
                && !args[0].contains("–-output-file=") && !args[1].contains("-i") && !args[1].contains("-o") &&
                !args[1].contains("--input-file=") && !args[1].contains("–-output-file=")) {
            inputArgs.setInputFile(args[0]);
            inputArgs.setOutputFile(args[1]);
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("--input-file=")) {
                    if (inputArgs.getInputFile() != null)
                        throw new DoublePathException("input");
                    else
                        inputArgs.setInputFile(args[i].replace("--input-file=", ""));
                } else if (args[i].contains("–-output-file=")) {
                    if (inputArgs.getOutputFile() != null)
                        throw new DoublePathException("output");
                    else
                        inputArgs.setOutputFile(args[i].replace("–-output-file=", ""));
                } else if (args[i].contains("-i")) {
                    if (inputArgs.getInputFile() != null)
                        throw new DoublePathException("input");
                    else inputArgs.setInputFile(args[i + 1]);
                } else if (args[i].contains("-o")) {
                    if (inputArgs.getOutputFile() != null)
                        throw new DoublePathException("output");
                    else inputArgs.setOutputFile(args[i + 1]);
                }
            }
        }
        return inputArgs;
    }
}