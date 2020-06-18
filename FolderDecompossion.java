package com.company;

import java.io.*;
import java.util.*;

public class FolderDecompossion {

    private Map<Character,String> codemap=new HashMap<Character,String>();

    public  Node root=new Node();
    public String[] massages;
    public String[] outputs;
    public List<PrintWriter> printWriterList=new ArrayList<PrintWriter>();
    public int nfiles;
    BufferedReader br ;
    public String[] getMassages() {
        return massages;
    }

    protected void readcompressedfolder(File file) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        br=new BufferedReader(new FileReader(file));
        String line,l1,l2;
        line=br.readLine();
        lines--;
        int x=Integer.parseInt(line,2);
        outputs=new String[x];
        massages=new String[x];
        int k=0;
        lines=lines-x;
        while (k<lines){
            line=br.readLine();
           /* if(line.equals("h_end")){

                break;
            }*/
            l1=line.substring(0,8);
            l2=line.substring(8);
            int dec=Integer.parseInt(l1,2);
            char c=(char)dec;
            codemap.put(c,l2);
k++;
        }
        int i=0;
        while ((line=br.readLine())!=null){
         massages[i]=line;
outputs[i]="";
         i++;
        }
        //outputs=new StringBuilder[4];
        nfiles=x;
    }
    protected void buildtree()
    {
        String s;
        char c;
        Set set =codemap.entrySet();
        Iterator iterator=set.iterator();
        root.left=null;
        root.right=null;
        root.value='~';
        Node temp;
        while (iterator.hasNext()){


            temp=root;
            Map.Entry entry=(Map.Entry)iterator.next();
            s= (String) entry.getValue();
            c=(char)entry.getKey();
            for (int i=0;i<s.length();i++)
            {
                if(s.charAt(i)=='0' && i!=s.length()-1){
                    if(temp.left==null) {
                        Node node = new Node();

                        node.value='~';
                        temp.left=node;
                        //step
                        temp=temp.left;

                    }
                    else
                        temp=temp.left;
                }
                if(s.charAt(i)=='1' && i!=s.length()-1) {
                    if (temp.right == null) {
                        Node node = new Node();
                        node.value='~';
                        temp.right=node;
                        //step
                        temp=temp.right;
                    }
                    else{

                        temp=temp.right;}
                }

            }
            Node newnode=new Node();
            newnode.left=null;
            newnode.right=null;
            newnode.value=c;
            if(s.charAt(s.length()-1)=='0'){


                temp.left=newnode;
            }
            else if(s.charAt(s.length()-1)=='1') {

                temp.right = newnode;
            }

        }
    }

    protected void decompress()
    {
        Node temp=root;

        for(int k=0;k<getMassages().length;k++) {
            String massage=getMassages()[k];
      temp=root;
            System.out.println(massage);
            System.out.println(massage.length());
            for (int i = 0; i < massage.length(); i++) {
                if (massage.charAt(i) == '0') {
                    if(temp.left!=null)
                        temp = temp.left;
                    if (temp.value != '~') {
                        //outputs[k].append(temp.value);
                        outputs[k]=outputs[k]+temp.value;
                        temp = root;
                    }
                }
                if (massage.charAt(i) == '1') {
                    if(temp.right!=null)
                        temp=temp.right;
                    if (temp.value != '~') {
                        //outputs[k].append(temp.value);
                        outputs[k]=outputs[k]+temp.value;

                        temp = root;

                    }
                }

            }
            System.out.println(outputs[k]);
        }
    }

    protected void writefolderfiles() throws IOException {

        for (int i=0;i<nfiles;i++){

            FileWriter fileWriter = new FileWriter("F:\\Huffman coding\\src\\com\\company\\Folder\\Output"+(i+1)+".txt");
            fileWriter.write(outputs[i]);
           //w.println(outputs[i]);
            for (char c:outputs[i].toCharArray()){
                int x=(int)c;
                //String str=Integer.toBinaryString(x);
               // w.write(("00000000" + str).substring(str.length()));
               // fileWriter.write(("00000000" + str).substring(str.length()));
            }
            fileWriter.close();
        }

    }


}
