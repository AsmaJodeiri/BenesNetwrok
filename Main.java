package io.asma;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        final Map<Integer, Integer> inputMap = new HashMap<>();

        inputMap.put(1, 2);
        inputMap.put(2, 5);
        inputMap.put(3, 6);
        inputMap.put(4, 4);
        inputMap.put(5, 8);
        inputMap.put(6, 7);
        inputMap.put(7, 3);
        inputMap.put(8, 1);

        int inputSize = inputMap.size();

        final Block grid = getBlockOf(inputSize, "");

        final ArrayList<Integer> visitedInputs = new ArrayList<>(inputSize);

        doCycle(inputMap, visitedInputs, 1, grid);
        System.out.println("\n------ first cycle finished ------");
        while (visitedInputs.size() != inputSize) {
            int unVisited = inputMap.keySet().stream().filter(input -> !visitedInputs.contains(input)).findFirst().get();
            doCycle(inputMap, visitedInputs, unVisited, grid);
        }

        if(visitedInputs.size() == inputSize) {
            System.out.println("\n\n----- Every input is visited\n----- finished");
        }
    }

    /**
     * Does a cycle until reaches an already visited input
     */
    static void doCycle(Map<Integer, Integer> inputMap, ArrayList<Integer> visitedInputs, int from, Block grid) {
        System.out.println("***** starting a cycle from input " + from + " *****");
        boolean reversed = false;
        int to = -1;

        while (true) {
            if (!reversed) {
                if (visitedInputs.contains(from)) {
                    System.out.println("got to visited input: " + from);
                    break;
                }
                to = inputMap.get(from);

            } else {
                final int currentFrom = from;
                to = inputMap.entrySet().stream()
                        .filter(entry -> entry.getValue() == currentFrom)
                        .findFirst()
                        .get()
                        .getKey();
            }
            System.out.println("\nfinding path: " + from + " -> " + to + ", reversed: " + reversed);

            findPath(grid, from, to, reversed);


            if (!reversed) {
                visitedInputs.add(from);
            } else {
                visitedInputs.add(to);
            }

             ////System.out.println("visited inputs: " + visitedInputs);

            if (to % 2 == 0) {
                from = to - 1;
            } else {
                from = to + 1;
            }

            reversed = !reversed;
        }
    }


    static void printCrossed(Block block) {
        if (block instanceof ComplexBlock) {
            for (SimpleBlock b : ((ComplexBlock) block).left.getAll()) {
                if (!b.up.isOpen()) {
                    System.out.println("block: " + b.name + " up, is occupied");
                }

                if (!b.down.isOpen()) {
                    System.out.println("block: " + b.name + " down, is occupied");
                }
            }
        }
    }


    /**
     * the recursive method to construct the structure of the blocks
     */
    static private Block getBlockOf(int size, String preName) {
        if (size == 2) {
            return new SimpleBlock(preName, new Connection(), new Connection());
        } else {
            final Column leftCol = new Column<SimpleBlock>();
            final Column rightCol = new Column<SimpleBlock>();

            final Column midCol = new Column<Block>();

            for (int i = 1; i <= size / 2; i++) {
                leftCol.add(new SimpleBlock(preName + " " + i, new Connection(), new Connection()));
                rightCol.add(new SimpleBlock(preName + " " + (i + size / 2), new Connection(), new Connection()));
            }

            String prevPreName = preName;

            if (preName.equals("")) {
                preName = "a";
            } else {
                char firstChar = preName.charAt(0);
                String nextChar = String.valueOf((char) (firstChar + 1));
                preName = nextChar + "_" + preName;
            }

            midCol.add(getBlockOf(size / 2,  preName + "1"));
            midCol.add(getBlockOf(size / 2, preName + "2"));


            return new ComplexBlock(leftCol, midCol, rightCol);
        }
    }


    /**
     * the recursive method to find a path between an input and an output
     */
    static private void findPath(Block block, int from, int to, boolean reversed) {
        if (block instanceof SimpleBlock) {
            System.out.println("crossing " + ((SimpleBlock) block).name);
        } else if (block instanceof ComplexBlock) {

            final SimpleBlock fromBlock;
            final SimpleBlock toBlock;

            if (!reversed) {
                fromBlock = (SimpleBlock) ((ComplexBlock) block).left.getBlockByValue(from);
                toBlock = (SimpleBlock) ((ComplexBlock) block).right.getBlockByValue(to);
            } else {
                fromBlock = (SimpleBlock) ((ComplexBlock) block).right.getBlockByValue(from);
                toBlock = (SimpleBlock) ((ComplexBlock) block).left.getBlockByValue(to);
            }

            String way = "";
            int middleGridIndex = -1;
//            printCrossed(block);

            if (fromBlock.up.isOpen() && toBlock.up.isOpen()) {
                way = "up";
                middleGridIndex = 0;
                fromBlock.up.occupy(from + "->" + to);
                toBlock.up.occupy(from + "->" + to);

            } else if (fromBlock.down.isOpen() && toBlock.down.isOpen()) {
                way = "down";
                middleGridIndex = 1;
                fromBlock.down.occupy(from + "->" + to);
                toBlock.down.occupy(from + "->" + to);
            } else {
                System.out.println("no way");
                return;
            }

            System.out.println("from " + fromBlock.name + " to center " + way);
            findPath(((ComplexBlock) block).mid.getBlockByIndex(middleGridIndex), (int) Math.ceil(from / 2.0), (int) Math.ceil(to / 2.0), reversed);
            System.out.println("going to " + toBlock.name);

        }
    }

}


