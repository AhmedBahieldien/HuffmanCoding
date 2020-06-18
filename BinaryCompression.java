package com.company;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


class FrequencyComparator2 implements Comparator<BinaryTreeNode> {
    public int compare(BinaryTreeNode x, BinaryTreeNode y)
    {

        return x.data - y.data;
    }
}

public class BinaryCompression {
    PriorityQueue<BinaryTreeNode> frequencyQueue = new PriorityQueue<BinaryTreeNode>(30, new  FrequencyComparator2());
    Map<String, String> codeMap = new HashMap<String, String>();
    PrintWriter writeOutput;
    String decodingString = "";
    StringBuilder inputStringBuilder = new StringBuilder("");
    StringBuilder decodingStringBuilder = new StringBuilder("");
    StringBuilder outputStringBuilder = new StringBuilder("");

    void readCharacterString() throws Exception
    {
        String inputString = "";

        FileReader fr = new FileReader("binaryInput.txt");

        int i;
        while ((i=fr.read()) != -1)
            inputStringBuilder.append((char) i);
        inputString = inputStringBuilder.toString();
        //System.out.println(inputString);
        fr.close();
        //System.out.println("Input length = "+inputString.length());
        calculateWeight(inputString, inputString.length());
    }
    void calculateWeight(String inputString,int n)
    {
        String[] inputArray = new String[(n/8) + 1];

        int j = 0, k = 0;
        String tempString = "";
        for(int i = 0; i<n; i++)
        {
            if(j == 8)
            {
                inputArray[k] = tempString;
                j = 0;
                k ++;
                tempString = "";
                i--;
            }
            else
            {
                tempString = tempString + inputString.charAt(i);
                j++;
            }

        }
        //System.out.println("j = "+j+"k = "+k);
        inputArray[k] = tempString;
        j = 0;
        k ++;


        Arrays.sort(inputArray,0,k);

        int characterCounts = 1;
        for(int i=1; i<k; i++)
        {
            if(inputArray[i].equals(inputArray[i-1]))
                characterCounts ++;
            else
            {
                BinaryTreeNode treeNode = new BinaryTreeNode();
                treeNode.str = inputArray[i - 1];
                treeNode.data = characterCounts;
                treeNode.left = null;
                treeNode.right = null;
                frequencyQueue.add(treeNode);
                characterCounts = 1;
            }
        }
        BinaryTreeNode treeNode = new BinaryTreeNode();
        treeNode.str = inputArray[k - 1];
        treeNode.data = characterCounts;
        treeNode.left = null;
        treeNode.right = null;
        frequencyQueue.add(treeNode);

        //printFrequency();
        treeConstruction(inputString, n);

    }

    void printFrequency()
    {
        PriorityQueue<BinaryTreeNode> printQueue = frequencyQueue;
        while(!printQueue.isEmpty())
        {
            BinaryTreeNode treeNode = new BinaryTreeNode();
            treeNode = printQueue.poll();
            //System.out.println(treeNode.str+" "+treeNode.data);
        }
    }

    void treeConstruction(String inputString, int n)
    {
        long starttime = System.nanoTime();
        BinaryTreeNode root = new BinaryTreeNode();
        while(frequencyQueue.size() >= 2)
        {
            BinaryTreeNode inputNode1 = new BinaryTreeNode();
            BinaryTreeNode inputNode2 = new BinaryTreeNode();
            BinaryTreeNode outputNode = new BinaryTreeNode();
            inputNode1 = frequencyQueue.poll();
            inputNode2 = frequencyQueue.poll();
            outputNode.data = inputNode1.data + inputNode2.data;
            outputNode.str = null;
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

    void generateCode(BinaryTreeNode root, String stringCode)
    {
        if(root.str != null)
        {
            decodingStringBuilder.append(root.str + "" + stringCode + "\n");
            //decodingString = decodingString + root.str + "" + stringCode + "\n";
            codeMap.put(root.str, stringCode);
            return;
        }
        generateCode(root.left, stringCode+"0");
        generateCode(root.right, stringCode+"1");
    }

    void writeInFolder(String inputString, int n)
    {
        try {
            writeOutput = new PrintWriter(
                    new FileWriter("binaryoutput.txt"));
//			String outputString = "";
//			String tempString = "";
            outputStringBuilder = new StringBuilder("");
            StringBuilder tempStringBuilder = new StringBuilder("");
            int j = 0;
            for(int i = 0; i<n; i++)
            {
                if(j == 8)
                {
                    outputStringBuilder.append(codeMap.get(tempStringBuilder.toString()));
//					outputString = outputString + codeMap.get(tempString);
                    j = 0;
                    tempStringBuilder = new StringBuilder("");
                    i--;
                }
                else
                {
                    j ++;
                    tempStringBuilder.append(inputString.charAt(i));
                    //tempString = tempString + inputString.charAt(i);
                }
            }
            outputStringBuilder.append(codeMap.get(tempStringBuilder.toString()));
            writeOutput.write(decodingString + outputStringBuilder.toString());
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

