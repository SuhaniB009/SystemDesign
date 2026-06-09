public class ThreadSafeDoubleLockingSingleton {
    private static ThreadSafeDoubleLockingSingleton instance = null;

    private ThreadSafeDoubleLockingSingleton() {
        System.out.println("Singleton Constructor Called!");
    }

    public static ThreadSafeDoubleLockingSingleton getInstance() {
        if (instance == null) { 
            synchronized (ThreadSafeDoubleLockingSingleton.class) {
                if (instance == null) { 
                    instance = new ThreadSafeDoubleLockingSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        ThreadSafeDoubleLockingSingleton s1 = ThreadSafeDoubleLockingSingleton.getInstance();
        ThreadSafeDoubleLockingSingleton s2 = ThreadSafeDoubleLockingSingleton.getInstance();

        System.out.println(s1 == s2);
    }
}