package ru.mirea.khudyakovma.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String job = msg.getData().getString("WORK");
                int age = msg.getData().getInt("AGE");

                Log.d("MyLooper", "Принято сообщение: возраст = " + age + ", профессия = " + job);

                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Log.d("MyLooper", "Прошло " + age + " секунд. Работаем: " + job);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "Вам " + age + " лет и вы работаете как " + job);
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };

        Looper.loop();
    }
}
