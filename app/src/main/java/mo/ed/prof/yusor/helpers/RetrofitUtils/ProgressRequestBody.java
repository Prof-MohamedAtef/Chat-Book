package mo.ed.prof.yusor.helpers.RetrofitUtils;

import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by Prof-Mohamed Atef on 3/30/2019.
 */

public class ProgressRequestBody extends RequestBody {

    private File file;
    private UploadCallbacks listener;
    private static final int DEFAULT_BUFFER_SIZE=4096;

    public ProgressRequestBody(File file, UploadCallbacks listener){
        this.file=file;
        this.listener=listener;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength=file.length();
        byte[] buffer=new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream inputStream=new FileInputStream(file);
        long uploaded=0;
        try{
            int read;
            android.os.Handler handler= new android.os.Handler(Looper.getMainLooper());
            while ((read=inputStream.read(buffer))!=-1){
                handler.post(new ProgressUpdater(uploaded,fileLength));
                uploaded+=read;
                sink.write(buffer,0,read);
            }
        }finally {
            inputStream.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long uploaded;
        private long fileLength;
        public ProgressUpdater(long uploaded, long fileLength) {
            this.fileLength=fileLength;
            this.uploaded=uploaded;
        }

        @Override
        public void run() {
            listener.onProgressUpdate((int)(100*uploaded/fileLength));
        }
    }
}
