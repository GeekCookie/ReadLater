package cookie.cn.readlater;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author bo_siu
 * @date 16/10/15
 * @description
 * @Email: zhibt_com@163.com
 */
public class ThreadManager {
    public static ExecutorService mExecutor;

    public static void runInThread(Runnable runnable) {
        if (mExecutor == null) {
            mExecutor = Executors.newCachedThreadPool();
        }
        mExecutor.execute(runnable);
    }
}
