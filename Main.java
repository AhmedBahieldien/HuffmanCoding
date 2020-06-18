package com.company;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" 1.Compress \n 2.Decompress \n 3.exit");
            int c = scanner.nextInt();
            if (c == 1) {
                Compression compression = new Compression();
                BinaryCompression binaryCompression = new BinaryCompression();
                FolderCompression folderCompression = new FolderCompression();
                System.out.println("1.Text file compression\n2.Binary file compression\n3.Folder compression");
                int c2 = scanner.nextInt();
                if (c2 == 1) {
                    compression.readCharacterString();
                    compression.compressionRatio();
                } else if (c2 == 2) {

                    binaryCompression.readCharacterString();
                    binaryCompression.compressionRatio();
                } else if (c2 == 3) {

                    folderCompression.readCharacterString();
                    folderCompression.compressionRatio();
                }

            }
            else if(c==2){
                System.out.println("1.Text file decompression\n2.Binary file decompression\n3.Folder decompression");
                int c2 = scanner.nextInt();
                if(c2==1){
                    File file = new File("F:\\Huffman coding\\src\\com\\company\\b2.txt");
                    File file1 = new File("F:\\Huffman coding\\src\\com\\company\\output.txt");
                    Decompression decompression = new Decompression();
                    decompression.readfile(file);
                    long starttime = System.nanoTime();
                    decompression.buildtree();
                    decompression.decompress();
                    decompression.writefile(file1,1);
                    long endtime = System.nanoTime();
                    long totaltime = endtime - starttime;
                    System.out.println("Decompression runtime:" + totaltime);
                }
                else if(c2==2){
                    File file = new File("F:\\Huffman coding\\src\\com\\company\\binaryInput2.txt");
                    File file1 = new File("F:\\Huffman coding\\src\\com\\company\\output.txt");
                    Decompression decompression = new Decompression();
                    decompression.readfile(file);
                    long starttime = System.nanoTime();
                    decompression.buildtree();
                    decompression.decompress();
                    decompression.writefile(file1,0);
                    long endtime = System.nanoTime();
                    long totaltime = endtime - starttime;
                    System.out.println("Decompression runtime :" + totaltime);
                }
                else{
                    File file = new File("F:\\Huffman coding\\src\\com\\company\\folders.txt");
                    FolderDecompossion folderDecompossion=new FolderDecompossion();
                    folderDecompossion.readcompressedfolder(file);
                    long starttime = System.nanoTime();
                    folderDecompossion.buildtree();
                    folderDecompossion.decompress();
                    folderDecompossion.writefolderfiles();
                    long endtime = System.nanoTime();
                    long totaltime = endtime - starttime;
                    System.out.println("Decompression runtime:" + totaltime);
                }
            }
            else if(c==3){
                return;
            }

        }
    }

}
