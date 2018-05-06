import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class test3_operators {


    @Test
    public void test() throws InterruptedException {
        // without cache()
        System.out.println("Without cache");
        Observable<Integer> observable = createObservable();
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);

        // with cache()
        System.out.println("With cache");
        Observable<Integer> cachedObservable = createObservable().cache();
        cachedObservable.subscribe(System.out::println);
        cachedObservable.subscribe(System.out::println);

        // filter
        Observable.range(0,30)
                .filter((i)->i%3==0)
                .subscribe(System.out::println);

        // delay
        System.out.println(System.currentTimeMillis());
        Observable.range(0,3)
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribe((i)-> System.out.println(System.currentTimeMillis()+":With delay:"+i));
        sleep(2000);


    }

    private Observable<Integer> createObservable() {
        return Observable.create(
                s -> {
                    System.out.println("Create list");
                    IntStream.range(0, 4).forEach(i -> s.onNext(i));
                    s.onComplete();
                }
        );
    }

}