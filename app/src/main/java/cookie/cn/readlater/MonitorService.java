package cookie.cn.readlater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bo_siu
 * @date 16/10/15
 * @description
 * @Email: zhibt_com@163.com
 */
public class MonitorService extends Service {

    ServerSocket mServerSocket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int flag = super.onStartCommand(intent, flags, startId);
        if (mServerSocket == null) {
            try {
                mServerSocket = new ServerSocket(22021);
                mServerSocket.setReuseAddress(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ThreadManager.runInThread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        final Socket socket = mServerSocket.accept();
                        ThreadManager.runInThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    BufferedReader is = new BufferedReader(new InputStreamReader
                                            (socket.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String tmp;
                                    while ((tmp = is.readLine()) != null) {
                                        buffer.append(tmp);
                                        break;
                                    }
                                    socket.getOutputStream().write("200".getBytes());
                                    socket.getOutputStream().flush();
                                    socket.getOutputStream().close();

                                    Log.e("MonitorService", buffer.toString());
                                    if (buffer.toString().toLowerCase().contains("http")) {
                                        Intent intent1 = new Intent(MonitorService.this,
                                                MainActivity
                                                        .class);
                                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent1);
                                    }
                                    is.close();
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return flag;
    }
}
