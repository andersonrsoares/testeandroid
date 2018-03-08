package br.com.andersonsoares.madeiramadeirateste;

import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by devmaker on 08/03/18.
 */

public class Utils {

    public static void saveBitmap(Bitmap bitmap, String filePath, String imageType, int compression){

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // PNG is a loss less format, the compression factor (100) is ignored
            if(imageType.equals("png") || imageType.equals("PNG") || imageType.equals(".png")){
                bitmap.compress(Bitmap.CompressFormat.PNG, compression, out);
            }
            else if(imageType.equals("jpg") || imageType.equals("JPG") || imageType.equals(".jpg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
            else if(imageType.equals("jpeg") || imageType.equals("JPEG") || imageType.equals(".jpeg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
