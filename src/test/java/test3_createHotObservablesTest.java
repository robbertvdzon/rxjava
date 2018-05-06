import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class test3_createHotObservablesTest {
    private PublishSubject<Integer> hotObservable = PublishSubject.create();


    @Test
    public void test() throws InterruptedException {
        // create hot observable
        // note that the hotObservable is just a PublishSubject which can be used as a Observable
        // but also as an Observer where we can just call onNext on
        startHotObservable();
        hotObservable.subscribe(nr-> System.out.println("Observable a:"+nr));
        sleep(150);
        hotObservable.subscribe(nr-> System.out.println("Observable b:"+nr));
        sleep(500);

        // TODO:
        // test met refcount (pag 54)
    }

    private void startHotObservable() {
        new Thread(()->{
            int nr = 1000;
            while(true) {
                sleep(50);
                hotObservable.onNext(nr++);
            }
        }).start();

    }

    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }
}
