package tk.pankajb.apitest.helpers;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UriEncoder {

    public static String encodeImage(ContentResolver resolver, Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
