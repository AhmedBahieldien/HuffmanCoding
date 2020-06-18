package com.company;
import java.io.*;
import java.util.*;


public class Decompression {

    private Map <Character,String> codemap=new HashMap<Character,String>();
    private String massage;
    public  Node root=new Node();
    public StringBuilder output=new StringBuilder();
    BufferedReader br ;


    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    protected void readfile(File file) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        br=new BufferedReader(new FileReader(file));
        String line,l1,l2;
int i=0;
        while (i<lines-1){
            line=br.readLine();
           /* if(line.equals("h_end")){
                line=br.readLine();
                setMassage(line);
                break;
            }*/
            l1=line.substring(0,8);
            l2=line.substring(8);
            int dec=Integer.parseInt(l1,2);
            char c=(char)dec;
            codemap.put(c,l2);
            i++;
        }
        line=br.readLine();
        setMassage(line);
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
        for (int i=0;i<getMassage().length();i++) {


            if (getMassage().charAt(i) == '0') {
                if(temp.left!=null)
                    temp = temp.left;

                if(temp.value!='~'){
                    output.append(temp.value);
                    temp=root;

                }
            }
            if (getMassage().charAt(i) == '1') {
                if(temp.right!=null)
                temp=temp.right;
                if(temp.value!='~'){
                    output.append(temp.value);
                    temp=root;

                }
            }

        }
    }

    protected void writefile(File file,int type) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        if(type==1)
        printWriter.println(output.toString());
        for (char c:output.toString().toCharArray()){
            int x=(int)c;
            String str=Integer.toBinaryString(x);
            if(type==0)
            printWriter.write(("00000000" + str).substring(str.length()));
        }
printWriter.close();
    }
}
