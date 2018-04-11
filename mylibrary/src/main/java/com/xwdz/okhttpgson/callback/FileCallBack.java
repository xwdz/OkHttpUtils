package com.xwdz.okhttpgson.callback;

import com.xwdz.okhttpgson.LOG;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @since 2018/3/31
 */
public abstract class FileCallBack extends AbstractCallBack<File> {

    private static final String FILE_PAUSE_NAME = "pause";
    private static final String FILE_NOT_SUPPORT = "not_support";

    private volatile boolean mPause;

    private long mCurrentLength;

    /**
     * 目标路径
     */
    private String mPath;
    /**
     * 目标文件名
     */
    private String mFileName;

    public FileCallBack(String path, String fileName, long currentLength) {
        this.mPath = path;
        this.mFileName = fileName;
        this.mCurrentLength = currentLength;
    }

    @Override
    protected File parser(Call call, Response response) {
        final int code = response.code();
        if (code != HttpURLConnection.HTTP_PARTIAL) {
            onFailure(call, new IOException("service not support HTTP_PARTIAL  + " + code));
            return new File(FILE_NOT_SUPPORT);
        }

        File file = null;
        try {
            file = saveFile(response);
            final File finalFile = file;
            if (FILE_PAUSE_NAME.equals(file.getName())) {
                onPause();
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinish(finalFile);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(call, e);
        }
        return file;
    }

    private File saveFile(Response response) throws IOException {

        FileChannel fileChannel = null;
        RandomAccessFile randomAccessFile = null;
        InputStream is = null;
        File resultFile;

        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dir = new File(mPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            resultFile = new File(dir, mFileName);
            randomAccessFile = new RandomAccessFile(resultFile.getAbsolutePath(), "rw");
            fileChannel = randomAccessFile.getChannel();
            LOG.w("[saveFile] current =" + mCurrentLength + " ,total=" + total);
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, mCurrentLength, total);
            int len = 0;
            byte[] buffer = new byte[1024];
            onStart();
            while ((len = is.read(buffer)) != -1) {

                if (mPause) {
                    return new File(FILE_PAUSE_NAME);
                }

                sum += len;
                mappedByteBuffer.put(buffer, 0, len);
                onProgressListener(sum * 1.0f / total, total);
            }
            return resultFile;
        } finally {
            try {
                response.body().close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fileChannel != null) {
                fileChannel.close();
            }
        }
    }

    public void pause() {
        mPause = true;
    }


    protected abstract void onProgressListener(float current, long total);

    protected abstract void onFinish(File file);

    protected abstract void onStart();

    protected abstract void onPause();

}
