package com.xwdz.okhttpgson.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 黄兴伟 (xwdz9989@gamil.com)
 * @since 2018/3/31
 */
public abstract class FileCallBack extends AbstractCallBack<File> {

    /**
     * 目标路径
     */
    private String mPath;
    /**
     * 目标文件名
     */
    private String mFileName;

    private File mFile;

    public FileCallBack(String path, String fileName) {
        this.mPath = path;
        this.mFileName = fileName;
    }


    public FileCallBack(File file) {
        this.mFile = file;
    }


    @Override
    protected File parser(Call call, Response response, boolean isMainUIThread) {
        File file = null;
        try {
            file = saveFile(response);
            final File finalFile = file;
            post(new Runnable() {
                @Override
                public void run() {
                    onFinish(finalFile);
                }
            }, isMainUIThread);
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(call, e);
        }
        return file;
    }

    private File saveFile(Response response) throws IOException {
        InputStream is = null;
        File resultFile = null;
        FileOutputStream fos = null;

        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            if (mFile == null) {
                File dir = new File(mPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                resultFile = new File(dir, mFileName);
            } else {
                resultFile = mFile;
            }
            fos = new FileOutputStream(resultFile);

            int len = 0;
            byte[] buffer = new byte[32 * 1024];
            onStart();
            while ((len = is.read(buffer)) != -1) {
                sum += len;
                fos.write(buffer, 0, len);
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
