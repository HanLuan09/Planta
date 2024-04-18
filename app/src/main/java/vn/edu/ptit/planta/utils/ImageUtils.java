package vn.edu.ptit.planta.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    @Nullable
    public static String imageToBase64(@NonNull Context context, Uri uri) {

        int maxWidth = 400;
        int maxHeight = 400;
        try {
            // Đọc ảnh từ Uri vào một đối tượng Bitmap
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Giảm kích thước của ảnh nếu nó vượt quá maxWidth hoặc maxHeight
            if (bitmap != null) {
                int originalWidth = bitmap.getWidth();
                int originalHeight = bitmap.getHeight();
                float scale = Math.min((float) maxWidth / originalWidth, (float) maxHeight / originalHeight);

                int scaledWidth = Math.round(originalWidth * scale);
                int scaledHeight = Math.round(originalHeight * scale);

                bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Thay đổi định dạng nén theo định dạng của ảnh ban đầu
            Bitmap.CompressFormat compressFormat;
            if (bitmap.hasAlpha()) {
                compressFormat = Bitmap.CompressFormat.PNG; // Giữ nguyên định dạng PNG nếu có kênh alpha
            } else {
                compressFormat = Bitmap.CompressFormat.JPEG; // Nén về JPEG nếu không có kênh alpha
            }

            // Nén bitmap với định dạng và chất lượng tương ứng
            bitmap.compress(compressFormat, 80, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            // Chuyển đổi mảng byte[] thành chuỗi Base64
            return Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lấy định dạng (extension) của file ảnh từ URI
    public static String getMimeType(Context context, @NonNull Uri uri) {
        String extension = "";
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            // Nếu URI là content URI
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            // Nếu URI là file URI
            extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        }
        return extension;
    }

    @NonNull
    @Contract(pure = true)
    public static String formattedBase64(String mimeType, String base64Image) {
        String base = "data:image/" + mimeType + ";base64," + base64Image;
        return base.toString().trim();
    }
}

