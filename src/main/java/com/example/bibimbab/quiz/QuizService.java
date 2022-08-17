package com.example.bibimbab.quiz;

import com.example.bibimbab.word.Word;
import com.example.bibimbab.word.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final WordRepository wordRepository;

    public Set<Integer> getRandom(int size,int checknumber){
        Random random = new Random();
        random.setSeed(System.nanoTime());

        int min=1, max=(int)wordRepository.count();
        Set<Integer> result=new TreeSet<>();
        while(result.size()!=size){
            int number=random.nextInt(max-min+1)+min;
            if(number==checknumber){
                continue;
            }
            result.add(number);
        }

        return result;
    }

    public void createQuiz(){
        Set<Integer> randomWord=this.getRandom(5,0);
        Quiz quiz =new Quiz();
    }
    public List<Quiz> getQuizList(){
        // 5문제 random으로 가져오기
        Set<Integer> randomWordId=this.getRandom(5,0); //문제 이다.
        Iterator<Integer> iter=randomWordId.iterator();
        List<Quiz> quizList=new ArrayList<>();
        //5문제의 퀴즈를 만드는 것이다.
        while(iter.hasNext()){
            Optional<Word> word=this.wordRepository.findById(iter.next()); //문제가 될 단어 가져오기
            Word answerWord=word.get(); //문제이자 정답인 단어

            Set<Integer> randomExampleWordId=this.getRandom(3,(int)answerWord.getId()); //보기에 3개 추출
            Iterator<Integer> iter2=randomExampleWordId.iterator();

            //quiz 에 종속될 list
            List<Word> wordList=new ArrayList<>();
            wordList.add(answerWord);
            //보기 3개 넣기
            while(iter2.hasNext()){
                Optional<Word> exampleWordList=this.wordRepository.findById(iter2.next());
                Word exampleWord=exampleWordList.get();
                wordList.add(exampleWord);
            }
            // 섞기
            Collections.shuffle(wordList);
            Quiz quiz=new Quiz(wordList,answerWord.getName());
            quizList.add(quiz);
        }

        return quizList;
    }
}
