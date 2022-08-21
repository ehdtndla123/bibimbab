package com.example.bibimbab;

import com.example.bibimbab.word.WordForm;
import com.example.bibimbab.word.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;
@SpringBootTest
class BibimbabApplicationTests {

    @Autowired
    private WordService wordService;

    @Test
    void testJpa() {

        HashMap<String,String> word=new HashMap<String,String>();
        //사이트 1
        try{
            String URL = "https://2centi.tistory.com/603";
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get();
            Elements file=html.getElementsByClass("tt_article_useless_p_margin contents_style");
            Elements files=file.select("p");

            int flag=1;
            int after=0;
            String name="";
            String mean;
            for(Element elm : files){

                String text=elm.text();
                char[] charArray = text.toCharArray();
                if (charArray.length==0) continue;
                char firstChar = charArray[0];
                char slash='-';
                if (Character.isDigit(firstChar)){
                    //System.out.println("숫자줄 " + text);
                    String result=text.substring(text.lastIndexOf(".")+1);
                    name=result;
                    flag=0;
                    after=1;
                }else if(flag!=1){

                    //System.out.println("아님 " + text);
                    String result=text.substring(text.lastIndexOf("-")+1);
                    word.put(name,result);
                    flag=1;
                }else if(Character.compare(firstChar,slash)!=0&&after==1){
                    String temp=word.get(name);
                    String presult=temp+text;
                    word.put(name,presult);

                }

            }


            //System.out.println( html.toString() );

        }catch(IOException e){
            e.printStackTrace();

        }
        try{
            String URL="https://youngni-96.tistory.com/85";
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get();
            Elements file=html.getElementsByClass("tt_article_useless_p_margin contents_style");
            Elements files=file.select("p>b > span");
            int i=0;
            for(Element elm:files){
                if(i>49)continue;
                String text=elm.text();
                char[] charArray = text.toCharArray();
                char firstChar = charArray[0];
                if (charArray.length==0) continue;
                i++;
                String[] result1=text.split(": ");
                String result=result1[0].substring(result1[0].lastIndexOf(". ")+1);
                if(!word.containsKey(result)){
                    word.put(result,result1[1]);
                }




            }
            //System.out.println(files.toString());
        }catch(IOException e){
            e.printStackTrace();;
        }
        int i=0;
        for ( Map.Entry<String, String> entry : word.entrySet()){
            i++;
            System.out.println(i+" "+entry);
        }

        for (Map.Entry<String, String> entry : word.entrySet()) {
            String name = entry.getKey();
            String meaning = entry.getValue();
            String example ="";
            this.wordService.create(new WordForm(name,meaning,example));
        }
    }
}
