import io.reactivex.Observable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class test2_createColdObservablesTest {

    @Test
    public void test() throws InterruptedException {
        // create eager
        createObservableUsingCreate().subscribe(System.out::println);
        createObservableUsingJust().subscribe(System.out::println);
        createObservableUsingRange().subscribe(System.out::println);
        createObservableUsingFrom().subscribe(System.out::println);

        // create lazy
        // this call will create the list, even when we do not subscribe to it
        createObservableUsingFrom();

        // this call is lazy, so will not create the list when we do not subscrive to it
        createLazyObservableUsingFrom();

        // now the list will be created, because we subscribe to it
        createLazyObservableUsingFrom().subscribe(System.out::println);

        // using timer / delay
        createObservableUsingTimer().subscribe(n-> System.out.println("from timer:"+n));
        createObservableUsingInterval().subscribe(n-> System.out.println("from interval:"+n));

        sleep(3000);


    }

    private Observable<Long> createObservableUsingInterval() {
        return Observable.interval(300, TimeUnit.MILLISECONDS);
    }

    private Observable<Long> createObservableUsingTimer() {
        return Observable.timer(1, TimeUnit.SECONDS);
    }

    private Observable<Integer> createObservableUsingCreate() {
        /*
          Aan de create geef je een ObservableOnSubscribe. Die heeft 1 functie:
          subscribe(ObservableEmitter<T> var1)

          De ObservableEmitter bevat de volgde interface
          public interface Emitter<T> {
            void onNext(@NonNull T var1);
            void onError(@NonNull Throwable var1);
            void onComplete();
          }

          Met Observable.create heb je zelf toegang tot de Observer (via de class Emitter) en kun je direct onNext, onComplete etc aanroepen.
         */
        return Observable.create(
                s -> {
                    IntStream.range(0, 4).forEach(i -> s.onNext(i));
                    s.onComplete();
                }
        );
    }

    private Observable<Integer> createObservableUsingRange() {
        return Observable.range(8,3);
    }

    private Observable<Integer> createObservableUsingJust() {
        return Observable.just(5, 6, 7);
    }

    private Observable<Integer> createObservableUsingFrom() {
        List<Integer> integerList = Arrays.asList(11,12,13);
        System.out.println("Integer list created from eager createObservableUsingFrom");
        return Observable.fromIterable(integerList);
    }

    private Observable<Integer> createLazyObservableUsingFrom() {
        return Observable.defer(() -> {
            List<Integer> integerList = Arrays.asList(11,12,13);
            System.out.println("Integer list created from lazy createLazyObservableUsingFrom");
            return Observable.fromIterable(integerList);
        });
    }

    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }


}
