package com.xwdz.okhttpgson.callback;

import com.xwdz.okhttpgson.LOG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @since 2018/3/31
 */
public abstract class FileCallBack extends AbstractCallBack<File> {

    private static final int SIZE = 2048;

    /**
     * 目标路径
     */
    private String mPath;
    /**
     * 目标文件名
     */
    private String mFileName;

    public FileCallBack(String path, String fileName) {
        this.mPath = path;
        this.mFileName = fileName;
    }

    @Override
    protected File parser(Call call, Response response) {
        File file = null;
        try {
            file = saveFile(response);
            final File finalFile = file;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFinish(finalFile);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(call, e);
        }
        return file;
    }

    private File saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[SIZE];
        int len;
        FileOutputStream fos = null;
        File resultFile = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dir = new File(mPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            resultFile = new File(dir, mFileName);
            fos = new FileOutputStream(resultFile);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onStart();
                }
            });

            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                onProgressListener(sum * 1.0f / total, total);
            }
            fos.flush();
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
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void onProgressListener(float current, long total);

    protected abstract void onFinish(File file);

    protected abstract void onStart();

}
