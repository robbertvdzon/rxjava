import io.reactivex.Observable;

import java.util.stream.IntStream;

public class RxJavaApplication {
    static int nr = 0;
    public static void main (String[] args){
        new RxJavaApplication().start();
    }

    public void start(){
        getNumbers(1,5).subscribe(v-> System.out.println(v));
    }


    public Observable<Integer> getNumbers(int start, int end){
        return Observable.create(
                s -> {
                    IntStream.range(start, end).forEach(i->s.onNext(i));
                    s.onComplete();
                }
        );
    }

}
