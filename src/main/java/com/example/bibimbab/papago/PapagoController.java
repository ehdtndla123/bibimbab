package com.example.bibimbab.papago;

import com.example.bibimbab.word.WordForm;
import com.example.bibimbab.word.WordService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/papago")
public class PapagoController {

    private final WordService wordService;

    @RequestMapping("/crollsklasdkljasfljasdkljsadljds")
    public void croll(){
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
        i=0;
        for (Map.Entry<String, String> entry : word.entrySet()) {
            // if(entry.getKey()=="돈미새 / 여미새 / 남미새") continue;
            String name = entry.getKey();
            String meaning = entry.getValue();
            String example ="";
            this.wordService.create(new WordForm(name,meaning,example));
        }
    }

    @RequestMapping("")
    public String translator(@Valid PapagoForm papagoForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "papago";
        }

        // BufferedReader br = new BufferedReader(papagoForm.getTranslation());
        InputStream is = new ByteArrayInputStream(papagoForm.getTranslation().getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String str="";
        for(int j=0; j<sb.length();j++) {


            String line = br.readLine();

            for (int i = 0; i < line.length(); i++) {
                sb.append(trans(line.charAt(i)));
            }
            System.out.println(sb);
            sb.delete(0, sb.length());
        }
        System.out.println("----------------");
        System.out.println(sb);
        return "redirect:/";
    }


    public static char trans(char w) {
        if('대'<=w&&w<='댛')//대->머
            w+='머'-'대';
        else if('머'<=w&&w<='멓')//머->대
            w-='머'-'대';

        else if('며'<=w&&w<='몋')//며->띠
            w+='띠'-'며';
        else if('띠'<=w&&w<='띻')//띠->며
            w-='띠'-'며';

        else if('귀'<=w&&w<='귛')//귀->커
            w+='커'-'귀';
        else if('커'<=w&&w<='커')//커->귀
            w-='커'-'귀';

        else if('파'<=w&&w<='팧')//파->과
            w+='과'-'파';
        else if('과'<=w&&w<='괗')//과->파
            w-='과'-'파';

        else if('피'<=w&&w<='핗')//피->괴
            w+='괴'-'피';
        else if('괴'<=w&&w<='굏')//괴->피
            w-='괴'-'피';

        else if('비'<=w&&w<='빟')//비->네
            w+='네'-'비';
        else if('네'<=w&&w<='넿')//네->비
            w-='네'-'비';

        else if('근'==w)//근->ㄹ
            w='ㄹ';
        else if('ㄹ'==w)//ㄹ->근
            w='근';

        else if('유'==w)//유->윾
            w='윾';
        else if('윾'==w)//윾->유
            w='유';

        else if('위'==w)//위->읶
            w='읶';
        else if('읶'==w)//읶->위
            w='위';

        else if('굿'==w)//굿->긋
            w='긋';
        else if('긋'==w)//긋->굿
            w='굿';

        else if('삐'<=w&&w<='삫')//삐->볘
            w+='볘'-'삐';
        else if('볘'<=w&&w<='볳')//볘->삐
            w-='볘'-'삐';

        else if('포'<=w&&w<='퐇')//포->쪼
            w+='쪼'-'포';
        else if('쪼'<=w&&w<='쫗')//쪼->포
            w-='쪼'-'포';

        else if('거'<=w&&w<='겋')//거->지
            w+='지'-'거';
        else if('지'<=w&&w<='짛')//지->거
            w-='지'-'거';

        else if('겨'<=w&&w<='곃')//겨->저
            w+='저'-'겨';
        else if('저'<=w&&w<='젛')//저->겨
            w-='저'-'겨';

        else if('공'==w)//공->끙
            w='끙';
        else if('끙'==w)//끙->공
            w='공';

        else if('훟'==w)//훟->흫
            w='흫';
        else if('흫'==w)//흫->훟
            w='훟';

        else if('디'==w)//디->ㅁ
            w='ㅁ';
        else if('ㅁ'==w)//ㅁ->디
            w='디';

        else if('고'<=w&&w<='곻')//고->끄
            w+='끄'-'고';
        else if('끄'<=w&&w<='끟')//끄->고
            w-='끄'-'고';

        else if('구'==w)//구->ㅋ
            w='ㅋ';
        else if('ㅋ'==w)//ㅋ->구
            w='구';

        else if('켜'<=w&&w<='켷')//켜->궈
            w+='궈'-'켜';
        else if('궈'<=w&&w<='궣')//궈->켜
            w-='궈'-'켜';

        else if('왕'==w)//왕->앟
            w='앟';
        else if('앟'==w)//앟->왕
            w='왕';

        else if('왱'==w)//왱->앻
            w='앻';
        else if('앻'==w)//앻->왱
            w='왱';

        else if('욍'==w)//욍->잏
            w='잏';
        else if('잏'==w)//잏->욍
            w='욍';

        else if('왓'==w)//왓->앛
            w='앛';
        else if('앛'==w)//앛->왓
            w='왓';

        else if('왯'==w)//왯->앷
            w='앷';
        else if('앷'==w)//앷->왯
            w='왯';

        else if('욋'==w)//욋->잋
            w='잋';
        else if('잋'==w)//잋->욋
            w='욋';

        else if('봉'==w)//봉->넣
            w='넣';
        else if('넣'==w)//넣->봉
            w='봉';

        else if('티'<=w&&w<='팋')//티->더
            w+='더'-'티';
        else if('더'<=w&&w<='덯')//더->티
            w-='더'-'티';

        else if('익'==w)//익->의
            w='의';
        else if('의'==w)//의->익
            w='익';

        else if('든'==w)//든->ㅌ
            w='ㅌ';
        else if('ㅌ'==w)//ㅌ->든
            w='든';

        else if('백'==w)//백->뿌
            w='뿌';
        else if('뿌'==w)//뿌->백
            w='백';

        else if('포'==w)//포->딜
            w='딜';
        else if('딜'==w)//딜->포
            w='포';

        return w;
    }
}
