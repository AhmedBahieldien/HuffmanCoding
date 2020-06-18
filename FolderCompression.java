package com.company;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class FolderCompression {
    PriorityQueue<TreeNode> frequencyQueue = new PriorityQueue<TreeNode>(30, new  FrequencyComparator());
    Map<Character, String> codeMap = new HashMap<Character, String>();
    PrintWriter writeOutput;
    String decodingString = "";
    StringBuilder decodingStringBuilder = new StringBuilder("");
    File folder = new File("B://College//Term 7//Algorithms//HuffmanCode//inputFolder");
    File[] listOfFiles = folder.listFiles();
    int[] numberOfLettersInFile = new int[listOfFiles.length];
    StringBuilder inputStringBuilder = new StringBuilder("");
    StringBuilder outputStringBuilder = new StringBuilder("");

    void readCharacterString() throws Exception
    {
        String inputString = "";
        FileReader fr ;

        int i = 0;
        String[] fileNames = new String[listOfFiles.length];
        for (File file : listOfFiles) {
            if(file.isFile()) {
                fileNames[i] = file.getName();
                //System.out.println(fileNames[i]);
                i++;
            }
        }

        for(int j = 0; j<listOfFiles.length; j++)
        {
            i = 0;
            int letterCounter = 0;
            fr = new FileReader("inputFolder//"+fileNames[j]);
            while ((i=fr.read()) != -1)
            {
                inputStringBuilder.append((char) i);
                letterCounter++;
            }
            numberOfLettersInFile[j] = letterCounter;
            //inputStringBuilder.append((char)10);
            fr.close();
        }
        inputString = inputStringBuilder.toString();
        //System.out.println(inputString);
        calculateWeight(inputString, inputString.length());
    }
    void calculateWeight(String inputString,int n)
    {
        char[] inputArray = inputString.toCharArray();
        Arrays.sort(inputArray);

        int characterCounts = 1;
        for(int i=1; i<n; i++)
        {
            if(inputArray[i] == inputArray[i-1])
                characterCounts ++;
            else
            {
                TreeNode treeNode = new TreeNode();
                treeNode.c = inputArray[i - 1];
                treeNode.data = characterCounts;
                treeNode.left = null;
                treeNode.right = null;
                frequencyQueue.add(treeNode);
                characterCounts = 1;
            }
        }
        TreeNode treeNode = new TreeNode();
        treeNode.c = inputArray[n - 1];
        treeNode.data = characterCounts;
        treeNode.left = null;
        treeNode.right = null;
        frequencyQueue.add(treeNode);

        //printFrequency();
        treeConstruction(inputString, n);

    }

    void printFrequency()
    {
        PriorityQueue<TreeNode> printQueue = frequencyQueue;
        while(!printQueue.isEmpty())
        {
            TreeNode treeNode = new TreeNode();
            treeNode = printQueue.poll();
            //System.out.println(treeNode.c+" "+treeNode.data);
        }
    }

    void treeConstruction(String inputString, int n)
    {
        long starttime = System.nanoTime();
        TreeNode root = new TreeNode();
        while(frequencyQueue.size() >= 2)
        {
            TreeNode inputNode1 = new TreeNode();
            TreeNode inputNode2 = new TreeNode();
            TreeNode outputNode = new TreeNode();
            inputNode1 = frequencyQueue.poll();
            inputNode2 = frequencyQueue.poll();
            outputNode.data = inputNode1.data + inputNode2.data;
            outputNode.c = 0; //ASCII-CODE for null
            outputNode.left = inputNode1;
            outputNode.right = inputNode2;
            root = outputNode;
            frequencyQueue.add(outputNode);
        }
        generateCode(root, "");
        long endtime = System.nanoTime();
        long totaltime = endtime - starttime;
        System.out.println("Decompression runtime:" + totaltime);
        writeInFolder(inputString, n);
    }

    void generateCode(TreeNode root, String stringCode)
    {
        if(root.c != 0)
        {
            decodingStringBuilder.append(("00000000"+Integer.toBinaryString(root.c)).substring(Integer.toBinaryString(root.c).length()) + "" + stringCode + "\n");
            //decodingString = decodingString + ("00000000"+Integer.toBinaryString(root.c)).substring(Integer.toBinaryString(root.c).length()) + "" + stringCode + "\n";
            codeMap.put(root.c, stringCode);
            return;
        }
        generateCode(root.left, stringCode+"0");
        generateCode(root.right, stringCode+"1");
    }

    void writeInFolder(String inputString, int n)
    {
        try {
            writeOutput = new PrintWriter(
                    new FileWriter("folderOutput.txt"));
            int charAdder = 0;
            for(int j = 0; j<listOfFiles.length; j++)
            {
                for(int i = 0; i<listOfFiles[j].length(); i++)
                {
                    outputStringBuilder.append(codeMap.get(inputString.charAt(i + charAdder)));
                }
                charAdder = (int) (charAdder + listOfFiles[j].length());
                outputStringBuilder.append("\n");
            }
            writeOutput.write(Integer.toBinaryString(listOfFiles.length)+"\n"+decodingStringBuilder.toString() + outputStringBuilder.toString());
            writeOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void compressionRatio()
    {
        float i = inputStringBuilder.toString().length();
        float j =(decodingStringBuilder.toString().length() + outputStringBuilder.toString().length());
        float k = (j/i)*100;
        System.out.println("Compression ratio = "+k);
    }
}
