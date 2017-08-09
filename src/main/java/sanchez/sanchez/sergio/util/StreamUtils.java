package sanchez.sanchez.sergio.util;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
	public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
    
    public static <T> Stream<T> defaultIfEmpty(Stream<T> stream, Supplier<T> supplier) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }
    
    public static <T> Stream<T> concat(Stream<? extends T> lhs, Stream<? extends T> rhs) {
        return Stream.concat(lhs, rhs);
    }
    
    public static <T> Stream<T> concat(Stream<? extends T> lhs, T rhs) {
        return Stream.concat(lhs, Stream.of(rhs));
    }
    
	public static <T> T uncheckCall(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			return sneakyThrow(e);
		}
	}

	public static void uncheckRun(RunnableExc r) {
		try {
			r.run();
		} catch (Exception e) {
			sneakyThrow(e);
		}
	}

	public interface RunnableExc {
		void run() throws Exception;
	}

	public static <T> T sneakyThrow(Throwable e) {
		return StreamUtils.<RuntimeException, T>sneakyThrow0(e);
	}

	private static <E extends Throwable, T> T sneakyThrow0(Throwable t) throws E {
		throw (E) t;
	}
}
